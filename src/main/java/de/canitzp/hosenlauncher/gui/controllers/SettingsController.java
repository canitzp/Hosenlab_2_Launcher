package de.canitzp.hosenlauncher.gui.controllers;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigValueFactory;
import de.canitzp.hosenlauncher.Language;
import de.canitzp.hosenlauncher.gui.Controllers;
import de.canitzp.hosenlauncher.gui.GuiController;
import de.canitzp.hosenlauncher.gui.exceptions.NoSuchControllerException;
import de.canitzp.hosenlauncher.util.AlertBuilder;
import de.canitzp.hosenlauncher.util.ConfigUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.FileNotFoundException;

public class SettingsController extends GuiController {
    @FXML
    private TextField minMemoryField;
    @FXML
    private TextField maxMemoryField;
    @FXML
    private ChoiceBox<Language> languageChoiceBox;
    @FXML
    private CheckBox rememberUsernameCheckBox;
    @FXML
    private CheckBox debugCheckBox;

    private Config config;
    private MainController mainController;

    @Override
    protected void initialize() {
        languageChoiceBox.getItems().setAll(Language.values());
        languageChoiceBox.setConverter(new StringConverter<Language>() {
            @Override
            public String toString(Language object) {
                return object.getLocalizedName();
            }

            @Override
            public Language fromString(String string) {
                return null;
            }
        });

        config = getLauncher().getConfig();
        minMemoryField.setText(String.valueOf(config.getInt("jvm-args.default-xms")));
        maxMemoryField.setText(String.valueOf(config.getInt("jvm-args.default-xmx")));
        languageChoiceBox.setValue(Language.getByKey(config.getString("lang")));
        rememberUsernameCheckBox.setSelected(config.getBoolean("remember-username"));
        debugCheckBox.setSelected(Boolean.getBoolean("debug"));

        mainController = getGui().get(Controllers.MAIN);
        mainController.disableButtons();
        getStage().setOnCloseRequest(event -> mainController.enableButtons());
    }

    @FXML
    private void onSaveButtonAction(ActionEvent event) {
        if (!NumberUtils.isNumber(minMemoryField.getText())) {
            AlertBuilder
                    .newBuilder(Alert.AlertType.ERROR, ButtonType.OK)
                    .owner(getStage())
                    .contentText(String.format(getFormsBundle().getString("alert.no-valid-number"), getFormsBundle().getString("label.min-memory")), false)
                    .showAndWait();
            return;
        }
        if (!NumberUtils.isNumber(maxMemoryField.getText())) {
            AlertBuilder
                    .newBuilder(Alert.AlertType.ERROR, ButtonType.OK)
                    .owner(getStage())
                    .contentText(String.format(getFormsBundle().getString("alert.no-valid-number"), getFormsBundle().getString("label.max-memory")), false)
                    .showAndWait();
            return;
        }

        config = config
                .withValue("jvm-args.default-xms", ConfigValueFactory.fromAnyRef(Integer.valueOf(minMemoryField.getText())))
                .withValue("jvm-args.default-xmx", ConfigValueFactory.fromAnyRef(Integer.valueOf(maxMemoryField.getText())))
                .withValue("lang", ConfigValueFactory.fromAnyRef(languageChoiceBox.getValue().getKey()))
                .withValue("remember-username", ConfigValueFactory.fromAnyRef(rememberUsernameCheckBox.isSelected()))
                .withValue("debug", ConfigValueFactory.fromAnyRef(debugCheckBox.isSelected()));
        mainController.enableButtons();

        try {
            getLauncher().getLogger().info("Rendering new config to file");
            ConfigUtils.save(config, getLauncher().getConfigFile());
        } catch (FileNotFoundException e) {
            getLauncher().getLogger().error("Could not save config: Config file does not exist", e);
            AlertBuilder
                    .newBuilder(Alert.AlertType.ERROR, ButtonType.OK)
                    .owner(getStage())
                    .contentText("alert.failed-to-save-config")
                    .showAndWait();
            return;
        }

        getLauncher().setConfig(config);

        try {
            getGui().unload(this);
        } catch (NoSuchControllerException e) {
            getLauncher().getLogger().error("Failed to unload controller", e);
        }
    }

    @FXML
    private void onCancelButtonAction(ActionEvent event) {
        mainController.enableButtons();
        unload();
    }
}
