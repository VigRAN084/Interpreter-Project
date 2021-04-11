public class AssignStatement implements SimpleStatement {
    private String name;
    private SimpleExpression value;
    private Simple simple;
    public AssignStatement(Simple simple, String name, SimpleExpression value){
        this.name = name;
        this.simple = simple;
        this.value = value;
    }

    /**
     * stores in the memory the value of the variable under the given name
     */
    @Override
    public void execute() {
        simple.getMemory().put(this.name, this.value.evaluate());
    }
}
