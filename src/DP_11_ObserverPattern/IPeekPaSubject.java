package DP_11_ObserverPattern;

import java.util.ArrayList;
import java.util.List;

public class IPeekPaSubject {
    private List<IServantObserver> servants = new ArrayList<>();

    public void recruitServant(IServantObserver servant) {
        servants.add(servant);
    }

    public void fireServant(IServantObserver servant) {
        servants.remove(servant);
    }

    protected void notifyPoo(String state) {
        for (IServantObserver servent : servants) {
            servent.cleanUp(state);
        }
    }

    protected void notifyFeed(String state) {
        for (IServantObserver servent : servants) {
            servent.feed(state);
        }
    }
}
