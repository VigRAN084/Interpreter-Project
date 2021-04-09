public class PrintStatement implements Statement {
    private Expression expression;
    public PrintStatement(Expression e){
        this.expression = e;
    }
    @Override
    public void execute() {
        String toPrint = expression.evaluate().toString();
        System.out.println(toPrint);
    }
}
