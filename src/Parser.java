import java.util.*;
public class Parser {
    private ArrayList<SimpleToken> simpleTokens;
    private int position;
    private Simple simple;


    public Parser(Simple s, ArrayList<SimpleToken> arr){
        this.simple = s;
        this.simpleTokens = arr;
        position = 0;
    }

    public ArrayList<SimpleStatement> parseTokens(){
        ArrayList<SimpleStatement> simpleStatements = new ArrayList<>();

        while(position < simpleTokens.size()) {
            //if the source code consisted of new lines, skip them
            while(matchTokenWithType(TokenType.LINE));

            if (matchTokenWithText("print")){
                //if the token was a print token, parse the next
                // token to get the unitExpression to print
                SimpleExpression printExpr = expression();
                simpleStatements.add(new PrintStatement(printExpr));
            } else if (matchTokensWithTypes(TokenType.WORD, TokenType.EQUALS)){
                //if the token is a word followed by EQUALS, it's
                //an assignment statement so name and value required
                String name = prevToken(2).getText();
                SimpleExpression value = expression();
                AssignStatement assign = new AssignStatement(this.simple, name, value);
                simpleStatements.add(assign);
            } else if (matchTokenWithText("goto")){
                SimpleToken labelName = consumeTokenWithType(TokenType.WORD);
                simpleStatements.add(new GotoStatement(this.simple, labelName.getText()));
            } else if (matchTokenWithType(TokenType.LABEL)) {
                this.simple.getLabels().put(prevToken(1).getText(), simpleStatements.size());
            } else if (matchTokenWithText("if")){
                SimpleExpression condition = expression();
                consumeTokenWithText("then");
                String label = consumeTokenWithType(TokenType.WORD).getText();
                simpleStatements.add(new IfStatement(this.simple, condition, label));
            } else if (matchTokenWithType(TokenType.EOF)){
                //do nothing
            } else if (matchTokenWithText("scan")){
                String inputVar = consumeTokenWithType(TokenType.WORD).getText();
                InputStatement statement = new InputStatement(this.simple, inputVar);
                simpleStatements.add(statement);
            } else {
                SimpleToken currToken = getCurrToken();
                System.out.println("Error in line " + currToken.getLineNumber() + ": Unrecognized token => " + currToken.getText());
                this.simple.setCompileErrors(true);
                position++;
            }
        }
        return simpleStatements;
    }

    private SimpleToken getCurrToken (){
        return getToken(0);
    }
    private SimpleToken getToken (int offset){
        int index = position + offset;
        if (index >= simpleTokens.size()){
            return new SimpleToken("", TokenType.EOF, -1);
        }
        return simpleTokens.get(index);
    }
    private SimpleToken prevToken(int offset){
        int index = position - offset;
        return simpleTokens.get(index);
    }

    private boolean matchTokenWithType(String type){
        SimpleToken simpleToken = getCurrToken();
        if (!simpleToken.getType().equals(type)) return false;
        position++;
        return true;
    }

    private boolean matchTokenWithText(String name) {
        SimpleToken simpleToken = getCurrToken();
        if (!simpleToken.getType().equals(TokenType.WORD)) return false;
        if (!simpleToken.getText().equals(name)) return false;
        position++;
        return true;
    }

    private SimpleToken consumeTokenWithText(String text){
        if (!matchTokenWithText(text)) throw new Error("Expected " + text + ".");
        return prevToken(1);
    }

    private SimpleToken consumeTokenWithType (String type) {
        SimpleToken currSimpleToken = getCurrToken();
        if (!currSimpleToken.getType().equals(type)){
            throw new Error ("Expecting " + type);
        }
        else {
            return simpleTokens.get(position++);
        }
    }

    private boolean matchTokensWithTypes(String t1, String t2){
        SimpleToken tok1 = getCurrToken();
        SimpleToken tok2 = getToken(1);
        if (!tok1.getType().equals(t1)){
            return false;
        }
        if (!tok2.getType().equals(t2)){
            return false;
        }
        position += 2;
        return true;
    }

    private SimpleExpression unitExpression() {
        if (matchTokenWithType(TokenType.STRING)) {
            return new StringValue(prevToken(1).getText());
        } else if (matchTokenWithType(TokenType.NUMBER)){
            return new NumberValue(Double.parseDouble(prevToken(1).getText()));
        } else if (matchTokenWithType(TokenType.WORD)){
            return new VariableExpression(this.simple, prevToken(1).getText());
        }
        throw new Error("Couldn't parse");
    }
    private SimpleExpression expression() {
        SimpleExpression simpleExpression = unitExpression();

        // Keep building operator expressions as long as we have operators.
        if (matchTokenWithType(TokenType.OPERATOR) ||
                matchTokenWithType(TokenType.EQUALS)) {
            char operator = prevToken(1).getText().charAt(0);
            SimpleExpression right = unitExpression();
            simpleExpression = new OperatorExpression(simpleExpression, operator, right);
        }

        return simpleExpression;
    }
}
