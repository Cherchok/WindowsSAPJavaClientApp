package kzn.model;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kzn.properties.MyPropertiesHolder;

//Основной класс
public class ClientActivity extends Application {
    //Объект, с помощью которого осуществляются вся работа с сетью
    @SuppressWarnings("WeakerAccess")
    public static Connection connection;

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

    //Отображение формы
    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = (new File("src/main/java/kzn/view/Connect.fxml")).toURI().toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Подключение к серверу");
        primaryStage.setScene(new Scene(root, 350, 275));
        primaryStage.setResizable(false);

        primaryStage.show();
    }
}
