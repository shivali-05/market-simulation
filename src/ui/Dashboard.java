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

public class Dashboard extends Application {

    @Override
    public void start(Stage stage) {

        TextField stockInput = new TextField("AAPL");
        Button runBtn = new Button("Run Simulation");

        TextArea output = new TextArea();
        output.setPrefHeight(200);

        // Graph setup
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> chart =
                new LineChart<>(xAxis, yAxis);

        chart.setTitle("Stock Price Trend");

        runBtn.setOnAction(e -> {

            String stock = stockInput.getText();

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

            // Run simulation
            output.setText("");
            Simulator.run(returns, vol, prices, dates);

            // Plot graph
            chart.getData().clear();

            XYChart.Series<Number, Number> series =
                    new XYChart.Series<>();

            for (int i = 0; i < prices.size(); i++) {
                series.getData().add(
                        new XYChart.Data<>(i, prices.get(i))
                );
            }

            chart.getData().add(series);

            output.appendText("\nSimulation Complete\n");
        });

        VBox layout = new VBox(10,
                new Label("Stock Symbol"),
                stockInput,
                runBtn,
                chart,
                output
        );

        Scene scene = new Scene(layout, 800, 600);

        stage.setTitle("Market Simulation Dashboard");
        stage.setScene(scene);
        stage.show();
    }
}