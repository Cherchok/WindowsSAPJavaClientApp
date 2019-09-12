package kzn.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import kzn.model.ClientActivity;
import kzn.model.DataSetStore;

public class ModuleA_Params extends Controller {

    //FXML elements start
    @FXML
    private TextField tableField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField whereField;
    @FXML
    private TextField orderField;
    @FXML
    private TextField groupField;
    @FXML
    private TextField namesField;
    @FXML
    private TextField langField;
    //FXML elements end


    public void onEnter(ActionEvent actionEvent) {
        ClientActivity.dataSetStore.showTable( tableField.getText(),
                quantityField.getText(),
                whereField.getText(),
                orderField.getText(),
                groupField.getText(),
                namesField.getText(),
                langField.getText() );
    }



}
