package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import model.*;
import service.*;

import java.util.*;

//import static jdk.javadoc.internal.doclets.formats.html.markup.HtmlStyle.summary;

public class Dashboard extends Application {

    @Override
    public void start(Stage stage) {

        // 🔽 Dropdown instead of TextField
        ComboBox<String> stockDropdown = new ComboBox<>();
        stockDropdown.getItems().addAll("AAPL", "GOOG", "MSFT", "IBM");
        stockDropdown.setValue("AAPL");

        Button runBtn = new Button("Run Simulation");

        TextArea output = new TextArea();
        output.setPrefHeight(200);

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        LineChart<Number, Number> chart =
                new LineChart<>(xAxis, yAxis);

        chart.setTitle("Market Analysis");

        runBtn.setOnAction(e -> {

            String stock = stockDropdown.getValue();

            List<StockData> data =
                    DataLoader.load("data/stockdata.csv", stock);

            if (data.isEmpty()) {
                output.setText("Invalid stock!");
                return;
            }

            List<Double> returns = new ArrayList<>();
            List<Double> prices = new ArrayList<>();
            List<String> dates = new ArrayList<>();

            for (StockData d : data) {
                prices.add(d.getPrice());
                dates.add(d.getDate());
            }

            for (int i = 1; i < data.size(); i++) {
                double today = data.get(i).getPrice();
                double yesterday = data.get(i - 1).getPrice();
                returns.add((today - yesterday) / yesterday);
            }

            List<Double> vol =
                    RollingVolatilityCalculator.rollingVolatility(returns, 5);

            //  NEW: Run simulation + get result
            Map<String, Object> result =
                    Simulator.run(returns, vol, prices, dates);

            String summary = SummaryGenerator.generate(returns);
            String volSummary = SummaryGenerator.volatilitySummary(vol);

            output.setText(
                    (String) result.get("log")
                            + summary
                            + volSummary
            );

            List<Double> portfolio =
                    (List<Double>) result.get("portfolio");

            chart.getData().clear();

            // 📈 Price
            XYChart.Series<Number, Number> priceSeries = new XYChart.Series<>();
            priceSeries.setName("Price");

            for (int i = 0; i < prices.size(); i++) {
                priceSeries.getData().add(
                        new XYChart.Data<>(i, prices.get(i))
                );
            }

            // 📉 Volatility
            XYChart.Series<Number, Number> volSeries = new XYChart.Series<>();
            volSeries.setName("Volatility");

            for (int i = 0; i < vol.size(); i++) {
                volSeries.getData().add(
                        new XYChart.Data<>(i, vol.get(i))
                );
            }

            //  Portfolio
            XYChart.Series<Number, Number> profitSeries = new XYChart.Series<>();
            profitSeries.setName("Portfolio Value");

            for (int i = 0; i < portfolio.size(); i++) {
                profitSeries.getData().add(
                        new XYChart.Data<>(i, portfolio.get(i))
                );
            }

            chart.getData().addAll(priceSeries, volSeries, profitSeries);

            summary = SummaryGenerator.generate(returns);

        });

        VBox layout = new VBox(10,
                new Label("Stock Symbol"),
                stockDropdown,
                runBtn,
                chart,
                output
        );

        Scene scene = new Scene(layout, 900, 650);

        stage.setTitle("Market Simulation Dashboard");
        stage.setScene(scene);
        stage.show();
    }
}