public class StringValue implements SimpleValue {
    private String val;
    public StringValue(String s){
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

    @Override
    public SimpleValue evaluate() {
        return this;
    }
}
