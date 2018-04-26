package DP_06_StrategyPattern;

import java.util.ArrayList;
import java.util.List;

public class PeekPa {
    private ICalculateWater strategy;
    private String date;
    private List<Integer> waters;

    public PeekPa(String date) {
        waters = new ArrayList<>();
        this.date = date;
    }

    public void addWater(int weight, ICalculateWater mode){
        this.strategy = mode;
        waters.add(this.strategy.calculateWater(weight));
    }

    public void printSumWater() {
        int sumWater = 0;
        for (Integer item : waters) {
            sumWater += item;
        }
        System.out.println("PeekPa needs " + sumWater + " ml water on " + this.date + ".");
    }
}
