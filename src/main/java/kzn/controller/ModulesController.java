package kzn.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import kzn.model.ClientActivity;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class ModulesController extends Controller implements Initializable {
    public ChoiceBox modulesListBox;
    public TextArea descriptionArea;
    public Label modulesLabel;

    public String ltrimZeros(String strToTrim) {
        int zerosLen = 0;
        while (strToTrim.charAt(zerosLen) == '0') zerosLen++;
        return strToTrim.substring(zerosLen);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
}
