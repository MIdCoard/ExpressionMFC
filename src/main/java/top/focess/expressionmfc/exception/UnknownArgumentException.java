package top.focess.expressionmfc.exception;

import top.focess.expressionmfc.argument.UnknownArgument;

public class UnknownArgumentException extends ExpressionException {


    public UnknownArgumentException(UnknownArgument argument) {
        super("Argument \"" + argument.getName() + "\" is unknown.");
    }
}
