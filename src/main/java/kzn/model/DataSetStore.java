package kzn.model;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class DataSetStore {
    // список запросов к серверу с данными
    private static LinkedHashMap<String, DataSet> dataSetList = new LinkedHashMap<>();
    // id запроса к серверу с данными
    //private static String dataSetID;
    // requestUrl get-запроса в SAP через сервер
    static String requestUrl;

    //Получение
    public static DataSet getDataSet(String table,
                                     String fieldsQuan, String language, String where, String order,
                                     String group, String fieldNames) {
        if (table.equals("")) table = "~~~";
        if (fieldsQuan.equals("")) fieldsQuan = "~~~";
        if (language.equals("")) language = "~~~";
        if (where.equals("")) where = "~~~";
        if (order.equals("")) order = "~~~";
        if (group.equals("")) group = "~~~";
        if (fieldNames.equals("")) fieldNames = "~~~";
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
        return dataSetList.get(dataSetID);
    }


//    public void showTable(String table,
//                              String fieldsQuan, String language, String where, String order,
//                              String group, String fieldNames) {
//        DataSet dataSetToShow = getDataSet(table, fieldsQuan, language, where, order, group, fieldNames);
//        callTableLayout(dataSetToShow);
//    }
//
//
//    //method showTable() in Android
//    private void callTableLayout(DataSet dataSet) {
//
//    }

    // добавление данных в список запросов
    public static void putDataSet(LinkedHashMap<String, LinkedList<String>> map, String dataSetID) {
        DataSet dataSet = new DataSet();
        dataSet.setMap(map);
        dataSetList.put(dataSetID, dataSet);
    }

    public static String getDataSetID(String table,
                             String fieldsQuan, String language, String where, String order,
                             String group, String fieldNames) {
        return table + fieldsQuan + language + where + order + group + fieldNames;
    }
}
