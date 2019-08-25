package kzn.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kzn.model.ClientActivity;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartController implements Initializable {

    private Stage prevStage;

    public void setPrevStage(Stage stage){
        this.prevStage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        boolean connectionSuccess = ClientActivity.connection.tryConnect();
        if (!connectionSuccess) {
            try {
//                System.out.println("ConnectController.fxml opening");
//                URL url1 = (new File("src/main/java/kzn/view/ConnectController.fxml")).toURI().toURL();
//                //FXMLLoader fxmlLoader = new FXMLLoader(url1);
//                //Parent root1 = (Parent) fxmlLoader.load();
//                Parent root1 = FXMLLoader.load(url1);
//                Stage stage = new Stage();
//                stage.initModality(Modality.NONE);
//                //stage.initStyle(StageStyle.UNDECORATED);
//                stage.setTitle("ABC");
//                stage.setScene(new Scene(root1));
//                ClientActivity.stage.close();
//                stage.show();
                Stage stage = new Stage();
                URL url1 = (new File("src/main/java/kzn/view/Connect.fxml")).toURI().toURL();
                FXMLLoader myLoader = new FXMLLoader(url1);
                Pane pane = (Pane)myLoader.load();
                ConnectController controller = (ConnectController)myLoader.getController();
                stage.setTitle("Подключение к серверу");
                stage.setScene(new Scene(pane, 350, 275));
                stage.setResizable(false);

                prevStage.close();

                stage.show();

            } catch (Exception ex) {
                System.out.println("Error while opening form Connect.fxml");
                ex.printStackTrace();

            }
        }
    }
}
