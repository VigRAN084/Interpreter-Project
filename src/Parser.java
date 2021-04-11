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
                IfStatement stmt = buildIfStatement();
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

    private ScanStatement buildScanStatement() {
        String inputVar = consumeTokenWithType(TokenType.WORD).getText();
        return new ScanStatement(this.simple, inputVar);
    }

    private IfStatement buildIfStatement() {
        SimpleExpression condition = buildExpression();
        consumeTokenWithText("then");
        String label = consumeTokenWithType(TokenType.WORD).getText();
        return new IfStatement(this.simple, condition, label);
    }

    private JumptoStatement buildJumptoStatement() {
        SimpleToken labelName = consumeTokenWithType(TokenType.WORD);
        return new JumptoStatement(this.simple, labelName.getText());
    }

    private AssignStatement buildAssignStatement() {
        String name = prevToken(2).getText();
        SimpleExpression value = buildExpression();
        return new AssignStatement(this.simple, name, value);
    }

    private PrintStatement buildPrintStatement() {
        SimpleExpression printExpr = buildExpression();
        return new PrintStatement(printExpr);
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
        SimpleToken currToken = getCurrToken();
        if (!currToken.getType().equals(type)){
            throw new Error ("Expecting " + type);
        }
        else {
            position++;
            return currToken;
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

    private SimpleExpression singleValuedExpression() {
        if (matchTokenWithType(TokenType.STRING)) {
            return new StringValue(prevToken(1).getText());
        } else if (matchTokenWithType(TokenType.NUMBER)){
            return new NumberValue(Double.parseDouble(prevToken(1).getText()));
        } else if (matchTokenWithType(TokenType.WORD)){
            return new VariableExpression(this.simple, prevToken(1).getText());
        }
        throw new Error("Couldn't parse");
    }

    private SimpleExpression buildExpression() {
        SimpleExpression simpleExpression = singleValuedExpression();

        // Keep building operator expressions as long as we have operators.
        if (matchTokenWithType(TokenType.OPERATOR) ||
                matchTokenWithType(TokenType.EQUALS)) {
            char operator = prevToken(1).getText().charAt(0);
            SimpleExpression right = singleValuedExpression();
            simpleExpression = new OperatorExpression(simpleExpression, operator, right);
        }

        return simpleExpression;
    }
}
