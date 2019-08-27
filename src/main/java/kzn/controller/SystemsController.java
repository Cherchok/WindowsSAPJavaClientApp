package kzn.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import kzn.model.ClientActivity;
import kzn.model.Connection;
import kzn.model.Mapa;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

public class SystemsController extends Controller implements Initializable {
    //FXML elements
    @FXML
    private ChoiceBox systemsListBox;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField langField;
    @FXML
    private ProgressIndicator tryingToLoginIndicator;


    public static final Alert loginErrorAlert = new Alert(Alert.AlertType.ERROR, "Ошибка входа\nНеправильный логин или пароль", ButtonType.OK);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> systemNames = FXCollections.observableArrayList();
        for (String system : ClientActivity.getSystems().keySet()) {
            systemNames.add(system);
        }
        systemsListBox.setItems(systemNames);
    }

    public LinkedHashMap<String, LinkedList<String>> tryToLoginAsync() {
        String system = (String)systemsListBox.getValue();
        system = ClientActivity.getSystems().get((String)systemsListBox.getValue()).get(0);

        String username = loginField.getText();
        String password = passwordField.getText();
        String language = langField.getText();
        LinkedHashMap<String, LinkedList<String>> sapData = ClientActivity.connection.tryLogin(system, username, password, language);
        if (ClientActivity.connection.getStatus() == Connection.ConnectionStatus.SUCCESS) return sapData;
        else return null;
    }

    public void afterLogin(LinkedHashMap<String, LinkedList<String>> sapDataList) {
        if (ClientActivity.connection.getStatus() == Connection.ConnectionStatus.SUCCESS) {
            Platform.runLater(() -> {
                tryingToLoginIndicator.setVisible(false);
            });
            ClientActivity.readSyst(sapDataList);

            System.out.println(ClientActivity.getClientID());

            for (String module : ClientActivity.modulesList) {
                System.out.println(module);
            }

            //this.changeScene("src/main/java/kzn/view/Systems.fxml", "Подключение к системе");
        }
        else {
            Platform.runLater(() -> {
                tryingToLoginIndicator.setVisible(false);
                loginErrorAlert.showAndWait();
            });
        }
    }

    public void loginButtonClicked(ActionEvent actionEvent) {
        tryingToLoginIndicator.setVisible(true);
        CompletableFuture.supplyAsync(this::tryToLoginAsync).thenAccept(this::afterLogin);
    }
}
