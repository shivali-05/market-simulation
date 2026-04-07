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

        for (int i = 0; i < vol.size(); i++) {

            double r = returns.get(i);
            double v = vol.get(i);
            double price = prices.get(i + 1);
            String date = dates.get(i + 1);

            String pattern = PatternDetector.detect(r);

            // Only act on important events
            if (pattern.equals("SPIKE") && v > 0.02) {
                trader.buy(price);
                trades++;

                System.out.println(
                        date + " | SPIKE (" +
                                String.format("%.3f", r) +
                                ") → BUY at " +
                                String.format("%.2f", price)
                );

            } else if (pattern.equals("CRASH")) {
                trader.sell(price);
                trades++;

                System.out.println(
                        date + " | CRASH (" +
                                String.format("%.3f", r) +
                                ") → SELL at " +
                                String.format("%.2f", price)
                );
            }
        }

        double finalValue = trader.getTotalValue(
                prices.get(prices.size() - 1));

        System.out.println("\n--- SIMULATION RESULT ---");
        System.out.println("Total Trades: " + trades);
        System.out.println("Final Portfolio Value: " + finalValue);
    }
}