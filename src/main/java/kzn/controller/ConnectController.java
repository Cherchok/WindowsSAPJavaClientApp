package kzn.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;


import kzn.model.ClientActivity;
import kzn.model.Connection;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.*;
import java.util.concurrent.CompletableFuture;

//Контроллер формы ConnectController.fxml
public class ConnectController extends Controller implements Initializable {

    //FXML elements
    @FXML
    private TextField ipField;
    @FXML
    private ProgressIndicator tryingToConnectIndicator;

    //Предупреждение об ошибке подключения к серверу
    public static final Alert netErrorAlert = new Alert(AlertType.ERROR, "Ошибка подключения, проверьте IP адрес сервера", ButtonType.OK);

    //Отображение IP-адреса при открытии формы
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        netErrorAlert.setTitle("Connection error");
        ipField.setText(ClientActivity.connection.ipHolder.getProperty("ip"));
        netErrorAlert.show();
    }

    //Попытка подключения к серверу
    public boolean tryConnectAsync() {
        return ClientActivity.connection.tryConnect();
    }

    public void afterTryConnect(boolean connectionSuccess) {
        if (!connectionSuccess &&
                ClientActivity.connection.getStatus() == Connection.ConnectionStatus.IP_ERROR) {
            Platform.runLater(() -> {
                tryingToConnectIndicator.setVisible(false);
                netErrorAlert.showAndWait();
            });
        } else if (connectionSuccess &&
                ClientActivity.connection.getStatus() == Connection.ConnectionStatus.SUCCESS) {

        }
    }

    //Попытка подключения к серверу после нажатия кнопки
    public void MouseClick(MouseEvent mouseEvent) throws IOException {
        ClientActivity.connection.setServerIP(ipField.getText());
        ClientActivity.connection.ipHolder.setProperty("ip", ipField.getText());
        ClientActivity.connection.ipHolder.commit();
        tryingToConnectIndicator.setVisible(true);
        CompletableFuture.supplyAsync(this::tryConnectAsync).thenAccept(this::afterTryConnect);

    }
}
