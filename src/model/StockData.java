package model;

public class StockData {

    private String date;
    private double price;

    public StockData(String date, double price) {
        this.date = date;
        this.price = price;
    }

    public String getDate() { return date; }
    public double getPrice() { return price; }
}