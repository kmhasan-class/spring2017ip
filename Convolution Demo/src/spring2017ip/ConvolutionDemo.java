/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring2017ip;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author kmhasan
 */
public class ConvolutionDemo {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    
    // handle the anchor!
    public Mat convolute(Mat inputImage, double kernel[][], double anchorX, double anchorY) {
        Mat outputImage = new Mat(inputImage.rows(), inputImage.cols(), inputImage.type());
        
        // fix this code so that it works for any sized kernel
        for (int i = 1; i < inputImage.rows() - 1; i++)
            for (int j = 1; j < inputImage.cols() - 1; j++) {
                double sum = 0;
                
                for (int r = 0; r < kernel.length; r++)
                    for (int c = 0; c < kernel[r].length; c++) {
                        double pixel[] = inputImage.get(i - kernel.length / 2 + r, 
                                j - kernel[0].length / 2 + c);
                        double product = kernel[r][c] * pixel[0];
                        sum = sum + product;
                    }
                
                outputImage.put(i, j, sum);
            }
        return outputImage;
    }
    
    public Mat doBlur(Mat inputImage) {
        double kernel[][] = {
            {1, 2, 1}, 
            {2, 4, 2}, 
            {1, 2, 1}
        };
        
        for (int r = 0; r < kernel.length; r++)
            for (int c = 0; c < kernel[r].length; c++)
                kernel[r][c] /= 16.0;
        
        Mat output = convolute(inputImage, kernel, 1, 1);
        return output;
    }

    public Mat doSobelGx(Mat inputImage) {
        double kernel[][] = {
            {-1, -2, -1}, 
            {+0, +0, +0}, 
            {+1, +2, +1}
        };
        
        for (int r = 0; r < kernel.length; r++)
            for (int c = 0; c < kernel[r].length; c++)
                kernel[r][c] /= 8.0;
        
        Mat output = convolute(inputImage, kernel, 1, 1);
        return output;
    }
    
    public Mat doCanny(Mat inputImage) {
        Mat canny = null;
        Mat blurred = doBlur(inputImage);
        Mat gx = doSobelGx(blurred);
        Imgcodecs.imwrite("gx.png", gx);
        canny = blurred;
        return canny;
    }
    
    public ConvolutionDemo() {
        System.out.println("Version " + Core.VERSION);
        Mat image = Imgcodecs.imread("lena.png", Imgcodecs.IMREAD_GRAYSCALE);

        Mat canny = doCanny(image);
        Imgcodecs.imwrite("canny.png", canny);
//        Mat canny = Imgcodecs.imread("lena.png", Imgcodecs.IMREAD_GRAYSCALE);
//        
//        Imgproc.Canny(image, canny, 80, 240);
//        Imgcodecs.imwrite("canny.png", canny);
        /*
        double kernel[][] = {
            {-1 / 8.0, -2 / 8.0, -1 / 8.0}, 
            {0 / 8.0, 0 / 8.0, 0 / 8.0}, 
            {+1 / 8.0, +2 / 8.0, +1 / 8.0}
        };
        
        Mat outputImage = convolute(image, kernel, 1, 1);
        
        Imgcodecs.imwrite("grayscale.png", image);
        Imgcodecs.imwrite("output.png", outputImage);*/
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new ConvolutionDemo();
    }
    
}
