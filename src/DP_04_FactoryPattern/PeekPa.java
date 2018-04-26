package DP_04_FactoryPattern;

public class PeekPa extends IPeekCat {
    private String BEEF = "beef";

    public PeekPa() {
        this.name = PeekPa.class.getSimpleName();
    }

    @Override
    public void init() {
        this.favouriteFood = BEEF;
    }

    @Override
    public void showInfo() {
        System.out.println("This is: " + this.name + " Favourite: " + this.favouriteFood);
    }
}
