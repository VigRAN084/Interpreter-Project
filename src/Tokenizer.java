import java.util.ArrayList;

public class Tokenizer {
    private static String TOKENIZE_STATE_DEFAULT = "DEFAULT";
    private static String TOKENIZE_STATE_WORD = "WORD";
    private static String TOKENIZE_STATE_STRING = "STRING";
    private static String TOKENIZE_STATE_COMMENT = "COMMENT";
    private static String TOKENIZE_STATE_NUMBER = "NUMBER";

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
        String state = TOKENIZE_STATE_DEFAULT;
        int lineNumber = 1;
        // Many simpleTokens are a single character, like operators and ().

        for (int i = 0; i < source.length(); i++){
            char c = source.charAt(i);
            if (state.equals(TOKENIZE_STATE_DEFAULT)){
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
                    state= TOKENIZE_STATE_WORD;
                } else if (Character.isDigit(c)){
                    token += c;
                    state = TOKENIZE_STATE_NUMBER;
                } else if (c == '"'){
                    state = TOKENIZE_STATE_STRING;
                } else if (c == '#'){
                    state = TOKENIZE_STATE_COMMENT;
                }

            } else if (state.equals(TOKENIZE_STATE_WORD)){
                if (Character.isLetterOrDigit(c)) {
                    token += c;
                } else if (c == ':') {
                    simpleTokens.add(new SimpleToken(token, TokenType.LABEL, lineNumber));
                    token = "";
                    state = TOKENIZE_STATE_DEFAULT;
                } else {
                    simpleTokens.add(new SimpleToken(token, TokenType.WORD, lineNumber));
                    token = "";
                    state = TOKENIZE_STATE_DEFAULT;
                    i--; // Reprocess this character in the default state.
                }


            } else if (state.equals(TOKENIZE_STATE_NUMBER)){
                if (Character.isDigit(c)) {
                    token += c;
                } else {
                    simpleTokens.add(new SimpleToken(token, TokenType.NUMBER, lineNumber));
                    token = "";
                    state = TOKENIZE_STATE_DEFAULT;
                    i--; // Reprocess this character in the default state.
                }
            } else if (state.equals(TOKENIZE_STATE_STRING)){
                if (c == '"') {
                    simpleTokens.add(new SimpleToken(token, TokenType.STRING, lineNumber));
                    token = "";
                    state = TOKENIZE_STATE_DEFAULT;
                } else {
                    token += c;
                }

            } else if (state.equals(TOKENIZE_STATE_COMMENT)){
                if (c == '\n') {
                    state = TOKENIZE_STATE_DEFAULT;
                    lineNumber++;
                }
            }
        }
        return simpleTokens;
    }
}
