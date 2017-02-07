/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring2017ip;

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
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        System.out.println(Core.VERSION);
        Parent root = FXMLLoader.load(getClass().getResource("CameraView.fxml"));
        
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
    
}
