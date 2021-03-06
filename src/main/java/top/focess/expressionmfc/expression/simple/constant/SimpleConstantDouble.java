package top.focess.expressionmfc.expression.simple.constant;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.util.MathHelper;

public class SimpleConstantDouble extends SimpleConstant {

    public static final SimpleConstantDouble ZERO = new SimpleConstantDouble(0);

    private final double value;

    public SimpleConstantDouble(double value) {
        this.value = value;
    }

    @Override
    public double doubleValue() {
        return this.value;
    }

    @Override
    public boolean isZero() {
        return MathHelper.abs(this.value) < 1e-15;
    }

    @Override
    public @NonNull SimpleConstantDouble clone() {
        return new SimpleConstantDouble(this.value);
    }

    @Override
    public @NonNull SimpleConstant reverse() {
        return new SimpleConstantDouble(-this.value);
    }

    @Override
    public @NonNull SimpleConstant plus(SimpleConstant simpleConstant) {
        return new SimpleConstantDouble(this.value + simpleConstant.getValue().doubleValue());
    }

    @Override
    public @NonNull SimpleConstant minus(SimpleConstant simpleConstant) {
        return new SimpleConstantDouble(this.value - simpleConstant.getValue().doubleValue());
    }

    @Override
    public @NonNull SimpleConstant multiply(SimpleConstant simpleConstant) {
        return new SimpleConstantDouble(this.value * simpleConstant.getValue().doubleValue());
    }

    @Override
    public @NonNull Double getValue() {
        return this.value;
    }

    @NonNull
    public SimpleConstantLong toLong() {
        return new SimpleConstantLong((long) this.value);
    }

    @NonNull
    public SimpleConstantFraction toFraction(int range) {
        if (range < 0 || range > 15)
            range = 0;
        if (range == 0)
            return new SimpleConstantFraction(this, SimpleConstantLong.ONE);
        else {
            double v = MathHelper.pow(10, range);
            return new SimpleConstantFraction(new SimpleConstantDouble(this.value * v), new SimpleConstantLong((long) v));
        }
    }

    @NonNull
    public SimpleConstantFraction toFraction() {
        return toFraction(0);
    }

    @Override
    @NonNull
    public String toString() {
        return String.valueOf(this.value);
    }
}
