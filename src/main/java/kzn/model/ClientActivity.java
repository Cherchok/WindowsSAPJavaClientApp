package kzn.model;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import kzn.controller.StartController;
import kzn.model.httpsconnection.Connection;


//Основной класс
public class ClientActivity extends Application {
    //Объект, с помощью которого осуществляются вся работа с сетью
    @SuppressWarnings("WeakerAccess")
    public static Connection connection;

    //Список систем
    private static LinkedHashMap<String, LinkedList<String>> systems;
    //Адрес выбранной системы
    private static String selectedSystem;
    // клиентский ID полученный от сервера
    private static String clientID;
    // имя пользователя
    private static String username;
    // пароль
    private static String password;
    // язык выходных данных
    private static String language;
    // список модулей
    private static LinkedList<String> modulesList = new LinkedList<>();
    // модуль с которым производится работа
    private static String selectedModule;
    // id модуля с которым производится работа
    private static String selectedModuleID;
    // список id модулей
    private static LinkedList<String> moduleIDlist = new LinkedList<>();

    public static DataSetStore dataSetStore;



    public static void main(String[] args) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        connection = new Connection();
        dataSetStore = new DataSetStore();
        launch(args);
    }



    //GETTERS & SETTERS start
    //--------------------------------------------------
    public static LinkedHashMap<String, LinkedList<String>> getSystems() {
        return systems;
    }

    public static void setSystems(LinkedHashMap<String, LinkedList<String>> systems) { ClientActivity.systems = systems; }

    public static String getSelectedSystem() { return selectedSystem; }

    public static void setSelectedSystem(String selectedSystem) { ClientActivity.selectedSystem = selectedSystem; }

    public static String getClientID() {
        return clientID;
    }

    public static void setClientID(String clientID) {
        ClientActivity.clientID = clientID;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        ClientActivity.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        ClientActivity.password = password;
    }

    public static String getLanguage() {
        return language;
    }

    public static void setLanguage(String language) {
        ClientActivity.language = language;
    }

    public static LinkedList<String> getModulesList() { return modulesList; }

    public static void setModulesList(LinkedList<String> modulesList) { ClientActivity.modulesList = modulesList; }

    public static void setSelectedModule(String selectedModule) { ClientActivity.selectedModule = selectedModule; }

    // получем id выбранного модуля
    public static String getSelectedModuleID() {
        String selectedApp = "";
        int index = 999;
        for (int i = 0; i < ClientActivity.modulesList.size(); i++) {
            if (ClientActivity.modulesList.get(i).contains(ClientActivity.selectedModule)
                    && !ClientActivity.selectedModule.equals("")) {
                index = i;
            }
        }
        if (index != 999) {
            selectedApp = ClientActivity.moduleIDlist.get(index).trim();
        }
        return selectedApp;
    }

    //--------------------------------------------------
    //GETTERS & SETTERS end

    //считываем данные из syst
    public static void readSyst(LinkedHashMap<String, LinkedList<String>> sapDataList) {
        //считываем список доступных приложений и номер клиента, передаваемый от сервера
        for (Map.Entry<String, LinkedList<String>> entry : sapDataList.entrySet()) {
            String key = entry.getKey();
            LinkedList<String> values = entry.getValue();
            if (key.equals("REPI2")) {
                ClientActivity.modulesList = values;
            } else if (key.equals("clientNumber")) {
                ClientActivity.clientID = values.get(0);
            }
            if (key.equals("CPROG")) {
                ClientActivity.moduleIDlist = values;
            }
        }

    }

    //Отображение формы
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            //Загрузка Start.fxml
            URL url = (new File("src/main/java/kzn/view/Start.fxml")).toURI().toURL();
            FXMLLoader myLoader = new FXMLLoader(url);
            Pane pane = (Pane) myLoader.load();

            //Создание контроллера сцены
            final StartController controller = (StartController) myLoader.getController();



            //Передача контроллеру текущей Stage
            controller.setPrevStage(primaryStage);

            //Задание Scene для Stage
            primaryStage.setTitle("SAP Windows App");
            primaryStage.setScene(new Scene(pane));
            //primaryStage.setResizable(false);
            primaryStage.setFullScreen(false);
            primaryStage.sizeToScene();

            //При открытии окна вызвать метод onShow()
            primaryStage.setOnShown(event -> controller.onShow());

            primaryStage.show();

        } catch (Exception ex) {
            System.out.println("Error while opening Start.fxml");
            ex.printStackTrace();
        }
    }

}
