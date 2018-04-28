package DP_07_StatePattern;

public class MainFunc {
    public static void main(String[] args){
        PeekPa peekPa = new PeekPa();
        peekPa.inHappyMode();
        peekPa.eat(20);
        peekPa.voice();
        peekPa.move();
        peekPa.inDepressionMode();
        peekPa.voice();
        peekPa.move();
        peekPa.eat(20);
        peekPa.showInfo();
    }
}
