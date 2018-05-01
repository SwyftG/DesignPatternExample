package DP_08_ChainOfResponsibilityPattern;

public class PartIV extends DigestionTime{
    private final int PART_IV_DIGEST_TIME = 16;
    @Override
    protected double getSoftHandleSolidity() {
        return 9.0;
    }

    @Override
    protected double getHardHandleSolidity() {
        return 10.0;
    }

    @Override
    protected void calculateTime(Food food) {
        System.out.println("This is PartIV\n PeekPa needs: " + PART_IV_DIGEST_TIME + " hours to digestive " + food.name);
    }
}
