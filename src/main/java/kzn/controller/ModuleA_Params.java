package kzn.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import kzn.model.ClientActivity;
import kzn.model.DataSet;
import kzn.model.DataSetStore;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.LinkedList;

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


    public void onEnter(ActionEvent actionEvent) throws IOException {
        DataSet dataSet = ClientActivity.dataSetStore.getDataSet( tableField.getText(),
                quantityField.getText(),
                whereField.getText(),
                orderField.getText(),
                groupField.getText(),
                namesField.getText(),
                langField.getText() );

        //TODO Передача таблицы в ModuleA_Show
        LinkedHashMap<String, Object> dataForNextController = new LinkedHashMap<>();
        dataForNextController.put("dataset", dataSet);
        this.changeScene("src/main/java/kzn/view/ModuleA_Show.fxml", "Table view", dataForNextController);
    }



}
