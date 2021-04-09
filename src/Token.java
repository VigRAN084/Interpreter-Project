import java.util.ArrayList;

public class Token {

    public static String TOKENIZE_STATE_DEFAULT = "DEFAULT";
    public static String TOKENIZE_STATE_WORD = "WORD";
    public static String TOKENIZE_STATE_STRING = "STRING";
    public static String TOKENIZE_STATE_COMMENT = "COMMENT";
    public static String TOKENIZE_STATE_NUMBER = "NUMBER";

    private String type;
    private String text;
    public Token (String te, String ty){
        this.type = ty;
        this.text = te;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static ArrayList<Token> generateTokens (String source){
        ArrayList<Token> tokens = new ArrayList<>();
        String token = "";
        String state = TOKENIZE_STATE_DEFAULT;

        // Many tokens are a single character, like operators and ().
        String charTokens = "\n=+-*/<>()";
        String[] tokenTypes = { TokenType.LINE, TokenType.EQUALS,
                TokenType.OPERATOR, TokenType.OPERATOR, TokenType.OPERATOR,
                TokenType.OPERATOR, TokenType.OPERATOR, TokenType.OPERATOR,
                TokenType.LEFT_PAREN, TokenType.RIGHT_PAREN
        };
        for (int i = 0; i < source.length(); i++){
            char c = source.charAt(i);
            if (state.equals(TOKENIZE_STATE_DEFAULT)){
                if (charTokens.indexOf(c) != -1){
                    tokens.add(new Token(Character.toString(c), tokenTypes[charTokens.indexOf(c)]));
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
