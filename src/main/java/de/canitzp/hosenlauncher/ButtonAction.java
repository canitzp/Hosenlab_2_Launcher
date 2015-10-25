package de.canitzp.hosenlauncher;

public class ButtonAction {

    public static void launchGame(Modpacks modpacks){
        Launch.launchGame(Variables.username, Variables.password, modpacks);
    }
}
