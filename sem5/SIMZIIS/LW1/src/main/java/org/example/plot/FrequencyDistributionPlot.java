package org.example.plot;

import org.example.checker.FrequencyDistributionChecker;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class FrequencyDistributionPlot extends JFrame {

    public FrequencyDistributionPlot(String title, Map<String, Integer> frequencyMap) throws HeadlessException {
        super(title);
        DefaultCategoryDataset dataset = createDataset(frequencyMap);
        JFreeChart barChart = ChartFactory.createBarChart(
                "Частотное распределение символов",
                "Символ",
                "Частота",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
    }

    private DefaultCategoryDataset createDataset(Map<String, Integer> frequencyMap) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        frequencyMap.forEach((key, value) -> {
            dataset.addValue(value, "Частота", key);
        });
        return dataset;
    }

    public static void display(Map<String, Integer> frequencyMap) {
        SwingUtilities.invokeLater(() -> {
            FrequencyDistributionPlot example = new FrequencyDistributionPlot("Частотное распределение", frequencyMap);
            example.setSize(800, 600);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }

    public static void main(String[] args) {
        FrequencyDistributionChecker frequencyDistributionChecker = new FrequencyDistributionChecker();
        display(frequencyDistributionChecker.checkFrequency(10, 10000));
    }
}
