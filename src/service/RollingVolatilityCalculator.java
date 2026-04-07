package service;

import java.util.*;

public class RollingVolatilityCalculator {

    public static List<Double> rollingVolatility(List<Double> returns, int window) {

        List<Double> volList = new ArrayList<>();

        for (int i = window; i < returns.size(); i++) {

            List<Double> sub = returns.subList(i - window, i);

            double mean = 0;
            for (double r : sub) mean += r;
            mean /= sub.size();

            double var = 0;
            for (double r : sub) {
                var += Math.pow(r - mean, 2);
            }
            var /= sub.size();

            volList.add(Math.sqrt(var));
        }

        return volList;
    }
}