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
public class RGBToGrayscale {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    // try this out: http://www.tannerhelland.com/3643/grayscale-image-algorithm-vb6/
    public void toGrayScale(String inputFilename, String outputFilename, GrayscaleModel model) {
        Mat inputImage = Imgcodecs.imread(inputFilename);

        for (int row = 0; row < inputImage.height(); row++) {
            for (int col = 0; col < inputImage.width(); col++) {
                double rgb[] = inputImage.get(row, col);
                double average = 0;
                switch (model) {
                    case AVERAGE:
                        average = (rgb[0] + rgb[1] + rgb[2]) / 3;
                        break;
                    case LUMINOSITY:
                        // R*0.3 + G*0.59 + B*0.11
                        average = rgb[2] * 0.30 + rgb[1] * 0.59 + rgb[0] * 0.11;
                        break;
                    default:
                        break;
                }
                rgb[0] = average;
                rgb[1] = average;
                rgb[2] = average;
                inputImage.put(row, col, rgb);
            }
        }
        Imgcodecs.imwrite(outputFilename, inputImage);
    }

    public RGBToGrayscale() {
        toGrayScale("lena.png", "lena_grayscale_average.jpg", GrayscaleModel.AVERAGE);
        toGrayScale("lena.png", "lena_grayscale_luminosity.jpg", GrayscaleModel.LUMINOSITY);
    }

    public static void main(String[] args) {
        new RGBToGrayscale();
    }

}
