package DP_07_StatePattern;

public class DepressionMode implements IModeState {
    private final String VOICE_STATE = "HuluHulu";
    private final String MOVE_STATE = "Sleep";
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
        return (int)foodWeight * 100 / 30;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
