package DP_08_ChainOfResponsibilityPattern;

public class CatFood extends Food{
    private final Double CATFOOD_SOLIDITY = 5.0;

    public CatFood() {
        this.name = this.getClass().getSimpleName();
        this.solidity = CATFOOD_SOLIDITY;
    }
}
