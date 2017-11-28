package com.medjournal.controller.tab;

import com.medjournal.model.Patient;
import com.medjournal.stub.DataSource;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatisticsController {
    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private CategoryAxis xAxis;

    private ObservableList<String> diseaseList;
    private List<Patient> patients;

    public StatisticsController() {
        DataSource dataSource = DataSource.getInstance();
        this.patients = dataSource.getPatients();
        this.diseaseList = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        Map<String, Integer> numOfDiseases = patients.stream()
                .collect(Collectors.groupingBy(
                        p -> p.getDoctorsNote().getDisease(),
                        Collectors.reducing(0, e -> 1, Integer::sum)));

        diseaseList.addAll(numOfDiseases.keySet());
        xAxis.setCategories(diseaseList);
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        numOfDiseases.forEach((key, value) -> series.getData().add(new XYChart.Data<>(key, value)));

        barChart.getData().add(series);
        setMaxBarWidth(40, 10);

        barChart.widthProperty().addListener((obs, b, b1) -> {
            Platform.runLater(() -> setMaxBarWidth(60, 10));
        });
    }

    private void setMaxBarWidth(double maxBarWidth, double minCategoryGap) {
        double barWidth = 0;
        do {
            double catSpace = xAxis.getCategorySpacing();
            double availableBarSpace = catSpace - (barChart.getCategoryGap() + barChart.getBarGap());
            barWidth = (availableBarSpace / barChart.getData().size()) - barChart.getBarGap();
            if (barWidth > maxBarWidth) {
                availableBarSpace = (maxBarWidth + barChart.getBarGap()) * barChart.getData().size();
                barChart.setCategoryGap(catSpace - availableBarSpace - barChart.getBarGap());
            }
        } while (barWidth > maxBarWidth);

        do {
            double catSpace = xAxis.getCategorySpacing();
            double availableBarSpace = catSpace - (minCategoryGap + barChart.getBarGap());
            barWidth = Math.min(maxBarWidth, (availableBarSpace / barChart.getData().size()) - barChart.getBarGap());
            availableBarSpace = (barWidth + barChart.getBarGap()) * barChart.getData().size();
            barChart.setCategoryGap(catSpace - availableBarSpace - barChart.getBarGap());
        } while (barWidth < maxBarWidth && barChart.getCategoryGap() > minCategoryGap);
    }
}
