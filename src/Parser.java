import java.util.*;
public class Parser {
    private ArrayList<Token> tokens;
    private int position;
    private Simple simple;


    public Parser(Simple s, ArrayList<Token> arr){
        this.simple = s;
        this.tokens = arr;
        position = 0;
    }

    public ArrayList<SimpleStatements> parseTokens(){
        ArrayList<SimpleStatements> simpleStatements = new ArrayList<>();

        while(true) {
            //if the source code consisted of new lines, skip them
            while(matchTokenWithType(TokenType.LINE));

            if (matchTokenWithText("print")){
                //if the token was a print token, parse the next
                // token to get the unitExpression to print
                SimpleExpression printExpr = expression();
                simpleStatements.add(new PrintSimpleStatements(printExpr));
            } else if (matchTokensWithTypes(TokenType.WORD, TokenType.EQUALS)){
                //if the token is a word followed by EQUALS, it's
                //an assignment statement so name and value required
                String name = prevToken(2).getText();
                SimpleExpression value = expression();
                AssignSimpleStatements assign = new AssignSimpleStatements(this.simple, name, value);
                simpleStatements.add(assign);
            } else if (matchTokenWithText("goto")){
                Token labelName = consumeTokenWithType(TokenType.WORD);
                simpleStatements.add(new GotoSimpleStatements(this.simple, labelName.getText()));
            } else if (matchTokenWithType(TokenType.LABEL)) {
                this.simple.getLabels().put(prevToken(1).getText(), simpleStatements.size());
            } else if (matchTokenWithText("if")){
                SimpleExpression condition = expression();
                consumeTokenWithText("then");
                String label = consumeTokenWithType(TokenType.WORD).getText();
                simpleStatements.add(new IfSimpleStatements(this.simple, condition, label));
            }
            else {
                break;
            }
        }
        return simpleStatements;
    }

    private Token getCurrToken (){
        return getToken(0);
    }
    private Token getToken (int offset){
        int index = position + offset;
        if (index >= tokens.size()){
            return new Token ("", TokenType.EOF);
        }
        return tokens.get(index);
    }
    private Token prevToken(int offset){
        int index = position - offset;
        return tokens.get(index);
    }

    private boolean matchTokenWithType(String type){
        Token token = getCurrToken();
        if (!token.getType().equals(type)) return false;
        position++;
        return true;
    }

    private boolean matchTokenWithText(String name) {
        Token token = getCurrToken();
        if (!token.getType().equals(TokenType.WORD)) return false;
        if (!token.getText().equals(name)) return false;
        position++;
        return true;
    }

    private Token consumeTokenWithText(String text){
        if (!matchTokenWithText(text)) throw new Error("Expected " + text + ".");
        return prevToken(1);
    }

    private Token consumeTokenWithType (String type) {
        Token currToken = getCurrToken();
        if (!currToken.getType().equals(type)){
            throw new Error ("Expecting " + type);
        }
        else {
            return tokens.get(position++);
        }
    }

    private boolean matchTokensWithTypes(String t1, String t2){
        Token tok1 = getCurrToken();
        Token tok2 = getToken(1);
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
            return new StringSimpleValue(prevToken(1).getText());
        } else if (matchTokenWithType(TokenType.NUMBER)){
            return new NumberSimpleValue(Double.parseDouble(prevToken(1).getText()));
        } else if (matchTokenWithType(TokenType.WORD)){
            return new VariableSimpleExpression(this.simple, prevToken(1).getText());
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
            simpleExpression = new OperatorSimpleExpression(simpleExpression, operator, right);
        }

        return simpleExpression;
    }
}
