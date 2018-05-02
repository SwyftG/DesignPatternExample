package DP_09_InterpreterPattern;

public class AoExpression extends AbstractExpression{
    private final String INTERPRETIVE_RESULT = "A";

    @Override
    public String interpret() {
        return INTERPRETIVE_RESULT;
    }
}
