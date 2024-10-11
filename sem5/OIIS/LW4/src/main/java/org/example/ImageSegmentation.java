package org.example;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class ImageSegmentation {
    static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Mat image;
        while(true) {
                System.out.println("Введите путь к изображению: ");
                String path = scanner.next();
                image = Imgcodecs.imread(path, Imgcodecs.IMREAD_GRAYSCALE);
            if (!image.empty()) {
                break;
            } else {
                System.out.println("Ошибка: не удалось загрузить изображение. Попробуйте снова.");
            }
        }
        Mat blurredImage = new Mat();
        Imgproc.GaussianBlur(image, blurredImage, new Size(5, 5), 0);

        Mat edges = new Mat();
        Imgproc.Canny(blurredImage, edges, 100, 200);

        Imgcodecs.imwrite("edges.jpg", edges);

        BufferedImage bufferedImage = convertMatToBufferedImage(edges);
        BufferedImage scaledImage = scaleImage(bufferedImage, 800, 600);
        displayImage(scaledImage, "Границы изображения");
    }

    public static BufferedImage convertMatToBufferedImage(Mat mat) {
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", mat, matOfByte);
        byte[] byteArray = matOfByte.toArray();
        BufferedImage bufImage = null;
        try {
            bufImage = ImageIO.read(new ByteArrayInputStream(byteArray));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bufImage;
    }

    public static void displayImage(Image img, String title) {
        ImageIcon icon = new ImageIcon(img);
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(img.getWidth(null) + 50, img.getHeight(null) + 50);
        JLabel lbl = new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static BufferedImage scaleImage(BufferedImage img, int maxWidth, int maxHeight) {
        int width = img.getWidth();
        int height = img.getHeight();
        double aspectRatio = (double) width / height;

        if (width > maxWidth) {
            width = maxWidth;
            height = (int) (width / aspectRatio);
        }
        if (height > maxHeight) {
            height = maxHeight;
            width = (int) (height * aspectRatio);
        }

        Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage bufferedScaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedScaledImage.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();

        return bufferedScaledImage;
    }
}
