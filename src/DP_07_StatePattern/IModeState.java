package DP_07_StatePattern;

public interface IModeState {
    String voiceState();
    int eatState(int weight);
    String moveState();
    int calculateWater(int foodWeight);
}
