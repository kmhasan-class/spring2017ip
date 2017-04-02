package thresholding.demo;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class ThresholdingDemo {

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

    public static int[] getHistogram(Mat inputImage) {
        int histogram[] = new int[256];
        for (int r = 0; r < inputImage.rows(); r++)
            for (int c = 0; c < inputImage.cols(); c++) {
                double intensity[] = inputImage.get(r, c);
                int value = (int) intensity[0];
                histogram[value]++;
            }
        return histogram;
    }
    
    // improved Basic Global Thresholding using Histograms
    public static int improvedBGT(Mat inputImage) {
        int histogram[] = getHistogram(inputImage);
        double threshold = 127;
        double newThreshold = threshold;
        int iteration = 0;
        
        do {
            iteration++;
            threshold = newThreshold;
            double g1sum = 0;
            int g1count = 0;
            double g2sum = 0;
            int g2count = 0;

            // we don't need this loop
            // improve this code some more by getting rid of this
            // hint: use some extra arrays to keep the cumulative sums
            for (int i = 0; i < histogram.length; i++) {
                if (i <= threshold) {
                    g1count += histogram[i];
                    g1sum += i * histogram[i];
                } else {
                    g2count += histogram[i];
                    g2sum += i * histogram[i];
                }
            }

            double m1 = g1sum / g1count;
            double m2 = g2sum / g2count;

            newThreshold = (m1 + m2) / 2;
        } while (Math.abs(threshold - newThreshold) > 1);
        
        System.out.println("Number of iterations: " + iteration);
        return (int) newThreshold;
    }
    
    // DIP 3rd Edition page 742
    public static int basicGlobalThresholding(Mat inputImage) {
        // step 1
        // initial guess for threshold, T
        double threshold = 127;
        double newThreshold = threshold;
        int iteration = 0;
        
        do {
            iteration++;
            threshold = newThreshold;
            double g1sum = 0;
            int g1count = 0;
            double g2sum = 0;
            int g2count = 0;

            for (int r = 0; r < inputImage.rows(); r++) {
                for (int c = 0; c < inputImage.cols(); c++) {
                    double intensity[] = inputImage.get(r, c);
                    if (intensity[0] <= threshold) {
                        g1sum += intensity[0];
                        g1count++;
                    } else {
                        g2sum += intensity[0];
                        g2count++;
                    }
                }
            }

            double m1 = g1sum / g1count;
            double m2 = g2sum / g2count;

            newThreshold = (m1 + m2) / 2;
        } while (Math.abs(threshold - newThreshold) > 1);
        
        System.out.println("Number of iterations: " + iteration);
        return (int) newThreshold;
    }

    public static void main(String[] args) {
        Mat inputImage = Imgcodecs.imread("meat.jpg", Imgcodecs.IMREAD_GRAYSCALE);
        
        // epoch - January 1, 1970
        long startTime = System.currentTimeMillis();
        //int threshold = basicGlobalThresholding(inputImage);
        int threshold = improvedBGT(inputImage);
        long stopTime = System.currentTimeMillis();
        System.out.printf("Time taken for basic global thresholding: %.2f\n", (stopTime - startTime) / 1000.0);
        System.out.println("Threshold " + threshold);
        Mat outputImage = applyThreshold(inputImage, threshold);
        Imgcodecs.imwrite("output.png", outputImage);
    }

}
