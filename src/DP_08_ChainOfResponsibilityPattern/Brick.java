package DP_08_ChainOfResponsibilityPattern;

public class Brick extends Food{
    private final Double BRICK_SOLIDITY = 100.0;

    public Brick() {
        this.name = this.getClass().getSimpleName();
        this.solidity = BRICK_SOLIDITY;
    }
}
