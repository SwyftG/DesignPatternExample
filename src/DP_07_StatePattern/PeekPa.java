package DP_07_StatePattern;

import java.util.ArrayList;
import java.util.List;

public class PeekPa {
    private IModeState modeState;
    private IModeState happyMode;
    private IModeState depressionMode;
    private String voiceState;
    private String moveState;
    private List<Integer> drintWaterList;

    public PeekPa() {
        drintWaterList = new ArrayList<>();
        this.happyMode = new HappyMode();
        this.depressionMode = new DepressionMode();
        setMode(happyMode);
    }

    public void inHappyMode() {
        setMode(this.happyMode);
    }

    public void inDepressionMode() {
        setMode(this.depressionMode);
    }

    private void setMode(IModeState mode) {
        this.modeState = mode;
    }

    public void voice() {
        this.voiceState = this.modeState.voiceState();
        System.out.println("PeekPa's is In : " + this.modeState + ". Voice is : " + this.voiceState);
    }

    public void eat(int weight) {
        int foodWeight = this.modeState.eatState(weight);
        int drinkWater = calculateWater(this.modeState, foodWeight);
        drintWaterList.add(drinkWater);
        System.out.println("PeekPa eat: " + foodWeight + ". Drink: " + drinkWater + "ml water.");
    }

    private int calculateWater(IModeState modeState, int foodWeight) {
        return modeState.calculateWater(foodWeight);
    }

    public void move() {
        this.moveState = this.modeState.moveState();
        System.out.println("PeekPa's is In : " + this.modeState + ". MoveState is : " + this.moveState);
    }

    public void showInfo() {
        int waterSum = 0;
        for (Integer water : drintWaterList) {
            waterSum += water;
        }
        System.out.println("Water sum is : " + waterSum + "ml.");
    }
}
