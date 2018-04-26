package DP_04_FactoryPattern;

public class PeekBa extends IPeekCat {
    private String FISH = "fish";

    public PeekBa() {
        this.name = PeekBa.class.getSimpleName();
    }

    @Override
    public void init() {
        this.favouriteFood = FISH;
    }

    @Override
    public void showInfo() {
        System.out.println("This is: " + this.name + " Favourite: " + this.favouriteFood);
    }
}
