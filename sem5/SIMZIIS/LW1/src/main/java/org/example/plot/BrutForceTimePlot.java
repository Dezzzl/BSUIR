package org.example.plot;

import org.example.checker.BrutForceTimeChecker;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.Map;

public class BrutForceTimePlot extends JFrame {

    public BrutForceTimePlot(String title, Map<Integer, Long> timeMap) {
        super(title);
        XYSeriesCollection dataset = createDataset(timeMap);
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Время перебора паролей",
                "Длина пароля",
                "Время (мс)",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
    }

    private XYSeriesCollection createDataset(Map<Integer, Long> timeMap) {
        XYSeries series = new XYSeries("Время перебора");
        timeMap.forEach(series::add);
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        return dataset;
    }

    public static void display(Map<Integer, Long> timeMap) {
        SwingUtilities.invokeLater(() -> {
            BrutForceTimePlot example = new BrutForceTimePlot("График времени перебора", timeMap);
            example.setSize(800, 600);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }

    public static void main(String[] args) {
        BrutForceTimeChecker brutForceTimeChecker = new BrutForceTimeChecker();
        BrutForceTimePlot.display(brutForceTimeChecker.checkBrutForceTime(7));
    }
}