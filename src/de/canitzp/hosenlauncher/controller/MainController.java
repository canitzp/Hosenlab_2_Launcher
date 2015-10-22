package de.canitzp.hosenlauncher.controller;

import de.canitzp.hosenlauncher.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;

@SuppressWarnings("unchecked")
public class MainController {

    public Label userLabel;
    private Main main;
    @FXML public ImageView headImage;
    @FXML public ProgressBar progress = new ProgressBar();
    @FXML public Button infoBtn;
    @FXML public Button startBtn;
    @FXML public Button settingsBtn;
    @FXML public ComboBox dropDown;
    @FXML public TextArea logField;

    public void setMain(Main main){
        this.main = main;
    }

    public void usernameKeyHandler(Event event) {
    }

    public void passwordKeyHandler(Event event) {

    }

    public void startGame(ActionEvent actionEvent) {
        Variables.startThread = new Thread() {
            public void run() {
                ButtonAction.startGame();
            }
        };
        Variables.startThread.start();
    }

    public void addToLog(String string){
       logField.appendText(string + "\n");
    }

    public void clearLog(){
        this.logField.setText("Hosenlauncher - Welcome to the new Way!\n");
    }

    public void addToComboBox(String string){
        this.dropDown.getItems().add(string);
    }

    public void startup() {
        changeHead();
        userLabel.setText("Angemeldet als: " + Variables.loginMap.get("displayName"));
        this.dropDown.setValue(Modpacks.HOSENLAB2.getDisplayName());
        this.logField.setEditable(false);
        this.settingsBtn.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("pictures/settings.png"))));
        this.infoBtn.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("pictures/info.png"))));
        addToComboBox(Modpacks.HOSENLAB2.getDisplayName());
        addToComboBox(Modpacks.TECHNICUNIVERSE.getDisplayName());
    }

    public void settingsBtnHandler(ActionEvent actionEvent) {
        ButtonAction.startSettings();
    }

    public void infoHandler(ActionEvent actionEvent) {
        try {
            Variables.mainController.infoBtn.setDisable(true);
            Variables.mainController.settingsBtn.setDisable(true);
            Variables.mainController.startBtn.setDisable(true);
            new Description().start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeHead(){
        FileManager.download(new File(Variables.launcherPath.getAbsolutePath() + File.separator + "head.png"), "http://www.minotar.net/helm/" + Variables.loginMap.get("displayName") + "/24");
        headImage.setImage(new Image(new File(Variables.launcherPath.getAbsolutePath() + File.separator + "head.png").toURI().toString()));
    }
}
