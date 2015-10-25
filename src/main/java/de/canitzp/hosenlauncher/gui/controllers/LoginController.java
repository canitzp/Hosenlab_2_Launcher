package de.canitzp.hosenlauncher.gui.controllers;

import com.google.common.base.Strings;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import de.canitzp.hosenlauncher.Launch;
import de.canitzp.hosenlauncher.gui.Controllers;
import de.canitzp.hosenlauncher.gui.GuiController;
import de.canitzp.hosenlauncher.gui.exceptions.ControllerLoadException;
import de.canitzp.hosenlauncher.gui.exceptions.InvalidFxmlException;
import de.canitzp.hosenlauncher.gui.exceptions.NoSuchControllerException;
import de.canitzp.hosenlauncher.util.AlertBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;

import java.io.IOException;
import java.net.Proxy;
import java.util.Map;

public class LoginController extends GuiController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button exitButton;

    @FXML
    public void onLoginButtonAction(ActionEvent event) {
        if (!Strings.isNullOrEmpty(usernameField.getText()) && !Strings.isNullOrEmpty(passwordField.getText())) {
            if (authenticate()) {
                try {
                    getGui().load(Controllers.MAIN, "/views/main.fxml", "title.launcher");
                } catch (ControllerLoadException | InvalidFxmlException e) {
                    getLauncher().getLogger().error("Failed to load controller", e);
                    getLauncher().exit();
                }
                unload();
            } else {
                AlertBuilder
                        .newBuilder(Alert.AlertType.ERROR, ButtonType.OK)
                        .modality(Modality.APPLICATION_MODAL)
                        .owner(getStage())
                        .contentText("alert.invalid-credentials")
                        .showAndWait();
            }
        }
    }

    @FXML
    public void onExitButtonAction(ActionEvent event) {
        getLauncher().exit();
    }

    private boolean authenticate() {
        YggdrasilUserAuthentication authentication = (YggdrasilUserAuthentication) (new YggdrasilAuthenticationService(Proxy.NO_PROXY, "1")).createUserAuthentication(Agent.MINECRAFT);
        authentication.setUsername(usernameField.getText());
        authentication.setPassword(passwordField.getText());
        try {
            authentication.logIn();
            Map<String, Object> data = authentication.saveForStorage();
            data.put("token", authentication.getAuthenticatedToken());
            data.put("userType", authentication.getUserType().getName());
            data.put("prop", (new GsonBuilder()).registerTypeAdapter(PropertyMap.class, new Launch.OldPropertyMapSerializer()).create().toJson(authentication.getUserProperties()));
            getLauncher().setYggdrasilData(data);
            return true;
        } catch (AuthenticationException e) {
            getLauncher().getLogger().error("Failed to authenticate", e);
            return false;
        }
    }
}
