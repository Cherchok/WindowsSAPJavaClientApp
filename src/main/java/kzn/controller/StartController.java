package kzn.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import kzn.model.ClientActivity;
import kzn.model.httpsconnection.Connection;


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

    //Обработка попытки подключения
    public void afterTryConnect(boolean connectionSuccess) {
        if (!connectionSuccess &&
                ClientActivity.connection.getStatus() == Connection.ConnectionStatus.IP_ERROR) {
            this.changeScene("src/main/java/kzn/view/Connect.fxml", "Server connection");
        } else if (connectionSuccess &&
                ClientActivity.connection.getStatus() == Connection.ConnectionStatus.SUCCESS) {
            //Запустить выполнение gotSystemsList(getSystemsAsync()) в отдельном потоке
            CompletableFuture.supplyAsync(this::getSystemsAsync).thenAccept(this::gotSystemsList);
        }
    }

    //Получение списка систем
    public boolean getSystemsAsync() {
        ClientActivity.setSystems(ClientActivity.connection.getSystemsList());
        if (ClientActivity.connection.getStatus() == Connection.ConnectionStatus.SUCCESS) return true;
        else return false;
    }

    //Обрабока результата получения списка систем
    public void gotSystemsList(boolean requestSuccess) {
        if (requestSuccess) {
            this.changeScene("src/main/java/kzn/view/Systems.fxml", "System connection");
        }
        else {
            this.changeScene("src/main/java/kzn/view/Connect.fxml", "Server connection");
        }
    }

}
