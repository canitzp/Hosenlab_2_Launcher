package de.canitzp.hosenlauncher;

import javafx.stage.Stage;

public class ButtonAction {

    public static void startGame(){
        Variables.mainController.infoBtn.setDisable(true);
        Variables.mainController.settingsBtn.setDisable(true);
        Variables.mainController.startBtn.setDisable(true);
        new Update((String) Variables.mainController.dropDown.getValue());
    }

    public static void launchGame(Modpacks modpacks){
        Launch.launchGame(Variables.username, Variables.password, modpacks);
    }

    public static void startSettings() {
        new Settings().start(new Stage());
        Variables.mainController.settingsBtn.setDisable(true);
        Variables.mainController.startBtn.setDisable(true);
        Variables.mainController.infoBtn.setDisable(true);
    }

    public static void saveSettings(){
        try{
            Integer.parseInt(Variables.settingsController.ramMax.getText());
        } catch (NumberFormatException e){
            Variables.mainController.addToLog("Max-Ram ist keine g\u00fcltige Zahl! Zur\u00fcckgesetzt auf 1024!");
            Variables.settingsController.ramMax.setText("1024");
        }
        try{
            Integer.parseInt(Variables.settingsController.ramMin.getText());
        } catch (NumberFormatException e){
            Variables.mainController.addToLog("Min-Ram ist keine g\u00fcltige Zahl! Zur\u00fcckgesetzt auf 128!");
            Variables.settingsController.ramMin.setText("128");
        }
        Variables.save.getVariables();
        Variables.mainController.infoBtn.setDisable(false);
        Variables.mainController.settingsBtn.setDisable(false);
        Variables.mainController.startBtn.setDisable(false);
        Settings.close();
    }
}
