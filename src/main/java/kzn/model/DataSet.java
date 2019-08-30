package kzn.model;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class DataSet {
    private LinkedHashMap<String, LinkedList<String>> map;

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

}
