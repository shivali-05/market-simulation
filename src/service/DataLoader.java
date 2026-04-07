package service;
import model.StockData;
import java.io.*;
import java.util.*;

public class DataLoader {

    public static List<StockData> load(String file, String stock) {

        List<StockData> list = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String headerLine = br.readLine();
            String[] header = headerLine.replace("\"", "").split(",");

            int stockIndex = -1;
            int dateIndex = -1;

            for (int i = 0; i < header.length; i++) {

                if (header[i].trim().equalsIgnoreCase(stock))
                    stockIndex = i;

                if (header[i].trim().equalsIgnoreCase("Date"))
                    dateIndex = i;
            }

            String line;

            while ((line = br.readLine()) != null) {

                String[] parts = line.replace("\"", "").split(",");

                list.add(new StockData(
                        parts[dateIndex],
                        Double.parseDouble(parts[stockIndex])
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}