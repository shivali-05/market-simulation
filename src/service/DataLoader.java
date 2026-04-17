package service;

import model.StockData;
import java.io.*;
import java.util.*;

public class DataLoader {

    public static List<StockData> load(String file, String stock) {

        List<StockData> list = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String[] header = br.readLine().replace("\"", "").split(",");

            int stockIndex = -1, dateIndex = -1;

            for (int i = 0; i < header.length; i++) {
                if (header[i].equalsIgnoreCase(stock)) stockIndex = i;
                if (header[i].equalsIgnoreCase("Date")) dateIndex = i;
            }

            String line;
            while ((line = br.readLine()) != null) {

                String[] parts = line.replace("\"", "").split(",");

                list.add(new StockData(
                        parts[dateIndex],
                        Double.parseDouble(parts[stockIndex])
                ));
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}