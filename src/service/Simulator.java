package service;

import model.Trader;
import java.util.*;

public class Simulator {

    public static Map<String, Object> run(List<Double> returns,
                                          List<Double> vol,
                                          List<Double> prices,
                                          List<String> dates) {

        Trader trader = new Trader();

        int trades = 0;
        int cooldown = 3;
        int lastTrade = -cooldown;

        double cost = 0.001;

        StringBuilder output = new StringBuilder();
        List<Double> portfolioValues = new ArrayList<>();

        for (int i = 0; i < vol.size(); i++) {

            double r = returns.get(i);
            double v = vol.get(i);
            double price = prices.get(i + 1);
            String date = dates.get(i + 1);

            String pattern = PatternDetector.detect(r);
            String volLabel;

            if (v > 0.04)
                volLabel = "HIGH VOL";
            else if (v > 0.02)
                volLabel = "MOD VOL";
            else
                volLabel = "LOW VOL";

            // track portfolio value
            portfolioValues.add(trader.getTotalValue(price));

            if (i - lastTrade < cooldown) continue;

            if (v > 0.04) {

                if (pattern.equals("CRASH")) {

                    trader.buy(price * (1 + cost)); //buy low
                    lastTrade = i;
                    trades++;

                    output.append(date)
                            .append(" | ")
                            .append(volLabel)
                            .append(" + ")
                            .append(pattern)
                            .append(" → BUY at ")
                            .append(String.format("%.2f", price))
                            .append("\n");
                }

                else if (pattern.equals("SPIKE")) {

                    trader.sell(price * (1 - cost)); //sell high
                    lastTrade = i;
                    trades++;

                    output.append(date)
                            .append(" | ")
                            .append(volLabel)
                            .append(" + ")
                            .append(pattern)
                            .append(" → SELL at ")
                            .append(String.format("%.2f", price))
                            .append("\n");
                }
            }
        }

        double finalValue = trader.getTotalValue(prices.get(prices.size() - 1));
        double initial = 1000;
        double profitPercent = ((finalValue - initial) / initial) * 100;

        output.append("\n--- SIMULATION RESULT ---\n");
        output.append("Total Trades: ").append(trades).append("\n");
        output.append("Final Portfolio Value: ").append(finalValue).append("\n");
        output.append("Return %: ")
                .append(String.format("%.2f", profitPercent))
                .append("%\n");

        Map<String, Object> result = new HashMap<>();
        result.put("log", output.toString());
        result.put("portfolio", portfolioValues);

        return result;
    }
}