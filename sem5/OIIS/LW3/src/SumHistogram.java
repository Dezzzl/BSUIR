import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class SumHistogram {
    static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    public static void main(String[] args) {
        String imagePath = "equalized_image.jpg";
        Mat image = Imgcodecs.imread(imagePath);

        if (image.empty()) {
            System.out.println("Ошибка: изображение не загружено.");
            return;
        }

        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);

        Mat hist = new Mat();
        int histSize = 256;
        MatOfInt histSizeMat = new MatOfInt(histSize);
        MatOfFloat histRange = new MatOfFloat(0f, 256f);
        MatOfInt channels = new MatOfInt(0);

        Imgproc.calcHist(java.util.Collections.singletonList(grayImage), channels, new Mat(), hist, histSizeMat, histRange);

        List<Double> histogramData = new ArrayList<>();
        for (int i = 0; i < histSize; i++) {
            histogramData.add(hist.get(i, 0)[0]);
        }

        double maxHistValue = histogramData.stream().max(Double::compare).orElse(1.0);
        for (int i = 0; i < histogramData.size(); i++) {
            histogramData.set(i, histogramData.get(i) / maxHistValue * 400);        }

        JFrame frame = new JFrame("Гистограмма изображения");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.add(new HistogramPanel(histogramData));
        frame.setVisible(true);
    }

    static class HistogramPanel extends JPanel {
        private final List<Double> histogramData;

        public HistogramPanel(List<Double> histogramData) {
            this.histogramData = histogramData;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            int panelWidth = getWidth();
            int panelHeight = getHeight();
            int binWidth = panelWidth / histogramData.size();

            g2d.setColor(Color.BLACK);
            g2d.drawLine(50, panelHeight - 50, 50, 50);
            g2d.drawLine(50, panelHeight - 50, panelWidth - 50, panelHeight - 50);

            g2d.drawString("Интенсивность", panelWidth / 2, panelHeight - 20);
            g2d.drawString("Количество", 10, panelHeight / 2);

            g2d.setColor(Color.BLACK);
            for (int i = 0; i < histogramData.size(); i++) {
                int x = 50 + i * binWidth;
                int y = panelHeight - 50;
                int barHeight = histogramData.get(i).intValue();

                g2d.fillRect(x, y - barHeight, binWidth, barHeight);
            }
        }
    }
}

