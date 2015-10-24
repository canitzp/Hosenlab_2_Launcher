package de.canitzp.hosenlauncher;

import de.canitzp.hosenlauncher.controller.DescriptionController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Description extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Description.fxml"));
            AnchorPane pane = loader.load();
            primaryStage.setMaxWidth(512D);
            primaryStage.setMaxHeight(450D);
            primaryStage.setTitle("Modpack Description");
            DescriptionController descriptionController = loader.getController();
            Variables.descriptionController = descriptionController;
            descriptionController.startup();
            stage = primaryStage;
            primaryStage.setScene(new Scene(pane));
            primaryStage.setResizable(false);
            primaryStage.initOwner(Main.primaryStage);
            primaryStage.show();
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

