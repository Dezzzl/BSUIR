import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Color;

public class StereoscopicImage {

    public static BufferedImage loadImage(String path) throws IOException {
        return ImageIO.read(new File(path));
    }

    public static BufferedImage createAnaglyph(BufferedImage leftImage, BufferedImage rightImage) {
        int width = Math.min(leftImage.getWidth(), rightImage.getWidth());
        int height = Math.min(leftImage.getHeight(), rightImage.getHeight());
        BufferedImage anaglyph = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color leftColor = new Color(leftImage.getRGB(x, y));
                Color rightColor = new Color(rightImage.getRGB(x, y));

                int red = leftColor.getRed();
                int green = rightColor.getGreen();
                int blue = rightColor.getBlue();

                Color anaglyphColor = new Color(red, green, blue);
                anaglyph.setRGB(x, y, anaglyphColor.getRGB());
            }
        }

        return anaglyph;
    }

    public static void saveImage(BufferedImage image, String outputPath) throws IOException {
        ImageIO.write(image, "png", new File(outputPath));
    }

    public static void main(String[] args) {
        try {
            BufferedImage leftImage = loadImage("left.jpg");
            BufferedImage rightImage = loadImage("right.jpg");

            BufferedImage anaglyph = createAnaglyph(leftImage, rightImage);

            saveImage(anaglyph, "anaglyph_output.png");

            System.out.println("Анаглифическое изображение создано и сохранено в anaglyph_output.png");
        } catch (IOException e) {
            System.out.println("Ошибка при работе с изображениями: " + e.getMessage());
        }
    }
}
