package kzn.controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public abstract class Controller {
    //Текущая Stage
    protected Stage stage;

    //Получение Stage от предыдущего контроллера
    public void setPrevStage(Stage stage) {
        this.stage = stage;
    }

    public void changeScene(String scenePath, String title) {
        Platform.runLater(() -> {
                    try {
                        URL url1 = (new File(scenePath)).toURI().toURL();
                        FXMLLoader myLoader = new FXMLLoader(url1);
                        Pane pane = (Pane) myLoader.load();
                        stage.setTitle(title);

                        stage.setScene(new Scene(pane));
                        stage.sizeToScene();
                    } catch (Exception ex) {
                        System.out.println("Error while changing Scene to" + scenePath);
                        ex.printStackTrace();
                    }
                }
        );
    }
}
