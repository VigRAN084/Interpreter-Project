import java.util.ArrayList;

public class Tokenizer {
    private static String TOKENIZE_STATE_DEFAULT = "DEFAULT";
    private static String TOKENIZE_STATE_WORD = "WORD";
    private static String TOKENIZE_STATE_STRING = "STRING";
    private static String TOKENIZE_STATE_COMMENT = "COMMENT";
    private static String TOKENIZE_STATE_NUMBER = "NUMBER";

    private static String charTokens = "\n=+-*/%<>";
    private static String[] tokenTypes = { TokenType.LINE, TokenType.EQUALS,
            TokenType.OPERATOR, TokenType.OPERATOR, TokenType.OPERATOR,
            TokenType.OPERATOR, TokenType.OPERATOR, TokenType.OPERATOR,
            TokenType.OPERATOR
    };

    public static ArrayList<Token> extractTokens (String source){
        ArrayList<Token> tokens = new ArrayList<>();
        String token = "";
        String state = TOKENIZE_STATE_DEFAULT;

        // Many tokens are a single character, like operators and ().

        for (int i = 0; i < source.length(); i++){
            char c = source.charAt(i);
            if (state.equals(TOKENIZE_STATE_DEFAULT)){
                if (charTokens.indexOf(c) != -1){
                    String tokenChar = Character.toString(c);
                    String tokenType = tokenTypes[charTokens.indexOf(c)];
                    tokens.add(new Token(tokenChar, tokenType));
                } else if (Character.isLetter(c)){
                    token += c;
                    state= TOKENIZE_STATE_WORD;
                } else if (Character.isDigit(c)){
                    token += c;
                    state = TOKENIZE_STATE_NUMBER;
                } else if (c == '"'){
                    state = TOKENIZE_STATE_STRING;
                } else if (c == '\''){
                    state = TOKENIZE_STATE_COMMENT;
                }

            } else if (state.equals(TOKENIZE_STATE_WORD)){
                if (Character.isLetterOrDigit(c)) {
                    token += c;
                } else if (c == ':') {
                    tokens.add(new Token(token, TokenType.LABEL));
                    token = "";
                    state = TOKENIZE_STATE_DEFAULT;
                } else {
                    tokens.add(new Token(token, TokenType.WORD));
                    token = "";
                    state = TOKENIZE_STATE_DEFAULT;
                    i--; // Reprocess this character in the default state.
                }


            } else if (state.equals(TOKENIZE_STATE_NUMBER)){
                if (Character.isDigit(c)) {
                    token += c;
                } else {
                    tokens.add(new Token(token, TokenType.NUMBER));
                    token = "";
                    state = TOKENIZE_STATE_DEFAULT;
                    i--; // Reprocess this character in the default state.
                }
            } else if (state.equals(TOKENIZE_STATE_STRING)){
                if (c == '"') {
                    tokens.add(new Token(token, TokenType.STRING));
                    token = "";
                    state = TOKENIZE_STATE_DEFAULT;
                } else {
                    token += c;
                }

            } else if (state.equals(TOKENIZE_STATE_COMMENT)){
                if (c == '\n') {
                    state = TOKENIZE_STATE_DEFAULT;
                }
            }
        }
        return tokens;
    }
}
