package DP_11_ObserverPattern;

public class Servant implements IServantObserver{
    private IPeekPaSubject peekPa;
    private String servantName;
    private String peekPaState;

    public Servant(String servantName) {
        this.servantName = servantName;
    }

    @Override
    public void feed(String state) {
        this.peekPaState = state;
        System.out.println(this.servantName + " gives some cat food to Lord.");
    }

    @Override
    public void cleanUp(String state) {
        this.peekPaState = state;
        System.out.println(this.servantName + " is ready.");
    }

    public void printState() {
        System.out.println(this.servantName + " has state is : " + this.peekPaState);
    }
}
