package DP_06_StrategyPattern;

public class ExeciseMode implements ICalculateWater {
    @Override
    public int calculateWater(int wight) {
        return (int)wight * 300 / 30;
    }
}
