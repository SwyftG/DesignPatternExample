package DP_10_CommandPattern;

public interface ICommand {
    int go(int type);
    int back(int type);
}
