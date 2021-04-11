import java.util.Map;

public class VariableSimpleExpression implements SimpleExpression {
    private Simple simple;
    private String name;

    public VariableSimpleExpression(Simple simple, String name){
        this.simple = simple;
        this.name = name;
    }

    @Override
    public SimpleValue evaluate() {
        Map<String, SimpleValue> memory = this.simple.getMemory();
        if (memory.containsKey(name)){
            return memory.get(name);
        }
        return new NumberSimpleValue(0);
    }
}
