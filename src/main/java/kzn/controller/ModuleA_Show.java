package kzn.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import kzn.model.DataSet;

public class ModuleA_Show extends Controller {
    @FXML
    private TableView mainTableView;
    @FXML
    private CheckBox systemNamesBox;

    DataSet dataSet = new DataSet();

    public void setData(DataSet dataSet) {
        this.dataSet = dataSet;

    }
}
