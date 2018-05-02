package DP_09_InterpreterPattern;

public class AddExpression extends OperationExpression {

    public AddExpression(AbstractExpression exp1, AbstractExpression exp2) {
        super(exp1, exp2);
    }

    @Override
    public String interpret() {
        return exp1.interpret() + exp2.interpret();
    }
}
