import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class BrightnessEqualizer {

    public static BufferedImage loadImage(String path) throws IOException {
        return ImageIO.read(new File(path));
    }

    public static void saveImage(BufferedImage img, String path) throws IOException {
        ImageIO.write(img, "jpg", new File(path));
    }

    public static double calculateAverageBrightness(BufferedImage img) {
        long totalBrightness = 0;
        int width = img.getWidth();
        int height = img.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = img.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;
                int brightness = (r + g + b) / 3;
                totalBrightness += brightness;
            }
        }
        return totalBrightness / (double) (width * height);
    }

    public static BufferedImage equalizeBrightness(BufferedImage img, double targetBrightness, double currentBrightness) {
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage result = new BufferedImage(width, height, img.getType());

        double scaleFactor = targetBrightness / currentBrightness;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = img.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                r = (int) (r * scaleFactor);
                g = (int) (g * scaleFactor);
                b = (int) (b * scaleFactor);

                r = Math.min(255, Math.max(0, r));
                g = Math.min(255, Math.max(0, g));
                b = Math.min(255, Math.max(0, b));

                int newRGB = (r << 16) | (g << 8) | b;
                result.setRGB(x, y, newRGB);
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите путь к первому изображению:");
        String imagePath1 = scanner.nextLine();
        System.out.println("Введите путь ко второму изображению:");
        String imagePath2 = scanner.nextLine();
        BufferedImage img1 = loadImage(imagePath1);
        BufferedImage img2 = loadImage(imagePath2);
        double avgBrightness1 = calculateAverageBrightness(img1);
        double avgBrightness2 = calculateAverageBrightness(img2);
        BufferedImage equalizedImage = equalizeBrightness(img1, avgBrightness2, avgBrightness1);
        saveImage(equalizedImage, "equalized_image.jpg");
        System.out.println("Яркость первого изображения выравнена по второму. Результат сохранен как 'equalized_image.jpg'.");
    }
}