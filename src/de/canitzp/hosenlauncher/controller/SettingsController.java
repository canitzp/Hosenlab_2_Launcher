package de.canitzp.hosenlauncher.controller;

import de.canitzp.hosenlauncher.ButtonAction;
import de.canitzp.hosenlauncher.Variables;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SettingsController {

    public TextField userField;
    public PasswordField passField;
    public TextField ramMax;
    public TextField ramMin;
    public CheckBox debugBool;
    public Button saveBtn;

    public void saveSettingsHandler(ActionEvent actionEvent) {
        ButtonAction.saveSettings();
    }

    public void startup(){
        userField.setText(Variables.username);
        passField.setText(Variables.password);
        ramMax.setText(Integer.toString(Variables.maxRam));
        ramMin.setText(Integer.toString(Variables.minRam));
        debugBool.setSelected(Variables.debug);
    }
}
