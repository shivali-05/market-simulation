package com.example.demo.service;
import com.example.demo.model.StockData;
import java.util.ArrayList;
import java.util.List;

public class VolatilityCalculator {

    public static double calculate(List<StockData> data) {
        List<Double> returns = new ArrayList<>();

        // Step 1: Calculate returns
        for (int i = 1; i < data.size(); i++) {
            double today = data.get(i).getPrice();
            double yesterday = data.get(i - 1).getPrice();

            double ret = (today - yesterday) / yesterday;
            returns.add(ret);
        }

        // Step 2: Mean
        double sum = 0;
        for (double r : returns) {
            sum += r;
        }
        double mean = sum / returns.size();

        // Step 3: Variance
        double varianceSum = 0;
        for (double r : returns) {
            varianceSum += Math.pow(r - mean, 2);
        }
        double variance = varianceSum / returns.size();

        // Step 4: Volatility
        double volatility = Math.sqrt(variance);
        return volatility;
    }
}