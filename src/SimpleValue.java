/**
 * Interface denoting values supported by Simple Programming Language
 * Supported value are numbers and string
 */
public interface SimpleValue extends SimpleExpression {
    String toString();
    double toNumber();
    String getType();
}
