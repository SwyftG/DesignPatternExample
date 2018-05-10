package DP_11_ObserverPattern;

public interface IServantObserver {
    void feed(String state);
    void cleanUp(String state);
}
