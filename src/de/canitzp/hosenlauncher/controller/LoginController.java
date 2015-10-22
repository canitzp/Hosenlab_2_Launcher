package de.canitzp.hosenlauncher.controller;

import com.google.gson.GsonBuilder;
import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import de.canitzp.hosenlauncher.Launch;
import de.canitzp.hosenlauncher.Main;
import de.canitzp.hosenlauncher.Variables;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.Proxy;

public class LoginController {

    public static Stage stage;
    public TextField userFieled;
    public PasswordField passField;
    public Button loginBtn;

    public void login(ActionEvent actionEvent) {
        if(userFieled.getText() != null && passField.getText() != null){
            if(requestUser()){
                Main.createMainWindow();
            }
        }
    }

    private boolean requestUser() {
        YggdrasilUserAuthentication authentication = (YggdrasilUserAuthentication)(new YggdrasilAuthenticationService(Proxy.NO_PROXY, "1")).createUserAuthentication(Agent.MINECRAFT);
        authentication.setUsername(userFieled.getText());
        authentication.setPassword(passField.getText());
        try {
            authentication.logIn();
            Variables.loginMap = authentication.saveForStorage();
            Variables.loginMap.put("token", authentication.getAuthenticatedToken());
            Variables.loginMap.put("userType", authentication.getUserType().getName());
            Variables.loginMap.put("prop", (new GsonBuilder()).registerTypeAdapter(PropertyMap.class, new Launch.OldPropertyMapSerializer()).create().toJson(authentication.getUserProperties()));
            return true;
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return false;
        }



    }

}
