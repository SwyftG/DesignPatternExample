package DP_08_ChainOfResponsibilityPattern;

public class CatCan extends Food{
    private final Double MEETCAN_SOLIDITY = 2.5;

    public CatCan() {
        this.name = this.getClass().getSimpleName();
        this.solidity = MEETCAN_SOLIDITY;
    }
}
