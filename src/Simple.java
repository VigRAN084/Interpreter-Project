import java.io.*;
import java.util.*;
public class Simple {
    /**
     * Start interpreting contents of source code
     * @param contents
     */
    private int currentStatement;
    private Map<String, Value> memory;

    public Simple() {
        memory = new HashMap<String, Value>();
    }
    public int getCurrentStatement() {
        return currentStatement;
    }

    public void setCurrentStatement(int currentStatement) {
        this.currentStatement = currentStatement;
    }

    public Map<String, Value> getMemory() {
        return memory;
    }

    public void setMemory(Map<String, Value> memory) {
        this.memory = memory;
    }

    public void interpret(String contents){
        //generate tokens
        //skim unwanted comments, etc.
        ArrayList<Token> tokens = Token.generateTokens(contents);
        Parser parser = new Parser(this, tokens);
        ArrayList<Statement> statements = parser.parseTokens();
        // Interpret until we're done.
        currentStatement = 0;
        while (currentStatement < statements.size()) {
            int thisStatement = currentStatement;
            currentStatement++;
            statements.get(thisStatement).execute();
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
        String s = readFile("/Users/vignesh/Desktop/JavaProjects/CompilerProj/src/Program");
        Simple simple = new Simple();
        simple.interpret(s);
    }
}