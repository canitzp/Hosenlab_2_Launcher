package de.canitzp.hosenlauncher;

import de.canitzp.hosenlauncher.controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Login extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("fxml/Login.fxml"));
        AnchorPane pane = loader.load();
        primaryStage.setTitle("Hosenlauncher BETA");
        LoginController loginController = loader.getController();
        primaryStage.setScene(new Scene(pane));
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(t -> {
            System.gc();
            if(loginController.userFieled == null || loginController.passField == null){
                System.exit(0);
            }
        });
    }
}
