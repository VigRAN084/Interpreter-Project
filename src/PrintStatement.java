public class PrintStatement implements SimpleStatement {
    private SimpleExpression simpleExpression;
    public PrintStatement(SimpleExpression e){
        this.simpleExpression = e;
    }
    @Override
    public void execute() {
        String toPrint = simpleExpression.evaluate().toString();
        System.out.println(toPrint);
    }
}
