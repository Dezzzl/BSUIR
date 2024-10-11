package org.example;

import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;
import marvinplugins.MarvinPluginCollection;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ImageFilterApp {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String imagePath;
        while(true) {
            try {
                System.out.print("Введите путь к изображению: ");
                imagePath = scanner.nextLine();
                loadImage(imagePath);
                break;
            }
            catch(Exception e){
                System.out.println("Неверный путь");
            }
        }
        MarvinImage image = MarvinImageIO.loadImage(imagePath);

        MarvinImage output;

        while(true) {
            printMenu();
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    output = applyMosaicFilter(image);
                    break;
                case 2:
                    output = applyGrayscaleFilter(image);
                    break;
                case 3:
                    output = applyBlurFilter(image);
                    break;
                case 4:
                    output = applyInvertFilter(image);
                    break;
                case 5:
                    output = applyBrightnessAndContrast(image);
                    break;
                default:
                    System.out.println("Некорректный выбор");
                    return;
            }

            MarvinImageIO.saveImage(output, "output.jpg");

            BufferedImage resizedOutput = resizeImage(output.getBufferedImage(), 800, 600);
            showImage(resizedOutput, "Filtered Image");
        }
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();

        if (originalWidth <= targetWidth && originalHeight <= targetHeight) {
            return originalImage;
        }

        float widthRatio = (float) targetWidth / originalWidth;
        float heightRatio = (float) targetHeight / originalHeight;
        float ratio = Math.min(widthRatio, heightRatio);

        int newWidth = Math.round(originalWidth * ratio);
        int newHeight = Math.round(originalHeight * ratio);

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g2d.dispose();

        return resizedImage;
    }

    private static void showImage(BufferedImage img, String title) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ImageIcon imageIcon = new ImageIcon(img);
        JLabel jLabel = new JLabel(imageIcon);
        frame.getContentPane().add(jLabel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static MarvinImage applyMosaicFilter(MarvinImage image) {
        MarvinImage outputImage = image.clone();
        MarvinPluginCollection.mosaic(image, outputImage, "squares", 5, false);
        System.out.println("Применён фильтр мозаики");
        return outputImage;
    }

    private static MarvinImage applyGrayscaleFilter(MarvinImage image) {
        MarvinImage outputImage = image.clone();
        MarvinPluginCollection.grayScale(image, outputImage);
        System.out.println("Применён чёрно-белый фильтр");
        return outputImage;
    }

    private static MarvinImage applyBlurFilter(MarvinImage image) {
        MarvinImage outputImage = image.clone();
        MarvinPluginCollection.gaussianBlur(image, outputImage, 5);
        System.out.println("Применён фильтр размытия");
        return outputImage;
    }

    private static MarvinImage  applyInvertFilter(MarvinImage image) {
        MarvinImage outputImage = image.clone();
        MarvinPluginCollection.invertColors(image, outputImage);
        System.out.println("Применён фильтр инверсии цвета");
        return outputImage;
    }

    private static MarvinImage applyBrightnessAndContrast(MarvinImage image) {
        MarvinImage outputImage = image.clone();
        MarvinPluginCollection.brightnessAndContrast(image, outputImage, -50, 10);
        System.out.println("Применён фильтр изменения яркости и контраста");
        return outputImage;
    }

    public static BufferedImage loadImage(String path) throws IOException {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new IOException("Ошибка загрузки изображения: " + e.getMessage());
        }
        return img;
    }

    private static void printMenu(){
        System.out.println("Выберите фильтр для применения:");
        System.out.println("1. Фильтр мозаики");
        System.out.println("2. Чёрно-белый фильтр");
        System.out.println("3. Размытие");
        System.out.println("4. Инверсия цвета");
        System.out.println("5. Повышение яркости и контраста");
    }
}

