import java.io.*;
import java.util.*;
public class Simple {
    /**
     * Start interpreting contents of source code
     * @param contents
     */
    private int currentStatement;
    private Map<String, SimpleValue> memory;
    private Map<String, Integer> labels;
    private boolean compileErrors = false;
    private Scanner scanner = null;


    public Simple() {
        memory = new HashMap<String, SimpleValue>();
        labels = new HashMap<>();
        scanner = new Scanner(System.in);
    }
    public int getCurrentStatement() {
        return currentStatement;
    }

    public void setCurrentStatement(int currentStatement) {
        this.currentStatement = currentStatement;
    }

    public Map<String, SimpleValue> getMemory() {
        return memory;
    }

    public void setMemory(Map<String, SimpleValue> memory) {
        this.memory = memory;
    }

    public Map<String, Integer> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, Integer> labels) {
        this.labels = labels;
    }

    public boolean isCompileErrors() {
        return compileErrors;
    }

    public void setCompileErrors(boolean compileErrors) {
        this.compileErrors = compileErrors;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * start interpreting the program
     * @param contents
     */
    public void run(String contents){
        ArrayList<SimpleToken> simpleTokens = Tokenizer.extractTokens(contents);
        Parser parser = new Parser(this, simpleTokens);
        ArrayList<SimpleStatement> simpleStatements = parser.parseTokens();
        // Interpret until we're done.
        if (!this.compileErrors) executeStatements(simpleStatements);

    }

    private void executeStatements(ArrayList<SimpleStatement> simpleStatements) {
        currentStatement = 0;
        while (currentStatement < simpleStatements.size()) {
            int thisStatement = currentStatement;
            currentStatement++;
            simpleStatements.get(thisStatement).execute();
        }
    }

    /**
     * Read source code
     * @return
     * @throws FileNotFoundException
     */
    private static String readFile(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner sc = new Scanner (file);

        String s = "";
        while (sc.hasNextLine()){
            String line = sc.nextLine();
            if (sc.hasNextLine()){
                s += line + "\n";
            }
            else {
                s += line;
            }
        }
        s += "\n";
        return s;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String s = readFile(args[0]);
        Simple simple = new Simple();
        simple.run(s);
    }
}