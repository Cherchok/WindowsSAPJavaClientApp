package kzn.model;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class DataSetStore {
    // список запросов к серверу с данными
    static LinkedHashMap<String, DataSet> dataSetList = new LinkedHashMap<>();
    // id запроса к серверу с данными
    //private static String dataSetID;
    // requestUrl get-запроса в SAP через сервер
    static String requestUrl;


    private void showTable(String table,
                              String fieldsQuan, String language, String where, String order,
                              String group, String fieldNames) {
        String dataSetID = getDataSetID(table, fieldsQuan, language, where, order, group, fieldNames);
        if (!dataSetList.containsKey(dataSetID)) {
            LinkedHashMap<String, LinkedList<String>> newDataSet =
                    ClientActivity.connection.getDataSet(ClientActivity.getSelectedSystem(),
                    ClientActivity.getUsername(),
                    ClientActivity.getPassword(),
                    ClientActivity.getClientID(),
                    table, fieldsQuan, language, where, order, group, fieldNames);
            putDataSet(newDataSet, dataSetID);
        }
    }


    //method showTable() in Android
    public void callTableLayout(String dataSetID) {

    }

    // добавление данных в список запросов
    public static void putDataSet(LinkedHashMap<String, LinkedList<String>> map, String dataSetID) {
        DataSet dataSet = new DataSet();
        dataSet.setMap(map);
        dataSetList.put(dataSetID, dataSet);
    }

    public String getDataSetID(String table,
                             String fieldsQuan, String language, String where, String order,
                             String group, String fieldNames) {
        return table + fieldsQuan + language + where + order + group + fieldNames;
    }
}
