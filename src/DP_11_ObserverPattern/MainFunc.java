package DP_11_ObserverPattern;

public class MainFunc {
    public static void main(String[] args){
        PeekPa peekPa = new PeekPa();

        Servant liLei = new Servant("LiLei");
        Servant hanMeimei = new Servant("HanMeimei");

        peekPa.recruitServant(liLei);
        peekPa.recruitServant(hanMeimei);

        peekPa.poo();
        peekPa.hungry();

        peekPa.printState();
        liLei.printState();
        hanMeimei.printState();
    }
}
