package DP_04_FactoryPattern;

public class MainFunc {
    public static void main(String[] args){
        // 传入参数来创建对象
//        PeekFactory peekMom = new PeekMom();
//        IPeekCat peekPa = peekMom.bornPeekCat(PeekMom.PEEKPA);
//        IPeekCat peekBa = peekMom.bornPeekCat(PeekMom.PEEKBA);
//        peekPa.showInfo();
//        peekBa.showInfo();

        // 传入ClassName来创建对象
//        PeekFactory peekMom = new PeekMom();
//        IPeekCat peekPa = peekMom.bornPeekCat(PeekPa.class);
//        IPeekCat peekBa = peekMom.bornPeekCat(PeekBa.class);
//        peekPa.showInfo();
//        peekBa.showInfo();

        // 多工厂创建对象
        PeekFactory peekBaMom = new PeekBaMom();
        PeekFactory peekPaMom = new PeekPaMom();
        IPeekCat peekBa = peekBaMom.bornPeekCat();
        IPeekCat peekPa = peekPaMom.bornPeekCat();
        peekPa.showInfo();
        peekBa.showInfo();

    }
}
