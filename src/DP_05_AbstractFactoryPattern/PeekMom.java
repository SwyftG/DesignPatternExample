package DP_05_AbstractFactoryPattern;

public class PeekMom extends IPeekFactory {
    public String name;

    public PeekMom() {
        this.name = this.getClass().getSimpleName();
    }

    @Override
    IPeekCat bornPeekPa() {
        IPeekCat peekPa = new PeekPa(this.name);
        peekPa.init();
        return peekPa;
    }

    @Override
    IPeekCat bornPeekTata() {
        return null;
    }
}
