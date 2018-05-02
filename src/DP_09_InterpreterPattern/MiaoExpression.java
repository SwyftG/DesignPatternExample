package DP_09_InterpreterPattern;

public class MiaoExpression extends AbstractExpression {
    private final String INTERPRETIVE_RESULT = "M";
    @Override
    public String interpret() {
        return INTERPRETIVE_RESULT;
    }
}
