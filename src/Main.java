import java.util.*;
import model.*;
import service.*;

public class Main {

    public static void main(String[] args) {

        List<StockData> data =
                DataLoader.load("data/stockdata.csv", "AAPL");

        double volatility = VolatilityCalculator.calculate(data);

        System.out.println("Volatility: " + volatility);

        List<Double> returns = new ArrayList<>();

        for (int i = 1; i < data.size(); i++) {
            double today = data.get(i).getPrice();
            double yesterday = data.get(i - 1).getPrice();

            double ret = (today - yesterday) / yesterday;
            returns.add(ret);

            // String pattern = PatternDetector.detect(ret);

            // String date = data.get(i).getDate();

            // System.out.println(date + " | " +
            //         String.format("%.4f", ret) +
            //         " | " + pattern);
        }

        SummaryGenerator.generate(returns);
    }
}