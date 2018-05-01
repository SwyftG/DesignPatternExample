package DP_08_ChainOfResponsibilityPattern;

public class PartIII extends DigestionTime{
    private final int PART_III_DIGEST_TIME = 12;
    @Override
    protected double getSoftHandleSolidity() {
        return 5.0;
    }

    @Override
    protected double getHardHandleSolidity() {
        return 9.0;
    }

    @Override
    protected void calculateTime(Food food) {
        System.out.println("This is PartIII\n PeekPa needs: " + PART_III_DIGEST_TIME + " hours to digestive " + food.name);
    }
}
