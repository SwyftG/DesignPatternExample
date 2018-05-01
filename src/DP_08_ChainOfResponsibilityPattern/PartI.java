package DP_08_ChainOfResponsibilityPattern;

public class PartI extends DigestionTime{
    private final int PART_I_DIGEST_TIME = 4;
    @Override
    protected double getSoftHandleSolidity() {
        return 0.0;
    }

    @Override
    protected double getHardHandleSolidity() {
        return 2.0;
    }

    @Override
    protected void calculateTime(Food food) {
        System.out.println("This is PartI\n PeekPa needs: " + PART_I_DIGEST_TIME + " hours to digestive " + food.name);
    }
}
