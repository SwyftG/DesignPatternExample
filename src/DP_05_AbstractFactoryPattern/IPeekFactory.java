package DP_05_AbstractFactoryPattern;

public abstract class IPeekFactory {
    // 生产PeekPa的抽象方法
    abstract IPeekCat bornPeekPa();
    // 生产PeekTata的抽象方法
    abstract IPeekCat bornPeekTata();
}
