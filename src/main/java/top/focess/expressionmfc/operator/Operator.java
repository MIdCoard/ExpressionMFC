package top.focess.expressionmfc.operator;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.expression.Constable;
import top.focess.expressionmfc.expression.IExpression;
import top.focess.expressionmfc.expression.Simplifiable;
import top.focess.expressionmfc.expression.simple.SimpleExpression;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstable;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstant;

public enum Operator {
    PLUS("+", 0), MINUS("-", 0), MULTIPLY("*", 1), DIVIDED("/", 1);


    private final String name;
    private final int priority;

    private Operator(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    public static boolean isOperator(String op) {
        for (Operator operator : Operator.values())
            if (operator.getName().equalsIgnoreCase(op))
                return true;
        return false;
    }

    public static Operator getOperator(String op) {
        for (Operator operator : Operator.values())
            if (operator.getName().equalsIgnoreCase(op))
                return operator;
        return null;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    @NonNull
    public IExpression operate(IExpression a, IExpression b) {
        switch (this) {
            case PLUS:
                return a.plus(b);
            case MINUS:
                return a.minus(b);
            case DIVIDED:
                return a.divided(b);
            case MULTIPLY:
                return a.multiply(b);
            default:
                return null;
        }
    }

    @NonNull
    public Simplifiable operate(Simplifiable a, Simplifiable b) {
        if (this == PLUS || this == MULTIPLY) {
            if (a instanceof SimpleExpression && !(b instanceof SimpleExpression))
                return operate(b, a);
            else if (a instanceof SimpleExpression) {
                if (a instanceof Constable && !(b instanceof Constable))
                    return operate(b, a);
            }
        } else if (this == MINUS) {
            if (a instanceof SimpleExpression && !(b instanceof SimpleExpression))
                return operate(b, a).reverse();
            else if (a instanceof SimpleExpression) {
                if (a instanceof Constable && !(b instanceof Constable))
                    return operate(b, a).reverse();
            }
        }
        switch (this) {
            case PLUS:
                return a.plus(b);
            case MINUS:
                return a.minus(b);
            case DIVIDED:
                return a.divided(b);
            case MULTIPLY:
                return a.multiply(b);
            default:
                return null;
        }
    }

    @NonNull
    public SimpleExpression operate(SimpleExpression a, SimpleExpression b) {
        if (this == PLUS || this == MULTIPLY) {
            if (a instanceof Constable && !(b instanceof Constable))
                return operate(b, a);
        } else if (this == MINUS) {
            if (a instanceof Constable && !(b instanceof Constable))
                return operate(b, a).reverse();
        }
        switch (this) {
            case PLUS:
                return a.plus(b);
            case MINUS:
                return a.minus(b);
            case MULTIPLY:
                return a.multiply(b);
            default:
                return null;
        }
    }

    @NonNull
    public SimpleConstable operate(SimpleConstable a, SimpleConstable b) {
        if (this == PLUS || this == MULTIPLY) {
            if (a instanceof SimpleConstant && !(b instanceof SimpleConstant))
                return operate(b, a);
        } else if (this == MINUS) {
            if (a instanceof SimpleConstant && !(b instanceof SimpleConstant))
                return operate(b, a).reverse();
        }
        switch (this) {
            case PLUS:
                return a.plus(b);
            case MINUS:
                return a.minus(b);
            case MULTIPLY:
                return a.multiply(b);
            default:
                return null;
        }
    }

    @NonNull
    public SimpleConstant operate(SimpleConstant a, SimpleConstant b) {
        switch (this) {
            case PLUS:
                return a.plus(b);
            case MINUS:
                return a.minus(b);
            case MULTIPLY:
                return a.multiply(b);
            default:
                return null;
        }
    }

    public double operate(double a, double b) {
        switch (this) {
            case PLUS:
                return a + b;
            case MINUS:
                return a - b;
            case MULTIPLY:
                return a * b;
            case DIVIDED:
                return a / b;
        }
        return 0;
    }
}
