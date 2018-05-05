package DP_10_CommandPattern;

public class MoveCommand implements ICommand {
    private IMoveReceiver jumpMoveReceiver;
    private IMoveReceiver walkMoveReceiver;

    public void setJumpMoveReceiver(IMoveReceiver jumpMoveReceiver) {
        this.jumpMoveReceiver = jumpMoveReceiver;
    }

    public void setWalkMoveReceiver(IMoveReceiver walkMoveReceiver) {
        this.walkMoveReceiver = walkMoveReceiver;
    }

    @Override
    public int go(int type) {
        if (type == 0) {
            return jumpMoveReceiver.forward();
        } else {
            return walkMoveReceiver.forward();
        }
    }

    @Override
    public int back(int type) {
        if (type == 0) {
            return jumpMoveReceiver.backward();
        } else {
            return walkMoveReceiver.backward();
        }
    }
}
