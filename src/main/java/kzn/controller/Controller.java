package kzn.controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public abstract class Controller {
    //Текущая Stage
    protected Stage stage;

    private LinkedHashMap<String, String> menuList = new LinkedHashMap<>();

    public void setPrevStage(Stage stage) {setPrevStageScenes(stage, new LinkedHashMap<>());}

    //Получение Stage и Scenes от предыдущего контроллера
    public void setPrevStageScenes(Stage stage, LinkedHashMap<String, String> prevScenes) {
        this.stage = stage;
        this.menuList = prevScenes;
    }

    //Изменение сцены на текущей Stage
    public void changeScene(String scenePath, String title) {
        Platform.runLater(() -> {
                    try {
                        URL url1 = (new File(scenePath)).toURI().toURL();
                        FXMLLoader myLoader = new FXMLLoader(url1);
                        Pane pane = (Pane) myLoader.load();

                        HBox menuBox = new HBox();
                        AnchorPane.setLeftAnchor(menuBox, 0D);
                        AnchorPane.setRightAnchor(menuBox, 0D);
                        AnchorPane.setBottomAnchor(menuBox, 0D);
                        menuBox.setStyle("-fx-background-color: lightgrey;");
                        menuList.put(title, scenePath);
                        for (String menuItem : menuList.keySet()) {
                            if (!menuList.keySet().toArray()[0].equals(menuItem)) {
                                Label arrowLabel = new Label(">");
                                HBox.setMargin(arrowLabel, new Insets(3, 0, 0, 0));
                                menuBox.getChildren().add(arrowLabel);
                            }
                            Button menuItemButton = new Button(menuItem);
                            menuItemButton.setText(menuItem);
                            menuItemButton.setOnAction((ae) -> {
                                LinkedHashMap<String, String> newMenuList = new LinkedHashMap<>();
                                for (String itemKey : menuList.keySet()) {
                                    if (!itemKey.equals(menuItemButton.getText())) newMenuList.put(itemKey, menuList.get(itemKey));
                                    else {
                                        newMenuList.put(itemKey, menuList.get(itemKey));
                                        break;
                                    }
                                }
                                menuList = newMenuList;
                                this.changeScene(menuList.get(menuItemButton.getText()), menuItemButton.getText());
                            });
                            menuBox.getChildren().add(menuItemButton);
                        }
                        pane.getChildren().add(menuBox);


                        //Создание контроллера сцены
                        final Controller controller = (Controller) myLoader.getController();

                        //Передача контроллеру текущей Stage
                        controller.setPrevStageScenes(stage, this.menuList);

                        stage.setTitle(title);

                        stage.setScene(new Scene(pane));
                        stage.sizeToScene();
                        stage.setMinHeight(pane.getPrefHeight());
                        stage.setMinWidth(pane.getPrefWidth());
                    } catch (Exception ex) {
                        System.out.println("Error while changing Scene to" + scenePath);
                        ex.printStackTrace();
                    }
                }
        );
    }
}
