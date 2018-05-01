package DP_08_ChainOfResponsibilityPattern;

public class Water extends Food{
    private final Double WATER_SOLIDITY = 0.0;

    public Water() {
        this.name = this.getClass().getSimpleName();
        this.solidity = WATER_SOLIDITY;
    }
}
