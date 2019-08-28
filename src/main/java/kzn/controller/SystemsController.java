package kzn.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import kzn.model.ClientActivity;
import kzn.model.Connection;
import kzn.properties.MyPropertiesHolder;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

public class SystemsController extends Controller implements Initializable {

    //FXML elements start
    @FXML
    private ChoiceBox systemsListBox;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField langField;
    @FXML
    private ProgressIndicator tryingToLoginIndicator;

    public MyPropertiesHolder userdataHolder;
    //FXML elements end


    //ALERTS start
    public static final Alert loginErrorAlert = new Alert(Alert.AlertType.ERROR,
            "Ошибка входа\nНеправильный логин или пароль",
            ButtonType.OK);
    public static final Alert systemNotChosenAlert = new Alert(Alert.AlertType.WARNING,
            "Выберите систему",
            ButtonType.OK);
    public static final Alert incorrectLangCodeAlert = new Alert(Alert.AlertType.ERROR,
            "Неправильный код языка",
            ButtonType.OK);
    //ALERTS end


    //Задать имя пользователя и язык, сохраненные во время последнего входа
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> systemNames = FXCollections.observableArrayList();
        for (String system : ClientActivity.getSystems().keySet()) {
            systemNames.add(system);
        }
        systemsListBox.setItems(systemNames);

        try {
            userdataHolder = new MyPropertiesHolder("userdata.properties", MyPropertiesHolder.MODE_UPDATE);

            usernameField.setText(userdataHolder.getProperty("username"));
            langField.setText(userdataHolder.getProperty("language"));
        } catch (Exception ex) {
            try {
                userdataHolder = new MyPropertiesHolder("userdata.properties", MyPropertiesHolder.MODE_CREATE);
                userdataHolder.setProperty("username", "");
                userdataHolder.setProperty("language", "R");
                userdataHolder.commit();
            } catch (Exception ex1) { }
        }
    }

    //Получение данных из полей и отправка запроса на вход в ситему
    public LinkedHashMap<String, LinkedList<String>> tryToLoginAsync() {
        String system = ClientActivity.getSystems().get((String)systemsListBox.getValue()).get(0);
        String username = usernameField.getText();
        String password = passwordField.getText();
        String language = langField.getText();
        LinkedHashMap<String, LinkedList<String>> sapData = ClientActivity.connection.tryLogin(system, username, password, language);
        if (ClientActivity.connection.getStatus() == Connection.ConnectionStatus.SUCCESS) return sapData;
        else return null;
    }

    //Обработка результата попытки входа в систему
    public void afterLogin(LinkedHashMap<String, LinkedList<String>> sapDataList) {

        if (ClientActivity.connection.getStatus() == Connection.ConnectionStatus.SUCCESS) {

            Platform.runLater(() -> {
                tryingToLoginIndicator.setVisible(false);
            });

            //Когда язык задан неправильно, сервер не отправляет основную таблицу (а количество остальных полей - 8)
            if (sapDataList.size() <= 8) {
                Platform.runLater(() -> {incorrectLangCodeAlert.show();});
                return;
            }

            ClientActivity.readSyst(sapDataList);

            System.out.println(ClientActivity.getClientID());

            for (String module : ClientActivity.modulesList) {
                System.out.println(module);
            }

            //this.changeScene("src/main/java/kzn/view/Systems.fxml", "Подключение к системе");
        }
        else {
            Platform.runLater(() -> {
                tryingToLoginIndicator.setVisible(false);
                loginErrorAlert.showAndWait();
            });
        }
    }

    public void loginButtonClicked(ActionEvent actionEvent) throws IOException {
        usernameField.setText(usernameField.getText().toUpperCase());
        langField.setText(langField.getText().toUpperCase());

        //Сохранение данных для восстановления при следующем запуске
        userdataHolder.setProperty("username", usernameField.getText());
        userdataHolder.setProperty("language", langField.getText());
        userdataHolder.commit();

        if (systemsListBox.getValue() == null) {
            systemNotChosenAlert.show();
            return;
        }

        tryingToLoginIndicator.setVisible(true);
        //Запустить выполнение afterLogin(tryLoginAsync()) в отдельном потоке
        CompletableFuture.supplyAsync(this::tryToLoginAsync).thenAccept(this::afterLogin);
    }
}
