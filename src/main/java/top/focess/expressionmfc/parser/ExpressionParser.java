package top.focess.expressionmfc.parser;

import com.google.common.collect.Lists;
import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.argument.Argument;
import top.focess.expressionmfc.exception.InvalidExpressionException;
import top.focess.expressionmfc.expression.IExpression;
import top.focess.expressionmfc.expression.multi.MultiExpression;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstantDouble;
import top.focess.expressionmfc.operator.Operator;

import java.util.List;
import java.util.Stack;

public class ExpressionParser {

    private final String expressionStr;
    private final List<Argument> arguments;
    private MultiExpression expression;

    public ExpressionParser(String expression) throws InvalidExpressionException {
        this.expressionStr = expression;
        this.arguments = Lists.newArrayList();
        this.parse();
    }

    private boolean isBracket;

    private void parse() throws InvalidExpressionException {
        this.isBracket = false;
        Parser parser = new Parser();
        Stack<MultiExpression> expressionStack = new Stack<>();
        expressionStack.push(new MultiExpression(false));
        int pos = 0;
        for (char c : expressionStr.toCharArray()) {
            if (c == '(') {
                if (!parser.isReset())
                    throw new InvalidExpressionException(parser.toString());
                MultiExpression multiExpression = new MultiExpression(false);
                expressionStack.peek().addExpression(multiExpression);
                expressionStack.push(multiExpression);
                pos++;
                this.isBracket = false;
            } else if (c == ')') {
                if (pos == 0)
                    throw new InvalidExpressionException(")");
                pos--;
                IExpression expression = parser.getExpression();
                if (expression != null)
                    expressionStack.peek().addExpression(expression);
                MultiExpression multiExpression;
                if (!(multiExpression = expressionStack.pop()).checkSize())
                    throw new InvalidExpressionException(multiExpression.toString());
                this.isBracket = true;
            } else if (c != ' ') {
                parser.add(c, expressionStack.peek());
                this.isBracket = false;
            }
        }
        IExpression expression = parser.getExpression();
        if (expression != null)
            expressionStack.peek().addExpression(expression);
        this.expression = expressionStack.pop();
    }

    @NonNull
    public List<Argument> getArguments() {
        return arguments;
    }

    public Argument getArgument(String name) {
        for (Argument argument : this.arguments)
            if (argument.getName().equals(name))
                return argument;
        return null;
    }

    @NonNull
    public MultiExpression getExpression() {
        return expression;
    }

    private class Parser {

        private StringBuilder stringBuilder;
        private boolean reset;
        private boolean first;
        private boolean argument;
        private boolean number;

        public Parser() {
            this.reset();
        }

        private void reset() {
            this.reset = true;
            this.first = true;
            this.argument = false;
            this.number = false;
            this.stringBuilder = new StringBuilder();
        }

        public void add(char c, MultiExpression expression) throws InvalidExpressionException {
            if (!checkOperator(c, expression)) {
                this.stringBuilder.append(c);
                check(c);
                this.reset = false;
            }
        }

        private void check(char c) throws InvalidExpressionException {
            if (this.first) {
                this.first = false;
                if (Character.isAlphabetic(c))
                    this.argument = true;
                else if (Character.isDigit(c) || c == '.' || c == '-')
                    this.number = true;
                else throw new InvalidExpressionException(this.stringBuilder.toString());
            } else if (this.argument) {
                if (!Character.isAlphabetic(c) && !Character.isDigit(c))
                    throw new InvalidExpressionException(this.stringBuilder.toString());
            } else if (this.number) {
                if (!Character.isDigit(c) && c != '.')
                    throw new InvalidExpressionException(this.stringBuilder.toString());
            } else throw new InvalidExpressionException(this.stringBuilder.toString());
        }

        private boolean checkOperator(char c, MultiExpression expression) throws InvalidExpressionException {
            String temp = this.stringBuilder.toString() + c;
            for (Operator operator : Operator.values())
                if (temp.endsWith(operator.getName())) {
                    if (operator == Operator.MINUS) {
                        if (ExpressionParser.this.isBracket) {
                            expression.addOperator(operator);
                            return true;
                        }
                        IExpression exp = this.getExpression();
                        if (exp != null) {
                            expression.addExpression(exp);
                            expression.addOperator(operator);
                            return true;
                        } else return false;
                    } else {
                        IExpression exp = this.getExpression();
                        if (exp != null)
                            expression.addExpression(exp);
                        expression.addOperator(operator);
                        return true;
                    }
                }
            return false;
        }

        private Argument getOrDefault(String name) {
            for (Argument unknownArgument : ExpressionParser.this.arguments)
                if (unknownArgument.getName().equals(name))
                    return unknownArgument;
            Argument unknownArgument = Argument.getArgument(name);
            ExpressionParser.this.arguments.add(unknownArgument);
            return unknownArgument;
        }

        private IExpression getExpression() throws InvalidExpressionException {
            if (!this.argument && !this.number)
                return null;
            else if (this.argument) {
                Argument unknownArgument = getOrDefault(this.stringBuilder.toString());
                this.reset();
                return unknownArgument;
            } else {
                SimpleConstantDouble simpleConstantDouble;
                try {
                    simpleConstantDouble = new SimpleConstantDouble(Double.parseDouble(stringBuilder.toString()));
                }
                catch (NumberFormatException e) {
                    throw new InvalidExpressionException(stringBuilder.toString());
                }
                this.reset();
                return simpleConstantDouble;
            }
        }

        public boolean isReset() {
            return reset;
        }

        @Override
        @NonNull
        public String toString() {
            return stringBuilder.toString();
        }
    }
}
