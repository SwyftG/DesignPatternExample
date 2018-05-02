package DP_09_InterpreterPattern;

public class MainFunc {
    public static void main(String[] args) {
        String voice1 = "Miao + Miao + Miao + Miao";
        String voice2 = "Ao + Ao + Ao";
        String voice3 = "Miao + Ao + Miao + Miao";
        String voice4 = "Miao + Ao + Ao + Miao";
        PeekPaInterpreter interpreter = new PeekPaInterpreter();
        interpreter.setExpression(voice1);
        System.out.println(interpreter.parse());
        interpreter.setExpression(voice2);
        System.out.println(interpreter.parse());
        interpreter.setExpression(voice3);
        System.out.println(interpreter.parse());
        interpreter.setExpression(voice4);
        System.out.println(interpreter.parse());
    }
}
