public class StringValueExpression implements ValueExpression {
    private String val;
    public StringValueExpression(String s){
        this.val = s;
    }
    @Override
    public double toNumber() {
        return Double.parseDouble(val);
    }

    @Override
    public String toString() {
        return val;
    }

    @Override
    public String getType() {
        return "string";
    }

    /**
     * returns the string value
     * @return
     */
    @Override
    public ValueExpression evaluate() {
        return this;
    }
}
