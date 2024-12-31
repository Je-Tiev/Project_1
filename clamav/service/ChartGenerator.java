package clamav.service;

import clamav.model.ScanResult;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;


import java.util.Map;

public class ChartGenerator {
    public JFreeChart createChart(StatisticsService.ScanStatistics statistics) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(statistics.cleanCount(), "Files", "Clean");
        dataset.addValue(statistics.infectedCount(), "Files", "Infected");
        dataset.addValue(statistics.errorCount(), "Files", "Error");


        JFreeChart barChart = ChartFactory.createBarChart(
                "Scan Result Statistics",
                "Status",
                "File Count",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        return barChart;
    }
    public JFreeChart createPieChart(StatisticsService.ScanStatistics statistics){
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Clean", statistics.cleanCount());
        dataset.setValue("Infected", statistics.infectedCount());
        dataset.setValue("Error", statistics.errorCount());
        JFreeChart pieChart = ChartFactory.createPieChart(
                "Scan Result Pie Chart",
                dataset,
                true,
                true,
                false
        );
        return pieChart;
    }
}