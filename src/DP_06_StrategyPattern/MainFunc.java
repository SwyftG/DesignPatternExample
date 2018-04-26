package DP_06_StrategyPattern;

public class MainFunc {
    public static void main(String[] args){
        ICalculateWater sleepMode = new SleepMode();
        ICalculateWater execiseMode = new ExeciseMode();

        PeekPa mondayPeekPa = new PeekPa("Monday");
        mondayPeekPa.addWater(50, sleepMode);
        mondayPeekPa.addWater(100, execiseMode);
        mondayPeekPa.addWater(150, execiseMode);
        mondayPeekPa.addWater(300, sleepMode);

        PeekPa sundayPeekPa = new PeekPa("Sunday");
        sundayPeekPa.addWater(30, sleepMode);
        sundayPeekPa.addWater(20, sleepMode);
        sundayPeekPa.addWater(10, sleepMode);
        sundayPeekPa.addWater(50, sleepMode);

        mondayPeekPa.printSumWater();
        sundayPeekPa.printSumWater();
    }
}
