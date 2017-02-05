/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring2017ip;

import java.util.Arrays;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;

/**
 *
 * @author kmhasan
 */
public class OpenCVDemo {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Welcome to OpenCV " + Core.VERSION);
        Mat image = Imgcodecs.imread("lena.png");
        double values[] = {0, 0, 0};
        for (int i = 0; i < image.height(); i++)
            for (int j = 0; j < image.width(); j++) {
                values = image.get(i, j);
                double average = (values[0] + values[1] + values[2]) / 3.0;
                values[0] = average;
                values[1] = average;
                values[2] = average;
                image.put(i, j, values);
            }
        Imgcodecs.imwrite("lena1.png", image);
    }

}
