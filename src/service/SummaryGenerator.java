package service;

import java.util.*;

public class SummaryGenerator {

    public static void generate(List<Double> returns) {

        int spikes = 0, crashes = 0, stable = 0;

        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;

        for (double r : returns) {

            if (r > 0.02) spikes++;
            else if (r < -0.02) crashes++;
            else stable++;

            if (r > max) max = r;
            if (r < min) min = r;
        }

        System.out.println("\n--- SUMMARY ---");
        System.out.println("Total Days: " + returns.size());
        System.out.println("Spikes: " + spikes);
        System.out.println("Crashes: " + crashes);
        System.out.println("Stable: " + stable);
        System.out.println("Max Spike: " + max);
        System.out.println("Max Crash: " + min);
    }
}