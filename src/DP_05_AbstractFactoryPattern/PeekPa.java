package DP_05_AbstractFactoryPattern;

public class PeekPa extends IPeekCat {

    public PeekPa(String mother) {
        this.mother = mother;
    }

    @Override
    void init() {
        this.name = this.getClass().getSimpleName();
        this.gender = 1;
    }

    @Override
    void showInfo() {
        System.out.println("This is: " + this.name + "\ngender: " + (this.gender == 0 ? "Female" : "Male") + "\nMother is: " + this.mother);
    }
}
