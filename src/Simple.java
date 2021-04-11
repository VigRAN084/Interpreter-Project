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

    public Simple() {
        memory = new HashMap<String, SimpleValue>();
        labels = new HashMap<>();
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

    public void interpret(String contents){
        //generate tokens
        //skim unwanted comments, etc.
        ArrayList<Token> tokens = Tokenizer.extractTokens(contents);
        Parser parser = new Parser(this, tokens);
        ArrayList<SimpleStatements> simpleStatements = parser.parseTokens();
        // Interpret until we're done.
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
        String s = readFile("/Users/vignesh/Desktop/JavaProjects/compilerproject/hello.simple");
        Simple simple = new Simple();
        simple.interpret(s);
    }
}