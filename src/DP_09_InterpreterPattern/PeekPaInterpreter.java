package DP_09_InterpreterPattern;

import java.util.Stack;

public class PeekPaInterpreter {
    private final String MIAO = "Miao";
    private final String AO = "Ao";
    private final String ADD_OPERATION = "+";

    private Stack<AbstractExpression> mStack = new Stack<>();

    public void setExpression(String expression) {
        if (mStack != null) {
            mStack.clear();
        }
        AbstractExpression exp1;
        AbstractExpression exp2;

        String[] elements = expression.split(" ");

        for (int i = 0; i < elements.length; i++) {
            switch (elements[i]) {
                case MIAO:
                    mStack.push(new MiaoExpression());
                    break;
                case AO:
                    mStack.push(new AoExpression());
                    break;
                case ADD_OPERATION:
                    exp1 = mStack.pop();
                    // 边界判断
                    String nextElement = elements[++i];
                    if (nextElement.equals(MIAO)) {
                        exp2 = new MiaoExpression();
                    } else {
                        exp2 = new AoExpression();
                    }
                    mStack.push(new AddExpression(exp1, exp2));
                    break;
                default:
                    mStack.push(new TerminalExpression(elements[i]));
            }
        }
    }

    public String parse(){
        String tempResult = mStack.pop().interpret();
        int miaoCount = 0;
        int aoCount = 0;
        for (int i =0; i < tempResult.length(); i++) {
            if (tempResult.charAt(i) == 'M') {
                miaoCount++;
            } else {
                aoCount++;
            }
        }
        return String.valueOf(aoCount) + "A" + String.valueOf(miaoCount) + "M";
    }

}
