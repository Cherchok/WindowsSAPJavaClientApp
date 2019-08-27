package kzn.model;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kzn.controller.StartController;


//Основной класс
public class ClientActivity extends Application {
    //Объект, с помощью которого осуществляются вся работа с сетью
    @SuppressWarnings("WeakerAccess")
    public static Connection connection;

    private static ArrayList<Mapa> systems;

    // клиентский ID полученный от сервера
    private static String clientID;
    // имя пользователя
    private static String username;
    // пароль
    private static String password;
    // язык выходных данных
    private static String language;

    public static void main( String[] args ) throws IOException
    {
        connection = new Connection();
//        if (connection.tryConnect()) {
//            System.out.println("Yes");
//            ArrayList<Mapa> systems = connection.getConnections();
//            for (Mapa sys : systems) {
//                System.out.println(sys.getName() + "  " + sys.getValues());
//            }
//        }
//        else {
//            System.out.println("No");
//        }
        launch(args);

    }


    //GETTERS & SETTERS start
    //--------------------------------------------------
    public static ArrayList<Mapa> getSystems() {
        return systems;
    }

    public static void setSystems(ArrayList<Mapa> systems) {
        ClientActivity.systems = systems;
    }

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
    //-----------------------------------------------------
    //GETTERS & SETTERS end

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
            primaryStage.setResizable(false);
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
