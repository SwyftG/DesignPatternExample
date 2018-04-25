package DP_05_AbstractFactoryPattern;

public class PeekTata extends IPeekCat {

    public PeekTata(String mother) {
        this.mother = mother;
    }

    @Override
    void init() {
        this.name = this.getClass().getSimpleName();
        this.gender = 0;
    }

    @Override
    void showInfo() {
        System.out.println("This is: " + this.name + "\ngender: " +  (this.gender == 0 ? "Female" : "Male") + "\nMother is: " + this.mother);
    }
}
