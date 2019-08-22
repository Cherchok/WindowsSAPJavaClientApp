package kzn.controller;

import javafx.scene.control.Alert;
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
import javafx.stage.Stage;


import kzn.model.ClientActivity;

public class Connect {
    public TextField ipField;


    public void MouseClick(MouseEvent mouseEvent) {
        ClientActivity.connection.setServerIP(ipField.getText());
        if (!ClientActivity.connection.tryConnect()) {
            //(new Alert(Alert.AlertType.ERROR, "Хуйня-с, введите IP", ButtonType.OK)).showAndWait();
        }
    }
}
