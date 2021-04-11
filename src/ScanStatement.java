import java.util.*;
public class ScanStatement implements SimpleStatement {
    private Simple simple;
    private String name;

    /**
     * constructor
     * @param simple
     * @param name
     */
    public ScanStatement(Simple simple, String name){
        this.simple = simple;
        this.name = name;
    }

    /**
     * Gathers user input from the scanner and stores it in the memory under the variable "name"
     */
    @Override
    public void execute() {
        Scanner scanner = this.simple.getScanner();
        String s = scanner.nextLine();
        this.simple.getMemory().put(this.name, new StringValueExpression(s));
    }
}
