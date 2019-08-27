package kzn.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import kzn.model.ClientActivity;
import kzn.model.Mapa;

import java.net.URL;
import java.util.ResourceBundle;

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
    private ProgressIndicator tryingToEnterIndicator;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> systemNames = FXCollections.observableArrayList();
        for (Mapa system : ClientActivity.getSystems()) {
            systemNames.add(system.getName());
        }
        systemsListBox.setItems(systemNames);
    }

    public void loginButtonClicked(ActionEvent actionEvent) {

    }
}
