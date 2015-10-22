package de.canitzp.hosenlauncher.controller;

import de.canitzp.hosenlauncher.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;

import java.io.File;

public class DescriptionController {

    public Label modpackLabel;
    public TextArea descField;
    public Button serverBtn;
    public Button backLauncher;

    public void startup(){
        descField.setEditable(false);
        modpackLabel.setText((String) Variables.mainController.dropDown.getValue());
        serverBtn.setDisable(FileManager.getModpackFromString((String) Variables.mainController.dropDown.getValue()).getServerLink() == null);
        descField.setText(FileManager.getDescription(FileManager.getModpackFromString((String) Variables.mainController.dropDown.getValue())));
    }

    public void setServer(boolean server){
        serverBtn.setDisable(server);
    }

    public void downlaodServerHandler(ActionEvent actionEvent) {
            DirectoryChooser chooser = new DirectoryChooser();
            Modpacks modpacks = FileManager.getModpackFromString((String) Variables.mainController.dropDown.getValue());
            File path = chooser.showDialog(null);
            if(path == null || path.getParentFile() == null) return;
            if (modpacks != null && modpacks.getServerLink() != null){
                Thread thread = new Thread(() -> {
                    FileManager.download(new File(path.getAbsolutePath() + File.separator + "server.zip"), modpacks.getServerLink());
                    FileManager.unZipIt(path.getAbsolutePath() + File.separator + "server.zip", path.getAbsolutePath() + File.separator);
                    new File(path.getAbsolutePath() + File.separator + "server.zip").delete();
                });
                Variables.mainController.infoBtn.setDisable(false);
                Variables.mainController.settingsBtn.setDisable(false);
                Variables.mainController.startBtn.setDisable(false);
                Description.close();
                Variables.mainController.addToLog("Serverdownload in das Verzeichnis \"" + path + File.separator + "\" gestartet!");
                thread.start();
            }
    }

    public void backToLauncherHandler(ActionEvent actionEvent) {
        Variables.mainController.infoBtn.setDisable(false);
        Variables.mainController.settingsBtn.setDisable(false);
        Variables.mainController.startBtn.setDisable(false);
        Platform.runLater(Description::close);
    }
}
