/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring2017ip;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

/**
 *
 * @author kmhasan
 */
public class ConvolutionDemo {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    
    public Mat convolute(Mat inputImage, double kernel[][], double anchorX, double anchorY) {
        Mat outputImage = new Mat(inputImage.rows(), inputImage.cols(), inputImage.type());
        
        // Task: write this convolution method that works for
        // any image and any kernel
        for (int i = 0; i < inputImage.rows(); i++)
            for (int j = 0; j < inputImage.cols(); j++)
                outputImage.put(i, j, 127.0);
        
        return outputImage;
    }
    
    public ConvolutionDemo() {
        System.out.println("Version " + Core.VERSION);
        Mat image = Imgcodecs.imread("lena.png", Imgcodecs.IMREAD_GRAYSCALE);
        
        double kernel[][] = {
            {1 / 9.0, 1 / 9.0, 1 / 9.0}, 
            {1 / 9.0, 1 / 9.0, 1 / 9.0}, 
            {1 / 9.0, 1 / 9.0, 1 / 9.0}
        };
        
        Mat outputImage = convolute(image, kernel, 1, 1);
        
        Imgcodecs.imwrite("output.png", outputImage);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new ConvolutionDemo();
    }
    
}
