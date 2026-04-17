package service;

import java.util.*;

public class SummaryGenerator {

    public static String generate(List<Double> returns) {

        int spikes = 0, crashes = 0, stable = 0;

        for (double r : returns) {
            if (r > 0.02) spikes++;
            else if (r < -0.02) crashes++;
            else stable++;
        }

        StringBuilder summary = new StringBuilder();

        summary.append("\n--- MARKET SUMMARY ---\n");
        summary.append("Total Days: ").append(returns.size()).append("\n");
        summary.append("Spikes: ").append(spikes).append("\n");
        summary.append("Crashes: ").append(crashes).append("\n");
        summary.append("Stable: ").append(stable).append("\n");
        summary.append("Max Spike: ").append(Collections.max(returns)).append("\n");
        summary.append("Max Crash: ").append(Collections.min(returns)).append("\n");

        return summary.toString();
    }

    public static String volatilitySummary(List<Double> vol) {

        double avg = vol.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        double max = Collections.max(vol);
        double min = Collections.min(vol);

        StringBuilder v = new StringBuilder();

        v.append("\n--- VOLATILITY SUMMARY ---\n");
        v.append("Average Volatility: ").append(String.format("%.4f", avg)).append("\n");
        v.append("Max Volatility: ").append(String.format("%.4f", max)).append("\n");
        v.append("Min Volatility: ").append(String.format("%.4f", min)).append("\n");

        if (avg > 0.04)
            v.append("Market Condition: HIGH VOLATILITY ⚠\n");
        else if (avg > 0.02)
            v.append("Market Condition: MODERATE VOLATILITY\n");
        else
            v.append("Market Condition: STABLE\n");

        return v.toString();
    }
}
