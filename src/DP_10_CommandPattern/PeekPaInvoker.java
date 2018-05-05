package DP_10_CommandPattern;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.ArrayList;
import java.util.List;

public class PeekPaInvoker {
    private List<Integer> forwardList = new ArrayList<>();
    private ICommand moveCommand;
    private int distance = 0;

    public void setMoveCommand(ICommand command) {
        this.moveCommand = command;
    }

    public void go(int type){
        distance += moveCommand.go(type);
        forwardList.add(type);
        System.out.println("Distance: " + distance);
    }

    public void back() {
        if (forwardList.size() > 0) {
            int type = forwardList.get(forwardList.size() - 1);
            forwardList.remove(forwardList.size() - 1);
            distance -= moveCommand.back(type);
        }
        System.out.println("Distance: " + distance);
    }
}
