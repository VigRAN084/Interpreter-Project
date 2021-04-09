public class NumberValue implements Value {
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
    public Value evaluate() {
        return this;
    }
}
