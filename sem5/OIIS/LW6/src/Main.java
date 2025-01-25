import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Main {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private static final String FILE_NAME = "img_2.png";

    public static void main(String[] args) {
        String imagePath = FILE_NAME;
        Mat src = Imgcodecs.imread(imagePath);

        if (src.empty()) {
            System.out.println("Не удалось загрузить изображение!");
            return;
        }

        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);

        Imgproc.GaussianBlur(gray, gray, new Size(9, 9), 2);

        Mat circles = new Mat();

        Imgproc.HoughCircles(
                gray, circles, Imgproc.HOUGH_GRADIENT,
                1,
                gray.rows() / 8,
                45,
                10,
                5,
                150
        );

        if (circles.cols() > 0) {
            for (int i = 0; i < circles.cols(); i++) {
                double[] circle = circles.get(0, i);
                if (circle == null) continue;

                Point center = new Point(circle[0], circle[1]);
                int radius = (int) Math.round(circle[2]);

                if (!isBall(src, center, radius)) continue;

                drawSolidCube(src, center, radius);
            }
        }

        String outputPath = "output_solid_cube.png";
        Imgcodecs.imwrite(outputPath, src);
        System.out.println("Обработанное изображение сохранено: " + outputPath);
    }

    private static boolean isBall(Mat image, Point center, int radius) {
        Rect roi = new Rect(
                Math.max((int) (center.x - radius), 0),
                Math.max((int) (center.y - radius), 0),
                Math.min(radius * 2, image.cols() - (int) (center.x - radius)),
                Math.min(radius * 2, image.rows() - (int) (center.y - radius))
        );
        if (roi.width <= 0 || roi.height <= 0) return false;

        Mat ballRegion = new Mat(image, roi);
        Mat hsv = new Mat();
        Imgproc.cvtColor(ballRegion, hsv, Imgproc.COLOR_BGR2HSV);

        Scalar lowerWhite = new Scalar(0, 0, 200);
        Scalar upperWhite = new Scalar(180, 30, 255);

        Scalar lowerBlue = new Scalar(90, 50, 50);
        Scalar upperBlue = new Scalar(130, 255, 255);

        Scalar lowerYellow = new Scalar(20, 100, 100);
        Scalar upperYellow = new Scalar(30, 255, 255);

        Mat whiteMask = new Mat();
        Mat blueMask = new Mat();
        Mat yellowMask = new Mat();

        Core.inRange(hsv, lowerWhite, upperWhite, whiteMask);
        Core.inRange(hsv, lowerBlue, upperBlue, blueMask);
        Core.inRange(hsv, lowerYellow, upperYellow, yellowMask);

        double whitePercentage = Core.countNonZero(whiteMask) / (double) (roi.width * roi.height);
        double bluePercentage = Core.countNonZero(blueMask) / (double) (roi.width * roi.height);
        double yellowPercentage = Core.countNonZero(yellowMask) / (double) (roi.width * roi.height);

        int matchCount = 0;
        if (whitePercentage > 0.1) matchCount++;
        if (bluePercentage > 0.1) matchCount++;
        if (yellowPercentage > 0.1) matchCount++;

        return matchCount >= 2;
    }

    private static void drawSolidCube(Mat image, Point center, int radius) {
        int side = radius * 2;
        int depth = radius;

        Point frontTopLeft = new Point(center.x - radius, center.y - radius);
        Point frontTopRight = new Point(center.x + radius, center.y - radius);
        Point frontBottomLeft = new Point(center.x - radius, center.y + radius);
        Point frontBottomRight = new Point(center.x + radius, center.y + radius);

        Point backTopLeft = new Point(frontTopLeft.x - depth, frontTopLeft.y - depth);
        Point backTopRight = new Point(frontTopRight.x - depth, frontTopRight.y - depth);
        Point backBottomLeft = new Point(frontBottomLeft.x - depth, frontBottomLeft.y - depth);
        Point backBottomRight = new Point(frontBottomRight.x - depth, frontBottomRight.y - depth);

        MatOfPoint topFace = new MatOfPoint(
                new Point(frontTopLeft.x, frontTopLeft.y),
                new Point(frontTopRight.x, frontTopRight.y),
                new Point(backTopRight.x, backTopRight.y),
                new Point(backTopLeft.x, backTopLeft.y)
        );
        Imgproc.fillConvexPoly(image, topFace, new Scalar(0, 0, 255)); // Синий цвет

        MatOfPoint leftFace = new MatOfPoint(
                new Point(frontTopLeft.x, frontTopLeft.y),
                new Point(backTopLeft.x, backTopLeft.y),
                new Point(backBottomLeft.x, backBottomLeft.y),
                new Point(frontBottomLeft.x, frontBottomLeft.y)
        );
        Imgproc.fillConvexPoly(image, leftFace, new Scalar(0, 0, 255)); // Зеленый цвет

        Imgproc.rectangle(image, frontTopLeft, frontBottomRight, new Scalar(0, 0, 255), -1); // Красный цвет

        Imgproc.line(image, frontTopLeft, frontTopRight, new Scalar(0, 0, 0), 2);
        Imgproc.line(image, frontTopRight, frontBottomRight, new Scalar(0, 0, 0), 2);
        Imgproc.line(image, frontBottomRight, frontBottomLeft, new Scalar(0, 0, 0), 2);
        Imgproc.line(image, frontBottomLeft, frontTopLeft, new Scalar(0, 0, 0), 2);

        Imgproc.line(image, frontTopLeft, backTopLeft, new Scalar(0, 0, 0), 2);
        Imgproc.line(image, frontTopRight, backTopRight, new Scalar(0, 0, 0), 2);
        Imgproc.line(image, frontBottomLeft, backBottomLeft, new Scalar(0, 0, 0), 2);
        Imgproc.line(image, frontBottomRight, backBottomRight, new Scalar(0, 0, 0), 2);

        Imgproc.line(image, backTopLeft, backTopRight, new Scalar(0, 0, 0), 2);
        Imgproc.line(image, backTopRight, backBottomRight, new Scalar(0, 0, 0), 2);
        Imgproc.line(image, backBottomRight, backBottomLeft, new Scalar(0, 0, 0), 2);
        Imgproc.line(image, backBottomLeft, backTopLeft, new Scalar(0, 0, 0), 2);
    }


}
