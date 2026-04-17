package com.example.demo.service;

import org.springframework.stereotype.Service;
import java.util.*;
import com.example.demo.model.StockData;

@Service
public class SimulationService {

    public Map<String, Object> run(String stock) {

        List<StockData> data = DataLoader.load("../data/stockdata.csv", stock);

        List<Double> prices = new ArrayList<>();
        List<String> dates = new ArrayList<>();

        for (StockData d : data) {
            prices.add(d.getPrice());
            dates.add(d.getDate());
        }

        // returns
        List<Double> returns = new ArrayList<>();
        for (int i = 1; i < prices.size(); i++) {
            double r = (prices.get(i) - prices.get(i - 1)) / prices.get(i - 1);
            returns.add(r);
        }

        // volatility (rolling)
        List<Double> vol = RollingVolatilityCalculator.rollingVolatility(returns, 5);

        int minSize = Math.min(Math.min(returns.size(), vol.size()), prices.size() - 1);

        returns = returns.subList(0, minSize);
        vol = vol.subList(0, minSize);

        // call YOUR simulator
        Map<String, Object> result = Simulator.run(returns, vol, prices, dates);

        return result;
    }
}