package DP_05_AbstractFactoryPattern;

public class MainFunc {
    public static void main(String[] args){
        IPeekFactory peekMom = new PeekMom();
        IPeekFactory peekAunt = new PeekAunt();
        IPeekCat peekPa = peekMom.bornPeekPa();
        IPeekCat peekTata = peekAunt.bornPeekTata();
        peekPa.showInfo();
        peekTata.showInfo();
    }
}
