public class IfElseStatement implements SimpleStatement {
    private Simple simple;
    private SimpleExpression simpleExpression;
    private String ifLabel;
    private String elseLabel;

    /**
     * constructor when no else statement present
     * @param simple
     * @param simpleExpression
     * @param ifLabel
     */
    public IfElseStatement(Simple simple, SimpleExpression simpleExpression, String ifLabel){
        this.simple = simple;
        this.simpleExpression = simpleExpression;
        this.ifLabel = ifLabel;
    }
    /**
     * constructor when else statement present
     * @param simple
     * @param simpleExpression
     * @param ifLabel
     */
    public IfElseStatement(Simple simple, SimpleExpression simpleExpression, String ifLabel, String elseLabel){
        this.simple = simple;
        this.simpleExpression = simpleExpression;
        this.ifLabel = ifLabel;
        this.elseLabel = elseLabel;
    }

    /**
     * If the expression evaluates to a nonzero value, then jump to the ifLabel
     */
    public void execute(){
        if (this.simple.getLabels().containsKey(this.ifLabel)){
            double value = this.simpleExpression.evaluate().toNumber();
            boolean trueValue = (value != 0);
            if (trueValue){
                Integer labelIndex = this.simple.getLabels().get(this.ifLabel);
                this.simple.setCurrentStatement(labelIndex.intValue());
            } else {
                if (elseLabel != null) {
                    Integer labelIndex = this.simple.getLabels().get(this.elseLabel);
                    this.simple.setCurrentStatement(labelIndex.intValue());
                }
            }
        }
    }
}
