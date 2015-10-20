package de.canitzp.hosenlauncher.controller;

import de.canitzp.hosenlauncher.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class UpdateWindowController{
    public TextArea updateField;
    public Button updateTrueBtn;
    public Button updateFalseBtn;
    public static Modpacks modpacks;

    public void updatePackHandler(ActionEvent actionEvent) {
        UpdateWindow.close();
        Variables.mainController.infoBtn.setDisable(false);
        Variables.mainController.settingsBtn.setDisable(false);
        Variables.mainController.startBtn.setDisable(false);
        Variables.startThread = new Thread(){
            @Override
            public void run(){
                Update.update(modpacks);
            }
        }; Variables.startThread.start();
    }

    public void startGameHandler(ActionEvent actionEvent) {
        Variables.mainController.infoBtn.setDisable(false);
        Variables.mainController.settingsBtn.setDisable(false);
        Variables.mainController.startBtn.setDisable(false);
        UpdateWindow.close();
        ButtonAction.launchGame(modpacks);
    }

    public void startup(){
        updateField.setEditable(false);
    }

}
