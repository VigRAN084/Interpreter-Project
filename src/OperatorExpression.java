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
    public ValueExpression evaluate() {
        if (this.operator == '=')
            return handleEquals();
        else if (this.operator == '~')
            return handleNotEquals();
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
    private ValueExpression handleGreaterThan() {
        ValueExpression leftVal = left.evaluate();
        ValueExpression rightVal = right.evaluate();
        if (leftVal.getType().equals("number")) {
            if (leftVal.toNumber() > rightVal.toNumber())
                return new NumberValueExpression(1);
            else {
                return new NumberValueExpression(0);
            }
        }
        return new NumberValueExpression(0);
    }

    /**
     * check if leftValue is less than rightValuw
     * @return 1.0 or 0.0
     */
    private ValueExpression handleLessThan() {
        ValueExpression leftVal = left.evaluate();
        ValueExpression rightVal = right.evaluate();
        if (leftVal.getType().equals("number")) {
            if (leftVal.toNumber() < rightVal.toNumber())
                return new NumberValueExpression(1);
            else {
                return new NumberValueExpression(0);
            }
        }
        return new NumberValueExpression(0);
    }

    /**
     * division operator for numbers
     * @return quotient
     */
    private ValueExpression handleDivision() {
        ValueExpression leftVal = left.evaluate();
        ValueExpression rightVal = right.evaluate();
        return new NumberValueExpression(leftVal.toNumber() /
                rightVal.toNumber());
    }

    /**
     * multiply operator for numbers
     * @return product
     */
    private ValueExpression handleMultiply() {
        ValueExpression leftVal = left.evaluate();
        ValueExpression rightVal = right.evaluate();
        return new NumberValueExpression(leftVal.toNumber() *
                rightVal.toNumber());
    }

    /**
     * subtraction operator for numbers
     * @return difference
     */
    private ValueExpression handleSubtract() {
        ValueExpression leftVal = left.evaluate();
        ValueExpression rightVal = right.evaluate();
        return new NumberValueExpression(leftVal.toNumber() -
                rightVal.toNumber());
    }

    /**
     * addition operator for numbers
     * @return sum
     */
    private ValueExpression handleAdd() {
        ValueExpression leftVal = left.evaluate();
        ValueExpression rightVal = right.evaluate();
        if (leftVal.getType().equals("number")) {
            return new NumberValueExpression(leftVal.toNumber() +
                    rightVal.toNumber());
        } else {
            return new StringValueExpression(leftVal.toString() +
                    rightVal.toString());
        }
    }

    /**
     * checks if left = right
     * @return 1.0 or 0.0
     */
    private ValueExpression handleEquals() {
        ValueExpression leftVal = left.evaluate();
        ValueExpression rightVal = right.evaluate();
        if (leftVal.getType().equals("number")) {
            if (leftVal.toNumber() == rightVal.toNumber())
                return new NumberValueExpression(1);
            else {
                return new NumberValueExpression(0);
            }
        } else {
            if (leftVal.toString().equals(rightVal.toString()))
                return new NumberValueExpression(1);
            else {
                return new NumberValueExpression(0);
            }
        }
    }
    private ValueExpression handleNotEquals() {
        ValueExpression leftVal = left.evaluate();
        ValueExpression rightVal = right.evaluate();
        if (leftVal.getType().equals("number")){
            if (leftVal.toNumber() != rightVal.toNumber())
                return new NumberValueExpression(1);
            else {
                return new NumberValueExpression(0);
            }
        } else {
            if (!leftVal.toString().equals(rightVal.toString()))
                return new NumberValueExpression(1);
            else {
                return new NumberValueExpression(0);
            }
        }
    }

    /**
     * division operator for numbers
     * @return quotient
     */
    private ValueExpression handleModulus() {
        ValueExpression leftVal = left.evaluate();
        ValueExpression rightVal = right.evaluate();
        return new NumberValueExpression(leftVal.toNumber() %
                rightVal.toNumber());
    }

}

