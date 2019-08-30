package kzn.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import kzn.model.ClientActivity;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class ModulesController extends Controller implements Initializable {

    //FXML elements
    @FXML
    private ChoiceBox modulesListBox;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private Label modulesLabel;
    @FXML
    private Button okButton;

    public static String ltrimZeros(String strToTrim) {
        int zerosLen = 0;
        while (strToTrim.charAt(zerosLen) == '0') zerosLen++;
        return strToTrim.substring(zerosLen);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Инициализация списка модулей
        ObservableList<String> modules = FXCollections.observableArrayList();
        boolean isLabelSet = false;
        LinkedList<String> modulesList = ClientActivity.getModulesList();
        for (String module : modulesList) {
            if (module.replaceAll(" ", "").equals("")) {
                continue;
            }
            if (!isLabelSet) {
                if (modulesList.indexOf(module) == 1) {
                    modulesLabel.setText(module.trim());
                    isLabelSet = true;
                    continue;
                }
            }
            modules.add(ltrimZeros(module));
        }
        modulesListBox.setItems(modules);
    }

    public void okButtonPressed(ActionEvent actionEvent) {
        if (modulesListBox.getValue() == null) {
            return;
        }

        //Передача в главный класс имени выбранного модуля
        ClientActivity.setSelectedModule( (String)modulesListBox.getValue() );
        String selectedAppID = ClientActivity.getSelectedModuleID();

        if (selectedAppID != null) {
            switch (selectedAppID) {
                case "A":
                    this.changeScene("src/main/java/kzn/view/ModuleA_Params.fxml", "Table parameters");
                    break;
                case "Z":
                    this.changeScene("src/main/java/kzn/view/ModuleZ_QRscan.fxml", "QR scanning");
                    break;
                default:
                    break;
            }
        }
    }

    //Действие при выборе модуля
    public void onModuleSelected(ActionEvent actionEvent) {
        okButton.setDisable(false);
        descriptionArea.setText("No description");
    }
}
