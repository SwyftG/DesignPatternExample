package DP_04_FactoryPattern;

public class PeekPaMom extends PeekFactory {
    @Override
    public IPeekCat bornPeekCat() {
        IPeekCat peekPa = new PeekPa();
        peekPa.init();
        return peekPa;
    }
}
