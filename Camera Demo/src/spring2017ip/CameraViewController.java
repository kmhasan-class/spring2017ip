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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 *
 * @author kmhasan
 */
public class CameraViewController implements Initializable {

    @FXML
    private ImageView imageView;
    private VideoCapture videoCapture;
    private Mat frame;
    private Mat frame1, frame2;
    private Image image;
    private Image image1, image2;

    @FXML
    private Label statusLabel;
    @FXML
    private ImageView frame1View;
    @FXML
    private ImageView frame2View;
    @FXML
    private ImageView resultView;
    private ScheduledExecutorService executorService;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        executorService = Executors.newSingleThreadScheduledExecutor();

        videoCapture = new VideoCapture();
        videoCapture.open(0);
        showFrame();
        statusLabel.setText("Started camera");
        CameraDemo.getMainStage().setOnCloseRequest(e -> {
            videoCapture.release();
            executorService.shutdownNow();
            Platform.exit();
        });
    }

    private Image matToImage(Mat mat) {
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".png", mat, matOfByte);
        Image newImage = new Image(new ByteArrayInputStream(matOfByte.toArray()));
        return newImage;
    }

    private Mat rgbToGray(Mat input) {
        Mat output = null;
        // write your code to convert a RGB Mat to Gray Mat
        return output;
    }
    
    private void showFrame() {
        frame = new Mat();
        frame1 = new Mat();
        frame2 = frame1.clone();

        Runnable frameGrabber = () -> {
            videoCapture.read(frame);
            image = matToImage(frame);
            Platform.runLater(() -> {
                imageView.setImage(image);
                frame1View.setImage(image);
                //frame2View.setImage(matToImage(frame2));
                // add some code to ensure that we see
                // the next frame in frame2View
            });
        };

        executorService.scheduleAtFixedRate(frameGrabber, 0, 10, TimeUnit.MILLISECONDS);
    }

    @FXML
    private void saveFrame() {
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("ddMMyyyy_");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hhmmss_");
        String filename = localDate.format(dateFormatter) + "_" + localTime.format(timeFormatter) + ".png";
        Imgcodecs.imwrite(filename, frame);
    }

    @FXML
    private void saveSuccessiveFrames(ActionEvent event) {
    }

}
