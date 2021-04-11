public class IfStatement implements SimpleStatement {
    private Simple simple;
    private SimpleExpression simpleExpression;
    private String label;
    public IfStatement(Simple simple, SimpleExpression simpleExpression, String label){
        this.simple = simple;
        this.simpleExpression = simpleExpression;
        this.label = label;
    }

    /**
     * If the expression evaluates to a nonzero value, then jump to the label
     */
    public void execute(){
        if (this.simple.getLabels().containsKey(this.label)){
            double value = this.simpleExpression.evaluate().toNumber();
            if (value != 0){
                this.simple.setCurrentStatement(this.simple.getLabels().get(this.label).intValue());
            }
        }
    }
}
