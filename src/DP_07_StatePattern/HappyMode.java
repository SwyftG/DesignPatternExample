package DP_07_StatePattern;

public class HappyMode implements IModeState {
    private final String VOICE_STATE = "AoAoAo";
    private final String MOVE_STATE = "Mad";

    @Override
    public String voiceState() {
        return VOICE_STATE;
    }

    @Override
    public int eatState(int weight) {
        return weight;
    }

    @Override
    public String moveState() {
        return MOVE_STATE;
    }

    @Override
    public int calculateWater(int foodWeight) {
        return (int)foodWeight * 300 / 30;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
