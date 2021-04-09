public class AssignStatement implements Statement{
    private String name;
    private Expression value;
    private Simple simple;
    public AssignStatement(Simple simple, String name, Expression value){
        this.name = name;
        this.simple = simple;
        this.value = value;
    }
    @Override
    public void execute() {
        simple.getMemory().put(this.name, this.value.evaluate());
    }
}
