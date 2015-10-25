package de.canitzp.hosenlauncher.gui.controllers;

import de.canitzp.hosenlauncher.*;
import de.canitzp.hosenlauncher.gui.Controllers;
import de.canitzp.hosenlauncher.gui.GuiController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import lombok.Getter;

public class UpdateController extends GuiController {
    @FXML
    private TextArea changelogTextArea;
    @FXML
    private Button yesButton;
    @FXML
    private Button noButton;

    private MainController mainController;
    @Getter
    private Modpacks modpack;

    @Override
    protected void initialize() {
        mainController = getGui().get(Controllers.MAIN);
        mainController.disableButtons();
    }

    @FXML
    private void onYesButtonAction(ActionEvent actionEvent) {
        mainController.enableButtons();
        Thread updateThread = new Thread("Update thread") {
            @Override
            public void run() {
                Update.update(modpack);
            }
        };
        updateThread.start();
        unload();
    }

    @FXML
    private void onNoButtonAction(ActionEvent event) {
        mainController.enableButtons();
        ButtonAction.launchGame(modpack);
        unload();
    }

    public void initModpack(Modpacks modpack) {
        this.modpack = modpack;
        changelogTextArea.setText(FileManager.getChangelog(modpack));
    }
}
