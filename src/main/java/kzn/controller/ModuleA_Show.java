package kzn.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import kzn.model.DataSet;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class ModuleA_Show extends Controller {
    @FXML
    private TableView mainTableView;
    @FXML
    private CheckBox systemNamesBox;

    private ObservableList<ObservableList> visibleData;

    DataSet dataSet = new DataSet();

    public ModuleA_Show() {
        System.out.println("SHOW");
        for (String key : dataFromPrevController.keySet())
            System.out.println(key);
        LinkedHashMap<String, LinkedList<String>> map;
        try {
            this.dataSet = (DataSet) dataFromPrevController.get("dataset");
            visibleData = FXCollections.observableArrayList();
            map = this.dataSet.getMap();
        } catch(NullPointerException ex) {
            return;
        }
        //Динамическое создание колонок
        for (int i = 0; i < map.keySet().size(); i++) {
            //We are using non property style for making dynamic table
            final int j = i;
            //TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
            TableColumn col = new TableColumn((String)map.keySet().toArray()[i + 1]);
            col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                }
            });

            mainTableView.getColumns().addAll(col);
            System.out.println("Column [" + i + "] ");
        }

    }

}
