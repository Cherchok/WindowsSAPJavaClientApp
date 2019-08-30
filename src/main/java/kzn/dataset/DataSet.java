package kzn.dataset;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class DataSet {
    protected LinkedHashMap<String, LinkedList<String>> map;

    protected View view;

    protected DataController controller;

    public DataSet() {
        view = new View();
        controller = new DataController();
    }

    public DataSet(LinkedHashMap<String, LinkedList<String>> map) {
        this.map = map;
        view = new View();
        controller = new DataController();
    }
}
