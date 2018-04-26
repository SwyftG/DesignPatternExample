package DP_06_StrategyPattern;

public class SleepMode implements ICalculateWater {

    @Override
    public int calculateWater(int wight) {
        return (int)wight * 100 / 30;
    }
}
