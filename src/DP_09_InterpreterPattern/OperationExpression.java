package DP_09_InterpreterPattern;

public class OperationExpression extends AbstractExpression{
    protected AbstractExpression exp1;
    protected AbstractExpression exp2;
    public OperationExpression(AbstractExpression exp1, AbstractExpression exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public String interpret() {
        return null;
    }
}
