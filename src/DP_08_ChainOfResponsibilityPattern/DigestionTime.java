package DP_08_ChainOfResponsibilityPattern;

abstract class DigestionTime {
    protected DigestionTime nextHandler;

    protected abstract double getSoftHandleSolidity();
    protected abstract double getHardHandleSolidity();

    protected abstract void calculateTime(Food food);

    public final void handleFood(Food food){
        if (food.getSolidity() >= getSoftHandleSolidity() && food.getSolidity() < getHardHandleSolidity()) {
            this.calculateTime(food);
        } else {
            if (nextHandler != null) {
                nextHandler.handleFood(food);
            } else {
                System.out.println("PeekPa can't digest: " + food.name);

            }
        }
    }
}
