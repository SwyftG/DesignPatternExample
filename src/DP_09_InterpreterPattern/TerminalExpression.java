package DP_09_InterpreterPattern;

public class TerminalExpression extends AbstractExpression{
    private String interpretiveResult;

    public TerminalExpression(String expression) {
        System.out.println("terminal");
        this.interpretiveResult = expression;
    }

    @Override
    public String interpret() {
        return interpretiveResult;
    }
}
