package kzn.model;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import kzn.controller.Connect;

public class ClientActivity extends Application {
    private static String serverIP = "192.168.0.13:8080";

    @SuppressWarnings("WeakerAccess")
    public static Connection connection;

    public static void main( String[] args )
    {
        connection = new Connection(serverIP);
        if (connection.tryConnect()) {
            System.out.println("Yes");
            ArrayList<Mapa> systems = connection.getConnections();
            for (Mapa sys : systems) {
                System.out.println(sys.getName() + "  " + sys.getValues());
            }
        }
        else {
            System.out.println("No");
        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
