package DP_05_AbstractFactoryPattern;

public class PeekAunt extends IPeekFactory {
    public String name;

    public PeekAunt() {
        this.name = this.getClass().getSimpleName();
    }

    @Override
    IPeekCat bornPeekPa() {
        return null;
    }

    @Override
    IPeekCat bornPeekTata() {
        IPeekCat peekTata = new PeekTata(this.name);
        peekTata.init();
        return peekTata;
    }
}
