package DP_05_AbstractFactoryPattern;

abstract class IPeekCat {
    public String name;
    public int gender;
    public String mother;
    abstract void init();
    abstract void showInfo();
}
