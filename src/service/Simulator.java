package service;

import model.Trader;
import java.util.*;

public class Simulator {

    public static void run(List<Double> returns,
                           List<Double> vol,
                           List<Double> prices,
                           List<String> dates) {

        Trader trader = new Trader();

        int trades = 0;
        int cooldown = 3;
        int lastTrade = -cooldown;

        double cost = 0.001; // 0.1% transaction cost

        for (int i = 0; i < vol.size(); i++) {

            double r = returns.get(i);
            double v = vol.get(i);
            double price = prices.get(i + 1);
            String date = dates.get(i + 1);

            String pattern = PatternDetector.detect(r);

            if (i - lastTrade < cooldown) continue;

            if (v > 0.025) { // stricter volatility threshold

                if (pattern.equals("SPIKE")) {

                    trader.buy(price * (1 + cost));
                    lastTrade = i;
                    trades++;

                    System.out.println(date +
                            " | HIGH VOL + SPIKE → BUY at " +
                            String.format("%.2f", price));
                }

                else if (pattern.equals("CRASH")) {

                    trader.sell(price * (1 - cost));
                    lastTrade = i;
                    trades++;

                    System.out.println(date +
                            " | HIGH VOL + CRASH → SELL at " +
                            String.format("%.2f", price));
                }
            }
        }

        double finalValue = trader.getTotalValue(prices.get(prices.size() - 1));

        double initial = 1000;
        double profitPercent = ((finalValue - initial) / initial) * 100;

        System.out.println("\n--- SIMULATION RESULT ---");
        System.out.println("Total Trades: " + trades);
        System.out.println("Final Portfolio Value: " + finalValue);
        System.out.println("Return %: " + String.format("%.2f", profitPercent) + "%");
    }
}