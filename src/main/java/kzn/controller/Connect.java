package kzn.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import javafx.scene.layout.VBox;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;


import kzn.model.ClientActivity;
import kzn.model.Connection;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.*;

public class Connect implements Initializable {
    @FXML
    private TextField ipField;

    @Override public void initialize(URL url, ResourceBundle rb) {
        ipField.setText(ClientActivity.connection.ipHolder.getProperty("ip"));

    }

    public void display() {
        ipField.setText(ClientActivity.connection.ipHolder.getProperty("ip"));
    }

    public void MouseClick(MouseEvent mouseEvent) throws IOException {
        ClientActivity.connection.setServerIP(ipField.getText());
        ClientActivity.connection.ipHolder.setProperty("ip", ipField.getText());
        ClientActivity.connection.ipHolder.commit();
        if (!ClientActivity.connection.tryConnect() &&
                ClientActivity.connection.getStatus() == Connection.ConnectionStatus.IP_ERROR) {
            Alert errorAlert = new Alert(AlertType.ERROR, "Хуйня-с, введите IP", ButtonType.OK);
            errorAlert.show();
        }
    }
}
