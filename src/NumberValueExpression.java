public class NumberValueExpression implements ValueExpression {
    private double val;
    public NumberValueExpression(double d){
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

    /**
     * returns the NumberValueExpression
     * @return
     */
    @Override
    public ValueExpression evaluate() {
        return this;
    }
    @Override
    public String getType() {
        return "number";
    }
}
