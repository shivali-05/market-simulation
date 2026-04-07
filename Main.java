import java.util.*;

public class Main {

    public static void main(String[] args) {

        List<StockData> data =
                DataLoader.load("data/stockdata.csv", "AAPL");

        double volatility = VolatilityCalculator.calculate(data);

        List<Double> returns = new ArrayList<>();
        for (int i = 1; i < data.size(); i++) {
            double today = data.get(i).price;
            double yesterday = data.get(i - 1).price;

            double ret = (today - yesterday) / yesterday;
            returns.add(ret);
        }
        int count_spike=0;
        int count_crash=0;
        int count_stable=0;

        // return size would be the same the no. of days
        for (int i = 0; i < returns.size(); i++) {

            double r = returns.get(i);
            String date = data.get(i + 1).date;

            if (r > 0.02) {
                count_spike++;
                System.out.println(date + " | " + "SPIKE");
                System.out.println("Aggressive buys");
            } else if (r < -0.02) {
                count_crash++;
                System.out.println(date + " | " + "CRASH");
                System.out.println("Panick Selling");
            } else {
                count_stable++;
            }
        }

        System.out.println(".......................");
        System.out.println("Market Summary: ");
        System.out.println("Total Days: " + returns.size());
        System.out.println("Spikes: " + count_spike);
        System.out.println("Crashes: " + count_crash);
        System.out.println("Stable: " + count_stable);
        System.out.println("Volatility: " + volatility);

        // voitility desc
        if (volatility > 0.02) {
            System.out.println("High Volatility");
        } else if (volatility > 0.01) {
            System.out.println("Moderate Volatility");
        } else {
            System.out.println("Low Volatility");
        }

//        //trader logic (simulation can do)
//
//        if (volatility > 0.02) {
//            System.out.println("Aggressive trader buys");
//        } else {
//            System.out.println("Conservative trader holds");
//        }
    }
}