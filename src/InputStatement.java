import java.util.*;
public class InputStatement implements SimpleStatement {
    private Simple simple;
    private String name;

    public InputStatement(Simple simple, String name){
        this.simple = simple;
        this.name = name;
    }

    @Override
    public void execute() {
        Scanner scanner = this.simple.getScanner();
        String s = scanner.nextLine();
        this.simple.getMemory().put(this.name, new StringValue(s));
    }
}
