public class StringValue implements Value{
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
    public Value evaluate() {
        return this;
    }
}
