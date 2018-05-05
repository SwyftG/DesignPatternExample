package DP_10_CommandPattern;

public class MainFunc {
    public static void main(String[] args) {
        PeekPaInvoker peekPaInvoker = new PeekPaInvoker();

        MoveCommand peekPaCommand = new MoveCommand();
        peekPaInvoker.setMoveCommand(peekPaCommand);

        IMoveReceiver jumpReceiver = new JumpMoveReceiver();
        IMoveReceiver walkReceiver = new WalkMoveReceiver();

        peekPaCommand.setJumpMoveReceiver(jumpReceiver);
        peekPaCommand.setWalkMoveReceiver(walkReceiver);

        peekPaInvoker.go(0);
        peekPaInvoker.go(1);
        peekPaInvoker.go(0);
        peekPaInvoker.go(1);
        peekPaInvoker.go(0);

        peekPaInvoker.back();
        peekPaInvoker.back();
        peekPaInvoker.back();
        peekPaInvoker.back();
        peekPaInvoker.back();
    }
}
