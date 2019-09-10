package kzn.model;

import java.util.LinkedHashMap;

public class DataSetStore {
    // список запросов к серверу с данными
    static LinkedHashMap<String, Dataset> dataSetList = new LinkedHashMap<>();
    // id запроса к серверу с данными
    static String dataSetID;
    // requestUrl get-запроса в SAP через сервер
    static String requestUrl;
}
