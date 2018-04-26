package DP_04_FactoryPattern;

public class PeekBaMom extends PeekFactory{
    @Override
    public IPeekCat bornPeekCat() {
        IPeekCat peekBa = new PeekBa();
        peekBa.init();
        return peekBa;
    }
}
