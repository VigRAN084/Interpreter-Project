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

    public ArrayList<Statement> parseTokens(){
        ArrayList<Statement> statements = new ArrayList<>();

        while(true) {
            //if the source code consisted of new lines, skip them
            while(matchTokenWithType(TokenType.LINE));

            if (matchTokenWithText("print")){
                //if the token was a print token, parse the next
                // token to get the expression to print
                Expression printExpr = expression();
                statements.add(new PrintStatement(printExpr));
            } else if (matchTokensWithTypes(TokenType.WORD, TokenType.EQUALS)){
                //if the token is a word followed by EQUALS, it's
                //an assignment statement so name and value required
                String name = prevToken(2).getText();
                Expression value = expression();
                AssignStatement assign = new AssignStatement(this.simple, name, value);
                statements.add(assign);
            } else if (matchTokenWithText("goto")){
                Token labelName = getNextWordToken(TokenType.WORD);
                statements.add(new GotoStatement(this.simple, labelName.getText()));
            } else if (matchTokenWithType(TokenType.LABEL)) {
                this.simple.getLabels().put(prevToken(1).getText(), statements.size());
            }
            else {
                break;
            }
        }
        return statements;
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

    private Token getNextWordToken(String name){
        if (!matchTokenWithType(name)) throw new Error("Expected " + name + ".");
        return prevToken(1);
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

    private Expression expression() {
        if (matchTokenWithType(TokenType.STRING)) {
            return new StringValue(prevToken(1).getText());
        } else if (matchTokenWithType(TokenType.NUMBER)){
            return new NumberValue(Double.parseDouble(prevToken(1).getText()));
        } else if (matchTokenWithType(TokenType.WORD)){
            return new VariableExpression(this.simple, prevToken(1).getText());
        }
        throw new Error("Couldn't parse");
    }
}
