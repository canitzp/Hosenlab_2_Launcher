package de.canitzp.hosenlauncher.gui.controllers;

import de.canitzp.hosenlauncher.*;
import de.canitzp.hosenlauncher.gui.Controllers;
import de.canitzp.hosenlauncher.gui.GuiController;
import de.canitzp.hosenlauncher.gui.exceptions.ControllerLoadException;
import de.canitzp.hosenlauncher.gui.exceptions.InvalidFxmlException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.File;

@SuppressWarnings("unchecked")
public class MainController extends GuiController {
    @FXML
    private ComboBox<Modpacks> modpackComboBox;
    @FXML
    private Button infoButton;
    @FXML
    private TextArea logTextArea;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private ImageView avatarImageView;
    @FXML
    private Label usernameLabel;
    @FXML
    private Button launchButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Button logoutButton;

    public void addToLog(String string) {
        // TODO
        logTextArea.appendText(string + "\n");
    }

    @Override
    protected void initialize() {
        modpackComboBox.getItems().setAll(Modpacks.values());
        modpackComboBox.setConverter(new StringConverter<Modpacks>() {
            @Override
            public String toString(Modpacks object) {
                return object.getDisplayName();
            }

            @Override
            public Modpacks fromString(String string) {
                return null;
            }
        });
        modpackComboBox.setValue(Modpacks.HOSENLAB2);

        changeHead();
        usernameLabel.setText(getLauncher().getYggdrasilData().get("displayName").toString());
    }

    @FXML
    private void onInfoButtonAction(ActionEvent event) {
        try {
            getGui().load(Controllers.MODPACK_INFO, "/views/modpack-info.fxml", "title.launcher");
        } catch (InvalidFxmlException | ControllerLoadException e) {
            getLauncher().getLogger().error("Failed to load controller", e);
        }
    }

    @FXML
    private void onLaunchButtonAction(ActionEvent event) {
        // TODO: Rewrite update stuff
        disableButtons();
        Thread updateThread = new Thread("Update thread") {
            @Override
            public void run() {
                new Update(getSelectedModpack());
            }
        };
        updateThread.run();
    }

    @FXML
    private void onSettingsButtonAction(ActionEvent event) {
        try {
            getGui().load(Controllers.SETTINGS, "/views/settings.fxml", "title.launcher");
        } catch (InvalidFxmlException | ControllerLoadException e) {
            getLauncher().getLogger().error("Failed to load controller", e);
        }
    }

    @FXML
    private void onLogoutButtonAction(ActionEvent event) {
        getLauncher().setYggdrasilData(null);
        getLauncher().exit();
    }

    public Modpacks getSelectedModpack() {
        return modpackComboBox.getValue();
    }

    public void enableButtons() {
        infoButton.setDisable(false);
        settingsButton.setDisable(false);
        launchButton.setDisable(false);
    }

    public void disableButtons() {
        infoButton.setDisable(true);
        settingsButton.setDisable(true);
        launchButton.setDisable(true);
    }

    public void setProgress(double progress) {
        progressBar.setProgress(progress);
    }

    public void changeHead() {
        // TODO: Download avatar asynchronously
        FileManager.download(new File(Variables.launcherPath.getAbsolutePath() + File.separator + "head.png"), "http://www.minotar.net/helm/" + getLauncher().getYggdrasilData().get("displayName") + "/24");
        avatarImageView.setImage(new Image(new File(Variables.launcherPath.getAbsolutePath() + File.separator + "head.png").toURI().toString()));
    }
}
