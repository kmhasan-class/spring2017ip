/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring2017ip;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kmhasan
 */
public class Bitmap {
    private int fileSize;
    private byte colorData[][][];
    private byte allData[];
    private int width;
    private int height;
    
    private Bitmap() {
        // don't want bitmaps to be instantiated with constructor calls from outside
        // they need to read a bitmap by providing a filename
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public byte[][][] getColorData() {
        return colorData;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    private static int byteArrayToInt(byte[] array) {
        // bmp bytes are written in little endian (b0, b1, b2 ...)
        int intValue = 0;
        for (int i = array.length - 1; i >= 0; i--)
            intValue = (intValue << 8) | (array[i] & 0xFF); // & 0xFF to convert to unsigned int
        return intValue;
    }
    
    public static Bitmap readBitmap(String filename) {
        // based on https://en.wikipedia.org/wiki/BMP_file_format
        Bitmap bitmap = null;
        try {
            RandomAccessFile input = new RandomAccessFile(filename, "r");
            bitmap = new Bitmap();

            bitmap.allData = new byte[(int) input.length()];
            input.read(bitmap.allData);
            
            input.seek(0);
            byte signatureByteArray[] = new byte[2];
            input.read(signatureByteArray);
            System.out.println((char) signatureByteArray[0]);
            System.out.println((char) signatureByteArray[1]);
            
            input.seek(2);
            byte fileSizeByteArray[] = new byte[4];
            input.read(fileSizeByteArray);
            int fileSize = byteArrayToInt(fileSizeByteArray);
            bitmap.fileSize = fileSize;

            input.seek(18);
            byte widthByteArray[] = new byte[4];
            input.read(widthByteArray);
            int width = byteArrayToInt(widthByteArray);
            bitmap.width = width;

            input.seek(22);
            byte heightByteArray[] = new byte[4];
            input.read(heightByteArray);
            bitmap.height = byteArrayToInt(heightByteArray);
            
            bitmap.colorData = new byte[bitmap.width][bitmap.height][3];
            
            input.seek(10);
            byte pixelOffsetByteArray[] = new byte[4];
            input.read(pixelOffsetByteArray);
            int pixelOffset = byteArrayToInt(pixelOffsetByteArray);
            
            input.seek(pixelOffset);
            byte colorByteArray[] = new byte[bitmap.width * bitmap.height * 3];
            input.read(colorByteArray);
            for (int c = 0; c < bitmap.width; c++)
                for (int r = 0; r < bitmap.height; r++) {
                    bitmap.colorData[c][r][0] = colorByteArray[c * bitmap.height + r + 0];
                    bitmap.colorData[c][r][1] = colorByteArray[c * bitmap.height + r + 1];
                    bitmap.colorData[c][r][2] = colorByteArray[c * bitmap.height + r + 2];
                }
            input.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BMPReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BMPReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bitmap;
    }
    
    public int[] getRGB(int column, int row) {
        int returnValue[] = new int[3];
        returnValue[0] = this.colorData[column][row][2] & 0xFF;
        returnValue[1] = this.colorData[column][row][1] & 0xFF;
        returnValue[2] = this.colorData[column][row][0] & 0xFF;
        return returnValue;
    }
    
    public void setRGB(int column, int row, int values[]) {
        // WRITE CODE FOR THIS ONE
        // values is an array containing R, G, B values
    }
    
    public void writeBitmap(String filename) {
        // WRITE CODE FOR THIS ONE
        // save the current bitmap object as a .bmp file on your computer
        // view it with any image viewer
    }
}
