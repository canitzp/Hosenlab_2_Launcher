package de.canitzp.hosenlauncher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Settings extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Settings.fxml"));
            AnchorPane pane = loader.load();
            primaryStage.setMaxWidth(350D);
            primaryStage.setMaxHeight(225D);
            primaryStage.setMinHeight(225D);
            primaryStage.setMinWidth(350D);
            primaryStage.setTitle("Hosenlauncher-Einstellungen");
            Variables.settingsController = loader.getController();
            Variables.settingsController.startup();
            primaryStage.setScene(new Scene(pane));
            primaryStage.initOwner(Main.primaryStage);
            primaryStage.setResizable(false);
            primaryStage.show();
            stage = primaryStage;
            primaryStage.setOnCloseRequest(t -> {
                System.gc();
                Variables.mainController.infoBtn.setDisable(false);
                Variables.mainController.settingsBtn.setDisable(false);
                Variables.mainController.startBtn.setDisable(false);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void close(){
        stage.close();
    }

}
