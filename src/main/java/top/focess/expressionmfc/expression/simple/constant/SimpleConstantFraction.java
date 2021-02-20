package top.focess.expressionmfc.expression.simple.constant;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.exception.DivideByZeroException;
import top.focess.expressionmfc.expression.*;
import top.focess.expressionmfc.expression.simple.SimpleExpression;
import top.focess.expressionmfc.expression.simple.SimpleFraction;
import top.focess.expressionmfc.operator.Operator;

public class SimpleConstantFraction extends SimpleFraction implements SimpleConstable {

    public static final SimpleConstantFraction ONE = new SimpleConstantFraction(SimpleConstantLong.ONE,SimpleConstantLong.ONE);

    private final SimpleConstant numerator;
    private final SimpleConstant denominator;

    public SimpleConstantFraction(SimpleConstant numerator, SimpleConstant denominator) {
        super(numerator,denominator);
        this.numerator = numerator;
        this.denominator = denominator;
    }

    @Override
    public double doubleValue() throws DivideByZeroException {
        if (this.denominator.isZero())
            throw new DivideByZeroException();
        return this.numerator.doubleValue() / this.denominator.doubleValue();
    }

    @Override
    public boolean isZero() throws DivideByZeroException {
        if (this.denominator.isZero())
            throw new DivideByZeroException();
        return this.numerator.isZero();
    }

    @Override
    public @NonNull SimpleConstant getNumerator() {
        return this.numerator;
    }

    @Override
    public @NonNull SimpleConstant getDenominator() {
        return this.denominator;
    }

    @Override
    @NonNull
    public SimpleConstantFraction clone() {
        return new SimpleConstantFraction(this.getNumerator(),this.getDenominator());
    }

    @Override
    public @NonNull SimpleConstable reverse() {
        return new SimpleConstantFraction(this.getNumerator().reverse(),this.getDenominator());
    }

    @Override
    public @NonNull SimpleConstable plus(SimpleConstable SimpleConstable) {
        if (SimpleConstable instanceof SimpleConstant)
            return new SimpleConstantFraction(Operator.PLUS.operate(this.getNumerator(),Operator.MULTIPLY.operate(this.getDenominator(), (SimpleConstant) SimpleConstable)), this.getDenominator());
        else {
            if (SimpleConstable instanceof SimpleConstantFraction)
                return new SimpleConstantFraction(Operator.PLUS.operate(Operator.MULTIPLY.operate(this.getNumerator(),((SimpleConstantFraction) SimpleConstable).getDenominator()),Operator.MULTIPLY.operate(this.getDenominator(),((SimpleConstantFraction) SimpleConstable).getNumerator())), Operator.MULTIPLY.operate(this.getDenominator(),((SimpleConstantFraction) SimpleConstable).getDenominator()));
            //todo
        }
        return null;
    }

    @Override
    public @NonNull SimpleConstable minus(SimpleConstable SimpleConstable) {
        if (SimpleConstable instanceof SimpleExpression)
            return new SimpleConstantFraction(Operator.MINUS.operate(this.getNumerator(),Operator.MULTIPLY.operate(this.getDenominator(),(SimpleConstant) SimpleConstable)), this.getDenominator());
        else {
            if (SimpleConstable instanceof SimpleConstantFraction)
                return new SimpleConstantFraction(Operator.MINUS.operate(Operator.MULTIPLY.operate(this.getNumerator(),((SimpleConstantFraction) SimpleConstable).getDenominator()),Operator.MULTIPLY.operate(this.getDenominator(),((SimpleConstantFraction) SimpleConstable).getNumerator())), Operator.MULTIPLY.operate(this.getDenominator(),((SimpleConstantFraction) SimpleConstable).getDenominator()));
            //todo
        }
        return null;
    }

    @Override
    public @NonNull SimpleConstable multiply(SimpleConstable SimpleConstable) {
        if (SimpleConstable instanceof SimpleExpression)
            return new SimpleConstantFraction(Operator.MULTIPLY.operate(this.getNumerator(),(SimpleConstant) SimpleConstable),this.getDenominator() );
        else {
            if (SimpleConstable instanceof SimpleConstantFraction)
                return new SimpleConstantFraction(Operator.MULTIPLY.operate(this.getNumerator(),((SimpleConstantFraction) SimpleConstable).getNumerator()),Operator.MULTIPLY.operate(this.getDenominator(),((SimpleConstantFraction) SimpleConstable).getDenominator()) );
            //todo
        }
        return null;
    }

    @Override
    @NonNull
    public SimpleConstantFraction value() {
        return this.clone();
    }

}
