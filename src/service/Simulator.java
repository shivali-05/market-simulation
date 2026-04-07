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

            //  CLASSIFY VOLATILITY
            String volType;
            if (v > 0.02) volType = "HIGH";
            else if (v > 0.01) volType = "MODERATE";
            else volType = "LOW";

            // DECISION BASED ON BOTH, (risk + return = kachaow kaboom woah)
            if (volType.equals("HIGH")) {

                if (pattern.equals("SPIKE")) {
                    trader.buy(price);
                    trades++;

                    System.out.println(
                            date + " | HIGH VOL + SPIKE (" +
                                    String.format("%.3f", r) +
                                    ") → Aggressive BUY at " +
                                    String.format("%.2f", price)
                    );
                }

                else if (pattern.equals("CRASH")) {
                    trader.sell(price);
                    trades++;

                    System.out.println(
                            date + " | HIGH VOL + CRASH (" +
                                    String.format("%.3f", r) +
                                    ") → Panic SELL at " +
                                    String.format("%.2f", price)
                    );
                }
            }

            // moderate behavior... (im commenting cause for now, ill show the major events)
            else if (volType.equals("MODERATE")) {

                if (pattern.equals("SPIKE")) {
                    trader.buy(price);
                    trades++;

                    System.out.println(
                            date + " | MOD VOL + SPIKE → BUY at " +
                                    String.format("%.2f", price)
                    );
                }
            }

            // LOW volatility → no action, do nothing
        }

        double finalValue =
                trader.getTotalValue(prices.get(prices.size() - 1));

        double initial = 1000;
        double profitPercent =
                ((finalValue - initial) / initial) * 100;

        System.out.println("\n--- SIMULATION RESULT ---");
        System.out.println("Total Trades: " + trades);
        System.out.println("Final Portfolio Value: " + finalValue);
        System.out.println("Return %: " +
                String.format("%.2f", profitPercent) + "%");
    }
}