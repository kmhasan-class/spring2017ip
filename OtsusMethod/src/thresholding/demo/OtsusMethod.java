package thresholding.demo;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class OtsusMethod {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static Mat applyThreshold(Mat inputImage, int threshold) {
        Mat outputImage = new Mat(inputImage.rows(), inputImage.cols(), inputImage.type());
        for (int r = 0; r < inputImage.rows(); r++) {
            for (int c = 0; c < inputImage.cols(); c++) {
                double intensity[] = inputImage.get(r, c);
                if (intensity[0] <= threshold) {
                    outputImage.put(r, c, 0);
                } else {
                    outputImage.put(r, c, 255);
                }
            }
        }
        return outputImage;
    }

    public static long[] getHistogram(Mat inputImage) {
        long histogram[] = new long[256];
        for (int r = 0; r < inputImage.rows(); r++)
            for (int c = 0; c < inputImage.cols(); c++) {
                double intensity[] = inputImage.get(r, c);
                int value = (int) intensity[0];
                histogram[value]++;
            }
        return histogram;
    }
    
    public static int getOtsusTheshold(Mat inputImage) {
        long frequency[] = getHistogram(inputImage);
        double p[] = new double[frequency.length];
        long totalPixels = inputImage.rows() *  inputImage.cols();
        
        for (int i = 0; i < frequency.length; i++)
            p[i] = frequency[i] / ((double) totalPixels);
        
        int k = 50;
        double m1k = 0;
        double m2k = 0;
        
        double p1k = 0;
        double p2k = 0;
        for (int i = 0; i <= k; i++)
            p1k += p[i];
        p2k = 1 - p1k;
        
        for (int i = 0; i <= k; i++)
            m1k += i * p[i];
        m1k /= p1k;

        for (int i = k + 1; i < frequency.length; i++)
            m2k += i * p[i];
        m2k /= p2k;
        
        return 0;
        
    }
    
    public static void main(String[] args) {
        Mat inputImage = Imgcodecs.imread("meat.jpg", Imgcodecs.IMREAD_GRAYSCALE);
        
        // epoch - January 1, 1970
        long startTime = System.currentTimeMillis();
        int threshold = getOtsusTheshold(inputImage);
        long stopTime = System.currentTimeMillis();
        System.out.printf("Time taken for Otsu's Method: %.2f\n", (stopTime - startTime) / 1000.0);
        System.out.println("Threshold " + threshold);
        Mat outputImage = applyThreshold(inputImage, threshold);
        Imgcodecs.imwrite("output.png", outputImage);
    }

}
