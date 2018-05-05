package DP_10_CommandPattern;

public class WalkMoveReceiver implements IMoveReceiver {
    private final int FORWARD_DISTANCE = 1;
    private final int BACKWARD_DISTANCE = 1;

    @Override
    public int forward() {
        System.out.println("Walk forward 1m.");
        return FORWARD_DISTANCE;
    }

    @Override
    public int backward() {
        System.out.println("Walk backward 1m.");
        return BACKWARD_DISTANCE;
    }
}
