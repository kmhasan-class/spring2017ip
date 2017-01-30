/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring2017ip;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kmhasan
 */
public class BMPReader {


    
    public BMPReader() {
        Bitmap bitmap = Bitmap.readBitmap("sample.bmp");
        System.out.printf("File size: %d bytes\n", bitmap.getFileSize());
        System.out.printf("Width: %d pixels\n", bitmap.getWidth());
        System.out.printf("Height: %d pixels\n", bitmap.getHeight());
        System.out.printf("Pixel data at 0 0 %s\n", Arrays.toString(bitmap.getRGB(0, 0)));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new BMPReader();
    }
    
}
