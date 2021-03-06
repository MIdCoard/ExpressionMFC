package top.focess.expressionmfc.expression.simple;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.argument.Argument;
import top.focess.expressionmfc.expression.SelfExpression;
import top.focess.expressionmfc.expression.Simplifiable;
import top.focess.expressionmfc.operator.Operator;

import java.util.List;

public abstract class SimpleExpression extends SelfExpression implements Simplifiable {

    @Override
    public @NonNull Simplifiable simplify() {
        return this;
    }

    @Override
    @NonNull
    public abstract SimpleExpression clone();

    @NonNull
    public abstract SimpleExpression plus(SimpleExpression simpleExpression);

    @NonNull
    public abstract SimpleExpression minus(SimpleExpression simpleExpression);

    @NonNull
    public abstract SimpleExpression multiply(SimpleExpression simpleExpression);

    @Override
    public @NonNull Simplifiable plus(Simplifiable simplifiable) {
        return plus((SimpleExpression) simplifiable);
    }

    @Override
    public @NonNull Simplifiable minus(Simplifiable simplifiable) {
        return minus((SimpleExpression) simplifiable);
    }

    @Override
    public @NonNull Simplifiable multiply(Simplifiable simplifiable) {
        return multiply((SimpleExpression) simplifiable);
    }

    @Override
    public @NonNull Simplifiable divided(Simplifiable simplifiable) {
        if (simplifiable instanceof SimpleExpression)
            return new SimpleFraction(this, (SimpleExpression) simplifiable);
        else {
            if (simplifiable instanceof SimpleFraction) {
                return new SimpleFraction(Operator.MULTIPLY.operate(this, ((SimpleFraction) simplifiable).getDenominator()), ((SimpleFraction) simplifiable).getNumerator());
            }
        }
        return null;
    }

    @NonNull
    public abstract SimpleExpression reverse();

    @NonNull
    public abstract List<Argument> getSameArguments();

    public abstract @NonNull Simplifiable removeSameArguments(List<Argument> arguments);
}
