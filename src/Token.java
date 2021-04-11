import java.util.ArrayList;

public class Token {



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


}
