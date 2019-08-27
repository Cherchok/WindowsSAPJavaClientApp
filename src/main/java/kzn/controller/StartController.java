package kzn.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import kzn.model.ClientActivity;
import kzn.model.Connection;


import java.util.concurrent.CompletableFuture;

public class StartController extends Controller {

    //FXML elements
    @FXML
    private ProgressIndicator progressInd;
    @FXML
    private AnchorPane mainPane;

    //Запуск попытки подключения асинхронно
    public void onShow() {
        CompletableFuture.supplyAsync(this::tryConnectAsync).thenAccept(this::afterTryConnect);
    }

    //Попытка подключения к серверу
    public boolean tryConnectAsync() {
        return ClientActivity.connection.tryConnect();
    }

    public boolean getSystemsAsync() {
        ClientActivity.setSystems(ClientActivity.connection.getSystemsList());
        if (ClientActivity.connection.getStatus() == Connection.ConnectionStatus.SUCCESS) return true;
        else return false;
    }

    public void gotSystemsList(boolean requestSuccess) {
        if (requestSuccess) {
            this.changeScene("src/main/java/kzn/view/Systems.fxml", "Подключение к системе");
        }
        else {
            this.changeScene("src/main/java/kzn/view/Connect.fxml", "Подключение к серверу");
        }
    }

    public void afterTryConnect(boolean connectionSuccess) {
        if (!connectionSuccess &&
                ClientActivity.connection.getStatus() == Connection.ConnectionStatus.IP_ERROR) {
            this.changeScene("src/main/java/kzn/view/Connect.fxml", "Подключение к серверу");
        } else if (connectionSuccess &&
                ClientActivity.connection.getStatus() == Connection.ConnectionStatus.SUCCESS) {
            CompletableFuture.supplyAsync(this::getSystemsAsync).thenAccept(this::gotSystemsList);
        }
    }

    //Обработка результата попытки подключения
    public void afterShow(boolean connectionSuccess) {
        //boolean connectionSuccess = ClientActivity.connection.tryConnect();
        if (!connectionSuccess) {

            //Обработка работы с UI в JavaFX Application Thread
//            Platform.runLater(() -> {
//                try {
//                    URL url1 = (new File("src/main/java/kzn/view/Connect.fxml")).toURI().toURL();
//                    FXMLLoader myLoader = new FXMLLoader(url1);
//                    Pane pane = (Pane) myLoader.load();
//                    ConnectController controller = (ConnectController) myLoader.getController();
//                    controller.netErrorAlert.show();
//                    stage.setTitle("Подключение к серверу");
//
//                    stage.setScene(new Scene(pane));
//                    stage.sizeToScene();
//                } catch (Exception ex) {
//                    System.out.println("Error while opening form Connect.fxml");
//                    ex.printStackTrace();
//                }
//            });

            this.changeScene("src/main/java/kzn/view/Connect.fxml", "Подключение к серверу");

        }
        else {

        }
    }
}
