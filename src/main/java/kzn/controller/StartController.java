package kzn.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kzn.model.ClientActivity;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

public class StartController extends Parent {

    //Текущая Stage
    private Stage stage;

    @FXML
    private ProgressIndicator progressInd;
    @FXML
    private AnchorPane mainPane;

    //Получение Stage от предыдущего контроллера
    public void setPrevStage(Stage stage){
        this.stage = stage;
    }

    //Запуск попытки подключения асинхронно
    public void onShow() {
        //Platform.runLater(this::afterShow);

        CompletableFuture.supplyAsync(this::tryConnectAsync).thenAccept(this::afterShow);
    }

    //Попытка подключения к серверу
    public boolean tryConnectAsync() {
        return ClientActivity.connection.tryConnect();
    }

    //Обработка результата попытки подключения
    public void afterShow(boolean connectionSuccess) {
        //boolean connectionSuccess = ClientActivity.connection.tryConnect();
        if (!connectionSuccess) {

            //Обработка работы с UI в JavaFX Application Thread
            Platform.runLater(() -> {
                try {
                    URL url1 = (new File("src/main/java/kzn/view/Connect.fxml")).toURI().toURL();
                    FXMLLoader myLoader = new FXMLLoader(url1);
                    Pane pane = (Pane) myLoader.load();
                    ConnectController controller = (ConnectController) myLoader.getController();
                    stage.setTitle("Подключение к серверу");

                    stage.setScene(new Scene(pane));
                    stage.sizeToScene();
                } catch (Exception ex) {
                    System.out.println("Error while opening form Connect.fxml");
                    ex.printStackTrace();
                }
            });


        }
    }
}
