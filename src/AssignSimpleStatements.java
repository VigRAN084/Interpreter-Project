public class AssignSimpleStatements implements SimpleStatements {
    private String name;
    private SimpleExpression value;
    private Simple simple;
    public AssignSimpleStatements(Simple simple, String name, SimpleExpression value){
        this.name = name;
        this.simple = simple;
        this.value = value;
    }
    @Override
    public void execute() {
        simple.getMemory().put(this.name, this.value.evaluate());
    }
}