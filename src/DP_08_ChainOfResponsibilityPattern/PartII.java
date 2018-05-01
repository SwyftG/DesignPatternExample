package DP_08_ChainOfResponsibilityPattern;

public class PartII extends DigestionTime{
    private final int PART_II_DIGEST_TIME = 8;
    @Override
    protected double getSoftHandleSolidity() {
        return 2.0;
    }

    @Override
    protected double getHardHandleSolidity() {
        return 5.0;
    }

    @Override
    protected void calculateTime(Food food) {
        System.out.println("This is PartII\n PeekPa needs: " + PART_II_DIGEST_TIME + " hours to digestive " + food.name);
    }
}
