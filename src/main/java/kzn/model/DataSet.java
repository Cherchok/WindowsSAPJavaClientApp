package kzn.model;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import kzn.controller.ModuleA_Show;

import javafx.util.Callback;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class DataSet {
    private LinkedHashMap<String, LinkedList<String>> map;

    private LinkedHashMap<String, Callback> typesMap;

    public LinkedHashMap<String, LinkedList<String>> getMap() {
        return map;
    }

    public void setMap(LinkedHashMap<String, LinkedList<String>> map) {
        this.map = map;

    }

    public DataSet() { }

    public DataSet(LinkedHashMap<String, LinkedList<String>> map) {
        setMap(map);
    }

    //TODO Классы для ячеек разного типа, присваивание столбцам нужных классов отображения

}
