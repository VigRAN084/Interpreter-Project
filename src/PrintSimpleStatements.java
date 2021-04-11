public class PrintSimpleStatements implements SimpleStatements {
    private SimpleExpression simpleExpression;
    public PrintSimpleStatements(SimpleExpression e){
        this.simpleExpression = e;
    }
    @Override
    public void execute() {
        String toPrint = simpleExpression.evaluate().toString();
        System.out.println(toPrint);
    }
}
