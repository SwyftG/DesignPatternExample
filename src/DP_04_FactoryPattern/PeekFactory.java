package DP_04_FactoryPattern;

public abstract class PeekFactory {
    // 传入参数创建对象
//    public abstract IPeekCat bornPeekCat(String name);

    // 传入ClassName创建对象
//    public abstract <T extends IPeekCat> T bornPeekCat(Class<T> clz);

    // 多工厂方法
    public abstract IPeekCat bornPeekCat();
}
