public class GotoSimpleStatements implements SimpleStatements {
    private Simple simple;
    private String label;
    public GotoSimpleStatements(Simple simple, String label){
        this.label = label;
        this.simple = simple;
    }
    public void execute() {
        if (this.simple.getLabels().containsKey(this.label)){
            this.simple.setCurrentStatement(simple.getLabels().get(label).intValue());
        }
    }
}
