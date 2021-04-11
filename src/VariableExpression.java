import java.util.Map;

public class VariableExpression implements SimpleExpression {
    private Simple simple;
    private String name;

    public VariableExpression(Simple simple, String name){
        this.simple = simple;
        this.name = name;
    }

    @Override
    public SimpleValue evaluate() {
        Map<String, SimpleValue> memory = this.simple.getMemory();
        if (memory.containsKey(name)){
            return memory.get(name);
        }
        return new NumberValue(0);
    }
}
