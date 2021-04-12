import java.util.*;
public class Parser {
    //List of Tokens to parse
    private ArrayList<SimpleToken> simpleTokens;
    //holds the position in Tokens arraylist, which is currently being parsed
    private int position;
    //handle to the simple object
    private Simple simple;

    /**
     * Constrcutor for Parser
     * @param s - simple object
     * @param arr - arraylist
     */
    public Parser(Simple s, ArrayList<SimpleToken> arr){
        this.simple = s;
        this.simpleTokens = arr;
        position = 0;
    }

    /**
     * parseTokens and return an arraylist
     * @return arraylist
     */
    public ArrayList<SimpleStatement> parseTokens(){
        ArrayList<SimpleStatement> simpleStatements = new ArrayList<>();

        while(position < simpleTokens.size()) {
            if (matchTokenWithText("print")){
                PrintStatement stmt = buildPrintStatement();
                simpleStatements.add(stmt);
            } else if (matchTokensWithTypes(TokenType.WORD, TokenType.EQUALS)){
                AssignStatement assign = buildAssignStatement();
                simpleStatements.add(assign);
            } else if (matchTokenWithText("jumpto")){
                JumptoStatement jumptoStatement = buildJumptoStatement();
                simpleStatements.add(jumptoStatement);
            } else if (matchTokenWithType(TokenType.LABEL)) {
                this.simple.getLabels().put(prevToken(1).getText(), simpleStatements.size());
            } else if (matchTokenWithText("if")){
                IfElseStatement stmt = buildIfStatement();
                simpleStatements.add(stmt);
            }  else if (matchTokenWithText("scan")){
                ScanStatement statement = buildScanStatement();
                simpleStatements.add(statement);
            } else if (matchTokenWithType(TokenType.EOF)) {
                //do nothing
            } else if (matchTokenWithType(TokenType.LINE)) {
                //do nothing
            } else {
                SimpleToken currToken = getCurrToken();
                String mesg = "Error in line " + currToken.getLineNumber() + ": Unrecognized token => " + currToken.getText();
                System.out.println(mesg);
                this.simple.setCompileErrors(true);
                position++;
            }
        }
        return simpleStatements;
    }
    /**
     * creates a scanner statement
     * @return
     */
    private ScanStatement buildScanStatement() {
        String inputVar = consumeTokenWithType(TokenType.WORD).getText();
        return new ScanStatement(this.simple, inputVar);
    }

    /**
     * builds an IfElseStatement
     * @return
     */
    private IfElseStatement buildIfStatement() {
        SimpleExpression condition = buildExpression();
        consumeTokenWithText("then");
        String ifLabel = consumeTokenWithType(TokenType.WORD).getText();
        SimpleToken elseToken = consumeTokenWithText("else", false);
        if (elseToken != null){
            String elseLabel = consumeTokenWithType(TokenType.WORD).getText();
            return new IfElseStatement(this.simple, condition, ifLabel, elseLabel);
        }
        else {
            return new IfElseStatement(this.simple, condition, ifLabel);
        }
    }

    /**
     * builds a JumptoStatement
     * @return
     */
    private JumptoStatement buildJumptoStatement() {
        SimpleToken labelName = consumeTokenWithType(TokenType.WORD);
        return new JumptoStatement(this.simple, labelName.getText());
    }
    /**
     * builds an AssigntoStatement
     * @return
     */
    private AssignStatement buildAssignStatement() {
        String name = prevToken(2).getText();
        SimpleExpression value = buildExpression();
        return new AssignStatement(this.simple, name, value);
    }
    /**
     * builds a PrintStatement
     * @return
     */
    private PrintStatement buildPrintStatement() {
        SimpleExpression printExpr = buildExpression();
        return new PrintStatement(printExpr);
    }

    /**
     * Retrieves the currentToken
     * @return
     */
    private SimpleToken getCurrToken (){
        return getToken(0);
    }

    /**
     * Gets the token offset from current position
     * @param offset
     * @return
     */
    private SimpleToken getToken (int offset){
        int index = position + offset;
        if (index >= simpleTokens.size()){
            return new SimpleToken("", TokenType.EOF, -1);
        }
        return simpleTokens.get(index);
    }

    /**
     * uses the negative of offset from position to get a previous token
     * @param offset
     * @return
     */
    private SimpleToken prevToken(int offset){
        int index = position - offset;
        return simpleTokens.get(index);
    }

    /**
     * tries to match the current token with a passed value of type
     * increments position if its a match
     * @param type
     * @return
     */
    private boolean matchTokenWithType(String type){
        SimpleToken simpleToken = getCurrToken();
        if (!simpleToken.getType().equals(type)) return false;
        position++;
        return true;
    }

    /**
     * tries to match the token with a passed text
     * increments position if there is in fact a match
     * @param name
     * @return
     */
    private boolean matchTokenWithText(String name) {
        SimpleToken simpleToken = getCurrToken();
        if (!simpleToken.getType().equals(TokenType.WORD)) return false;
        if (!simpleToken.getText().equals(name)) return false;
        position++;
        return true;
    }

    /**
     * tries to match the token with text
     * if there is a match, increments position and returns the token
     * @param text
     * @return
     */
    private SimpleToken consumeTokenWithText(String text){
        return consumeTokenWithText(text, true);
    }

    /**
     * tries to match the token with text
     * if it is a required token, throws an error; otherwise, returns null
     * @param text
     * @param required
     * @return
     */
    private SimpleToken consumeTokenWithText(String text, boolean required){
        boolean found = matchTokenWithText(text);
        if(found) return prevToken(1);
        else {
            if (!required) return null;
            else throw new Error("Expected " + text + ".");
        }
    }

    /**
     * tries to match a token with a type
     * if there is a match, increments position and returns the token
     * @param type
     * @return
     */
    private SimpleToken consumeTokenWithType (String type) {
        SimpleToken currToken = getCurrToken();
        if (!currToken.getType().equals(type)){
            throw new Error ("Expecting " + type);
        }
        else {
            position++;
            return currToken;
        }
    }

    /**
     * matches two consecutive tokens
     * if there is a match, increment position by 2
     * @param t1
     * @param t2
     * @return
     */
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

    /**
     * tries to match StringValueExpression expressions, NumberValueExpression expressions, VariableExpressions
     * @return
     */
    private SimpleExpression singleValuedExpression() {
        if (matchTokenWithType(TokenType.STRING)) {
            return new StringValueExpression(prevToken(1).getText());
        } else if (matchTokenWithType(TokenType.NUMBER)){
            return new NumberValueExpression(Double.parseDouble(prevToken(1).getText()));
        } else if (matchTokenWithType(TokenType.WORD)){
            return new VariableExpression(this.simple, prevToken(1).getText());
        }
        throw new Error("Couldn't parse");
    }

    /**
     * builds simple/composite expressions
     * @return
     */
    private SimpleExpression buildExpression() {
        SimpleExpression simpleExpression = singleValuedExpression();

        // Keep building operator expressions as long as we have operators.
        if (matchTokenWithType(TokenType.OPERATOR) ||
                matchTokenWithType(TokenType.EQUALS) || matchTokenWithType(TokenType.NOT_EQUALS)) {
            char operator = prevToken(1).getText().charAt(0);
            SimpleExpression right = singleValuedExpression();
            simpleExpression = new OperatorExpression(simpleExpression, operator, right);
        }

        return simpleExpression;
    }
}
