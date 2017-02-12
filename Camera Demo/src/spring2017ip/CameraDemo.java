/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring2017ip;

import java.util.concurrent.ScheduledExecutorService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.opencv.core.Core;

/**
 *
 * @author kmhasan
 */
public class CameraDemo extends Application {
    private static Stage mainStage;
    private static ScheduledExecutorService executorService;
    
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void setMainStage(Stage aMainStage) {
        mainStage = aMainStage;
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        this.setMainStage(stage);
        System.out.println(Core.VERSION);
        Parent root = FXMLLoader.load(getClass().getResource("CameraView.fxml"));
        stage.setTitle("OpenCV Camera Demo");
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public static ScheduledExecutorService getExecutorService() {
        return executorService;
    }

    public static void setExecutorService(ScheduledExecutorService executorService) {
        executorService = executorService;
    }
    
}
