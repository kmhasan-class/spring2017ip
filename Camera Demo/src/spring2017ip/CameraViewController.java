/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring2017ip;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.io.ByteArrayInputStream;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author kmhasan
 */
public class CameraViewController implements Initializable {
    
    @FXML
    private ImageView imageView;
    private VideoCapture videoCapture;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        videoCapture = new VideoCapture();
        videoCapture.open(0);
        captureFrame();
//        videoCapture.release();
    }    

    private void captureFrame() {
        Mat frame = new Mat();
        videoCapture.read(frame);
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".png", frame, matOfByte);
        Image image = new Image(new ByteArrayInputStream(matOfByte.toArray()));
        imageView.setImage(image);
        
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("ddMMyyyy_");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hhmmss_");
        String filename = localDate.format(dateFormatter) + "_" + localTime.format(timeFormatter) + ".png";
        Imgcodecs.imwrite(filename, frame);
    }
    
    @FXML
    private void handleImageCaptureAction(ActionEvent event) {
        System.out.println("Capturing a frame");
        captureFrame();
    }
    
}
