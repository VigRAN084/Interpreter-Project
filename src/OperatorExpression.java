public class OperatorExpression implements SimpleExpression {

    private final SimpleExpression left;
    private final char operator;
    private final SimpleExpression right;

    /**
     * constructor fo OperatorExpression.java
     * @param left - left expression
     * @param operator - such as +,-,%,etc.
     * @param right - right expression
     */
    public OperatorExpression(SimpleExpression left, char operator,
                              SimpleExpression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    /**
     * Checks the type of operator and performs the operation accordingly
     * @return
     */
    public SimpleValue evaluate() {
        if (this.operator == '=')
            return handleEquals();
        else if (this.operator == '+')
            return handleAdd();
        else if (this.operator == '-')
            return handleSubtract();
        else if (this.operator == '*')
            return handleMultiply();
        else if (this.operator == '/')
            return handleDivision();
        else if (this.operator == '%')
            return handleModulus();
        else if (this.operator == '<')
            return handleLessThan();
        else if (this.operator == '>')
            return handleGreaterThan();
        throw new Error("Unknown operator.");
    }

    /**
     * checks if leftValue is greater
     * @return 1.0 or 0.0
     */
    private SimpleValue handleGreaterThan() {
        SimpleValue leftVal = left.evaluate();
        SimpleValue rightVal = right.evaluate();
        if (leftVal.getType().equals("number")) {
            if (leftVal.toNumber() > rightVal.toNumber())
                return new NumberValue(1);
            else {
                return new NumberValue(0);
            }
        }
        return new NumberValue(0);
    }

    /**
     * check if leftValue is less than rightValuw
     * @return 1.0 or 0.0
     */
    private SimpleValue handleLessThan() {
        SimpleValue leftVal = left.evaluate();
        SimpleValue rightVal = right.evaluate();
        if (leftVal.getType().equals("number")) {
            if (leftVal.toNumber() < rightVal.toNumber())
                return new NumberValue(1);
            else {
                return new NumberValue(0);
            }
        }
        return new NumberValue(0);
    }

    /**
     * division operator for numbers
     * @return quotient
     */
    private SimpleValue handleDivision() {
        SimpleValue leftVal = left.evaluate();
        SimpleValue rightVal = right.evaluate();
        return new NumberValue(leftVal.toNumber() /
                rightVal.toNumber());
    }

    /**
     * multiply operator for numbers
     * @return product
     */
    private SimpleValue handleMultiply() {
        SimpleValue leftVal = left.evaluate();
        SimpleValue rightVal = right.evaluate();
        return new NumberValue(leftVal.toNumber() *
                rightVal.toNumber());
    }

    /**
     * subtraction operator for numbers
     * @return difference
     */
    private SimpleValue handleSubtract() {
        SimpleValue leftVal = left.evaluate();
        SimpleValue rightVal = right.evaluate();
        return new NumberValue(leftVal.toNumber() -
                rightVal.toNumber());
    }

    /**
     * addition operator for numbers
     * @return sum
     */
    private SimpleValue handleAdd() {
        SimpleValue leftVal = left.evaluate();
        SimpleValue rightVal = right.evaluate();
        if (leftVal.getType().equals("number")) {
            return new NumberValue(leftVal.toNumber() +
                    rightVal.toNumber());
        } else {
            return new StringValue(leftVal.toString() +
                    rightVal.toString());
        }
    }

    /**
     * checks if left = right
     * @return 1.0 or 0.0
     */
    private SimpleValue handleEquals() {
        SimpleValue leftVal = left.evaluate();
        SimpleValue rightVal = right.evaluate();
        if (leftVal.getType().equals("number")) {
            if (leftVal.toNumber() == rightVal.toNumber())
                return new NumberValue(1);
            else {
                return new NumberValue(0);
            }
        } else {
            if (leftVal.toString().equals(rightVal.toString()))
                return new NumberValue(1);
            else {
                return new NumberValue(0);
            }
        }
    }

    /**
     * division operator for numbers
     * @return quotient
     */
    private SimpleValue handleModulus() {
        SimpleValue leftVal = left.evaluate();
        SimpleValue rightVal = right.evaluate();
        return new NumberValue(leftVal.toNumber() %
                rightVal.toNumber());
    }

}

