package DP_08_ChainOfResponsibilityPattern;

public class PeekPaDigestiveSystem {
    private DigestionTime partI;
    private DigestionTime partII;
    private DigestionTime partIII;
    private DigestionTime partIV;

    public PeekPaDigestiveSystem() {
        partI = new PartI();
        partII = new PartII();
        partIII = new PartIII();
        partIV = new PartIV();

        partI.nextHandler = partII;
        partII.nextHandler = partIII;
        partIII.nextHandler = partIV;
    }

    public void calculateDigestiveTime(Food food){
        partI.handleFood(food);
    }
}
