package DP_08_ChainOfResponsibilityPattern;

public class MainFunc {
    public static void main(String[] args) {
        Food water = new Water();
        Food catCan = new CatCan();
        Food catFood = new CatFood();
        Food beefJerky = new BeefJerky();
        Food brick = new Brick();
        PeekPaDigestiveSystem system = new PeekPaDigestiveSystem();

        system.calculateDigestiveTime(water);
        system.calculateDigestiveTime(catCan);
        system.calculateDigestiveTime(catFood);
        system.calculateDigestiveTime(beefJerky);
        system.calculateDigestiveTime(brick);
    }
}
