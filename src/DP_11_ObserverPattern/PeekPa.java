package DP_11_ObserverPattern;

public class PeekPa extends IPeekPaSubject{
    private String state;

    public PeekPa() {
        this.state = "";
    }

    public void changeState(int type) {
        if(type == 0) {
            state += "pop;";
        } else if (type == 1) {
            state += "hungry:";
        }
    }

    public void printState() {
        System.out.println(this.state);
    }

    public String getState() {
        return this.state;
    }

    public void poo() {
        changeState(0);
        System.out.println("PeekPa poo~~~~~~");
        notifyPoo(this.state);
    }

    public void hungry() {
        changeState(1);
        System.out.println("PeekPa is hungry~~~~~");
        notifyFeed(this.state);
    }
}
