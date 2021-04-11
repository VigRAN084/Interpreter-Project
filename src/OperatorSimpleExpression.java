public class OperatorSimpleExpression implements SimpleExpression {

    private final SimpleExpression left;
    private final char operator;
    private final SimpleExpression right;
    public OperatorSimpleExpression(SimpleExpression left, char operator,
                                    SimpleExpression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

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
                return new NumberSimpleValue(1);
            else {
                return new NumberSimpleValue(0);
            }
        }
        return new NumberSimpleValue(0);
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
                return new NumberSimpleValue(1);
            else {
                return new NumberSimpleValue(0);
            }
        }
        return new NumberSimpleValue(0);
    }

    /**
     * division operator for numbers
     * @return quotient
     */
    private SimpleValue handleDivision() {
        SimpleValue leftVal = left.evaluate();
        SimpleValue rightVal = right.evaluate();
        return new NumberSimpleValue(leftVal.toNumber() /
                rightVal.toNumber());
    }

    /**
     * multiply operator for numbers
     * @return product
     */
    private SimpleValue handleMultiply() {
        SimpleValue leftVal = left.evaluate();
        SimpleValue rightVal = right.evaluate();
        return new NumberSimpleValue(leftVal.toNumber() *
                rightVal.toNumber());
    }

    /**
     * subtraction operator for numbers
     * @return difference
     */
    private SimpleValue handleSubtract() {
        SimpleValue leftVal = left.evaluate();
        SimpleValue rightVal = right.evaluate();
        return new NumberSimpleValue(leftVal.toNumber() -
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
            return new NumberSimpleValue(leftVal.toNumber() +
                    rightVal.toNumber());
        } else {
            return new StringSimpleValue(leftVal.toString() +
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
                return new NumberSimpleValue(1);
            else {
                return new NumberSimpleValue(0);
            }
        } else {
            if (leftVal.toString().equals(rightVal.toString()))
                return new NumberSimpleValue(1);
            else {
                return new NumberSimpleValue(0);
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
        return new NumberSimpleValue(leftVal.toNumber() %
                rightVal.toNumber());
    }

}
