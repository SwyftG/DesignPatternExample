package DP_10_CommandPattern;

public class JumpMoveReceiver implements IMoveReceiver {
    private final int FORWARD_DISTANCE = 2;
    private final int BACKWARD_DISTANCE = 2;

    @Override
    public int forward() {
        System.out.println("Jump forward 2m.");
        return FORWARD_DISTANCE;
    }

    @Override
    public int backward() {
        System.out.println("Jump backward 2m.");
        return BACKWARD_DISTANCE;
    }
}
