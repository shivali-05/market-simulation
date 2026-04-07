package model;

public class Trader {

    private double balance = 1000;
    private double shares = 0;

    public void buy(double price) {
        if (balance > 0) {
            shares = balance / price;
            balance = 0;
        }
    }

    public void sell(double price) {
        if (shares > 0) {
            balance = shares * price;
            shares = 0;
        }
    }

    public double getTotalValue(double currentPrice) {
        return balance + shares * currentPrice;
    }
}