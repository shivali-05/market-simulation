package com.example.demo.model;

public class Trader {

    private double balance = 1000;
    private double shares = 0;

    public void buy(double price) {
        double investment = balance * 0.5; // 50% capital
        if (investment > 0) {
            shares += investment / price;
            balance -= investment;
        }
    }

    public void sell(double price) {
        if (shares > 0) {
            balance += shares * price;
            shares = 0;
        }
    }

    public double getTotalValue(double currentPrice) {
        return balance + shares * currentPrice;
    }
}