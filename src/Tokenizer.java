import java.util.ArrayList;

public class Tokenizer {
    //Default state if not word, string, comment, or number
    private static String DEFAULT_STATE = "DEFAULT";
    //word state if starts with a letter and can end with a colon in the case of labels
    private static String WORD_STATE = "WORD";
    //if starts with a double quote and ends with a double quote
    private static String STRING_STATE = "STRING";
    //if character is a '#', or a comment
    private static String COMMENT_STATE = "COMMENT";
    //if character starts with a digit and ends with a digit
    private static String NUMBER_STATE = "NUMBER";

    private static String charTokens = "=+-*/%<>";
    private static String[] tokenTypes = { TokenType.EQUALS,
            TokenType.OPERATOR, TokenType.OPERATOR, TokenType.OPERATOR,
            TokenType.OPERATOR, TokenType.OPERATOR, TokenType.OPERATOR,
            TokenType.OPERATOR
    };
    /**
     * generate simpleTokens
     * skim unwanted comments, etc.
     * @param source
     * @return
     */
    public static ArrayList<SimpleToken> extractTokens (String source){
        ArrayList<SimpleToken> simpleTokens = new ArrayList<>();
        String token = "";
        String state = DEFAULT_STATE;
        int lineNumber = 1;

        for (int i = 0; i < source.length(); i++){
            char c = source.charAt(i);
            if (state.equals(DEFAULT_STATE)){
                if (charTokens.indexOf(c) != -1){
                    String tokenChar = Character.toString(c);
                    String tokenType = tokenTypes[charTokens.indexOf(c)];
                    simpleTokens.add(new SimpleToken(tokenChar, tokenType, lineNumber));
                } else if (c == '\n'){
                    String tokenChar = Character.toString(c);
                    String tokenType = TokenType.LINE;
                    lineNumber++;
                    simpleTokens.add(new SimpleToken(tokenChar, tokenType, lineNumber));
                } else if (Character.isLetter(c)){
                    token += c;
                    state= WORD_STATE;
                } else if (Character.isDigit(c)){
                    token += c;
                    state = NUMBER_STATE;
                } else if (c == '"'){
                    state = STRING_STATE;
                } else if (c == '#'){
                    state = COMMENT_STATE;
                }

            } else if (state.equals(WORD_STATE)){
                if (Character.isLetterOrDigit(c)) {
                    token += c;
                } else if (c == ':') {
                    simpleTokens.add(new SimpleToken(token, TokenType.LABEL, lineNumber));
                    token = "";
                    state = DEFAULT_STATE;
                } else {
                    simpleTokens.add(new SimpleToken(token, TokenType.WORD, lineNumber));
                    token = "";
                    state = DEFAULT_STATE;
                    i--;
                }


            } else if (state.equals(NUMBER_STATE)){
                if (Character.isDigit(c)) {
                    token += c;
                } else {
                    simpleTokens.add(new SimpleToken(token, TokenType.NUMBER, lineNumber));
                    token = "";
                    state = DEFAULT_STATE;
                    i--;
                }
            } else if (state.equals(STRING_STATE)){
                if (c == '"') {
                    simpleTokens.add(new SimpleToken(token, TokenType.STRING, lineNumber));
                    token = "";
                    state = DEFAULT_STATE;
                } else {
                    token += c;
                }

            } else if (state.equals(COMMENT_STATE)){
                if (c == '\n') {
                    state = DEFAULT_STATE;
                    lineNumber++;
                }
            }
        }
        return simpleTokens;
    }
}
