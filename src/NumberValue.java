public class NumberValue implements SimpleValue {
    private double val;
    public NumberValue(double d){
        this.val = d;
    }
    @Override
    public double toNumber() {
        return val;
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }
    @Override
    public SimpleValue evaluate() {
        return this;
    }
    @Override
    public String getType() {
        return "number";
    }
}
