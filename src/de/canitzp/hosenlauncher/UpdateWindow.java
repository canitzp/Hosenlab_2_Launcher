package de.canitzp.hosenlauncher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class UpdateWindow extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("fxml/Update.fxml"));
            AnchorPane pane = loader.load();
            primaryStage.setMaxWidth(300D);
            primaryStage.setMaxHeight(300D);
            primaryStage.setMinHeight(300D);
            primaryStage.setMinWidth(300D);
            primaryStage.setTitle("Changelog");
            Variables.updateController = loader.getController();
            Variables.updateController.startup();
            primaryStage.initOwner(Main.primaryStage);
            primaryStage.setResizable(false);
            primaryStage.setScene(new Scene(pane));
            primaryStage.show();
            primaryStage.setOnCloseRequest(event -> {
                Variables.mainController.infoBtn.setDisable(false);
                Variables.mainController.settingsBtn.setDisable(false);
                Variables.mainController.startBtn.setDisable(false);
            });
            stage = primaryStage;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setChangelog(Modpacks modpacks){
        Variables.updateController.updateField.setText(FileManager.getChangelog(modpacks));
    }

    public static void close(){
        stage.close();
    }

}
