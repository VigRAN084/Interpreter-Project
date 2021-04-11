/**
 * Class representing an individual token in the Simple Programming Language
 */
public class SimpleToken {
    private int lineNumber;
    private String type;
    private String text;
    public SimpleToken(String te, String ty, int lineNumber){
        this.type = ty;
        this.text = te;
        this.lineNumber = lineNumber;
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

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }
}
