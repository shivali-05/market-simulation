package service;

import java.util.*;

public class SummaryGenerator {

    public static void generate(List<Double> returns) {

        int spikes = 0, crashes = 0, stable = 0;

        for (double r : returns) {
            if (r > 0.02) spikes++;
            else if (r < -0.02) crashes++;
            else stable++;
        }

        System.out.println("\n--- SUMMARY ---");
        System.out.println("Total Days: " + returns.size());
        System.out.println("Spikes: " + spikes);
        System.out.println("Crashes: " + crashes);
        System.out.println("Stable: " + stable);
        System.out.println("Max Spike: " + Collections.max(returns));
        System.out.println("Max Crash: " + Collections.min(returns));
    }
}