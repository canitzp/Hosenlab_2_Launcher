package de.canitzp.hosenlauncher;

import de.canitzp.hosenlauncher.controller.LoginController;
import de.canitzp.hosenlauncher.controller.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Main extends Application {

    public static Stage primaryStage;
    public static boolean wait;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.primaryStage = primaryStage;
        if(Variables.loginMap == null){
            login();
            Variables.save.delete();
        } else createMainWindow();
    }

    public static void main(String[] args){
        Variables.startup();
        startup();
        launch(args);
    }

    private static void startup() {
        File path = new File("");
        if(new File(path.getAbsolutePath() + "/Updater.jar").exists()){
            new File(path.getAbsolutePath() + "/Updater.jar").delete();
        }
        try {
            if (!Variables.launcherSettings.exists()) {
                FileManager.createFile("/settings.txt", Update.getLauncherVersion());
            } else {
                Update.checkLauncherUpdate();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void createMainWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("fxml/Main.fxml"));
            AnchorPane pane = loader.load();
            primaryStage.setTitle("Hosenlauncher BETA");
            MainController mainController = loader.getController();
            Variables.mainController = mainController;
            primaryStage.setScene(new Scene(pane));
            primaryStage.setResizable(false);
            primaryStage.show();
            mainController.addToLog("Hosenlauncher BETA - Der neue Weg zu den Modpacks!");
            mainController.startup();
            primaryStage.setOnCloseRequest(t -> {
                System.gc();
                Variables.save.saveVariables();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void login(){
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("fxml/Login.fxml"));
            AnchorPane pane = loader.load();
            primaryStage.setTitle("Hosenlauncher BETA");
            LoginController loginController = loader.getController();
            primaryStage.setScene(new Scene(pane));
            primaryStage.setResizable(false);
            primaryStage.show();
            primaryStage.setOnCloseRequest(t -> {
                System.gc();
                if (loginController.userFieled == null || loginController.passField == null) {
                    System.exit(0);
                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void close(){
        Platform.runLater(() -> {
            try {
                Thread.sleep(5000);
                primaryStage.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

}
