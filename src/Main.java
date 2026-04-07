import model.*;
import service.*;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        List<StockData> data =
                DataLoader.load("data/stockdata.csv", "AAPL");

        List<Double> returns = new ArrayList<>();
        List<Double> prices = new ArrayList<>();

        for (StockData d : data) {
            prices.add(d.getPrice());
        }

        for (int i = 1; i < data.size(); i++) {
            double today = data.get(i).getPrice();
            double yesterday = data.get(i - 1).getPrice();

            returns.add((today - yesterday) / yesterday);
        }

        int window = 5;

        List<Double> rollingVol =
                RollingVolatilityCalculator.rollingVolatility(returns, window);

        System.out.println("Rolling Volatility Calculated\n");

        List<String> dates = new ArrayList<>();

        for (StockData d : data) {
            prices.add(d.getPrice());
            dates.add(d.getDate());
        }

        Simulator.run(returns, rollingVol, prices, dates);

        SummaryGenerator.generate(returns);
    }
}