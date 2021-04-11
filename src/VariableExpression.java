import java.util.Map;

public class VariableExpression implements SimpleExpression {
    private Simple simple;
    private String name;

    public VariableExpression(Simple simple, String name){
        this.simple = simple;
        this.name = name;
    }

    /**
     * stores the value of the variable in the memory
     * @return
     */
    @Override
    public ValueExpression evaluate() {
        Map<String, ValueExpression> memory = this.simple.getMemory();
        if (memory.containsKey(name)){
            return memory.get(name);
        }
        return new NumberValueExpression(0);
    }
}
