package kzn.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kzn.model.ClientActivity;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import java.util.TimerTask;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class StartController implements Initializable {

    private Stage stage;

    public void setPrevStage(Stage stage){
        this.stage = stage;
    }

    public void onShow() {
        boolean connectionSuccess = ClientActivity.connection.tryConnect();
        if (!connectionSuccess) {
            try {
                URL url1 = (new File("src/main/java/kzn/view/Connect.fxml")).toURI().toURL();
                FXMLLoader myLoader = new FXMLLoader(url1);
                Pane pane = (Pane) myLoader.load();
                ConnectController controller = (ConnectController) myLoader.getController();
                this.stage.setTitle("Подключение к серверу");

                this.stage.setScene(new Scene(pane, 350, 275));


            } catch (Exception ex) {
                System.out.println("Error while opening form Connect.fxml");
                ex.printStackTrace();

            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
