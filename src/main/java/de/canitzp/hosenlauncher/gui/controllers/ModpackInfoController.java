package de.canitzp.hosenlauncher.gui.controllers;

import de.canitzp.hosenlauncher.*;
import de.canitzp.hosenlauncher.gui.Controllers;
import de.canitzp.hosenlauncher.gui.GuiController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;
import javafx.stage.WindowEvent;

import java.io.File;

public class ModpackInfoController extends GuiController {
    @FXML
    private Label modpackLabel;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private Button downloadServerButton;
    @FXML
    private Button exitButton;

    private MainController mainController;
    private Modpacks modpack;

    @Override
    protected void initialize() {
        mainController = getGui().get(Controllers.MAIN);
        mainController.disableButtons();
        getStage().setOnCloseRequest(event -> mainController.enableButtons());

        modpack = mainController.getSelectedModpack();
        modpackLabel.setText(modpack.getDisplayName());
        downloadServerButton.setDisable(modpack.getServerLink() == null);
        descriptionTextArea.setText(FileManager.getDescription(modpack));
    }

    @FXML
    private void onDownloadServerButtonAction(ActionEvent actionEvent) {
        DirectoryChooser chooser = new DirectoryChooser();
        File path = chooser.showDialog(getStage());
        if (path != null && path.getParentFile() != null && modpack != null && modpack.getServerLink() != null) {
            // TODO: Rewrite server download
            Thread thread = new Thread(() -> {
                FileManager.download(new File(path.getAbsolutePath() + File.separator + "server.zip"), modpack.getServerLink());
                FileManager.unZipIt(path.getAbsolutePath() + File.separator + "server.zip", path.getAbsolutePath() + File.separator);
                new File(path.getAbsolutePath() + File.separator + "server.zip").delete();
            });

            mainController.enableButtons();
            mainController.addToLog("Starte Serverdownload in das Verzeichnis " + path);
            thread.start();
            unload();
        }
    }

    @FXML
    private void onExitButtonAction(ActionEvent actionEvent) {
        mainController.enableButtons();
        unload();
    }
}
