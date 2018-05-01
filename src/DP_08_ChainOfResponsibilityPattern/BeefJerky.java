package DP_08_ChainOfResponsibilityPattern;

public class BeefJerky extends Food{
    private final Double BEEFJERKY_SOLIDITY = 9.9;

    public BeefJerky() {
        this.name = this.getClass().getSimpleName();
        this.solidity = BEEFJERKY_SOLIDITY;
    }
}
