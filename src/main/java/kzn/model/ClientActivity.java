package kzn.model;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import kzn.controller.StartController;


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
