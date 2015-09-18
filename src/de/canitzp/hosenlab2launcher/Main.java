package de.canitzp.hosenlab2launcher;

import java.awt.event.WindowEvent;
import java.io.File;

public class Main {

    public static final File launcherPath = new File("Hosenlab2Launcher/");
    public static final File cachePath = new File(launcherPath.getAbsolutePath() + "/cache/");
    public static Window window;
    public static String username, password;

    public static void main(String[] strings){
        createFolder();
        window = new Window(500, 500, "Hosenlab 2");
        window.addToTextArea("===== Hosenlab 2 Launcher-Log =====");
    }

    private static void createFolder(){
        new File(launcherPath.getAbsolutePath() + "/assets/").mkdirs();
        new File(launcherPath.getAbsolutePath() + "/bin/").mkdirs();
        new File(launcherPath.getAbsolutePath() + "/cache/").mkdirs();
        new File(launcherPath.getAbsolutePath() + "/config/").mkdirs();
        new File(launcherPath.getAbsolutePath() + "/libraries/").mkdirs();
        new File(launcherPath.getAbsolutePath() + "/logs/").mkdirs();
        new File(launcherPath.getAbsolutePath() + "/mods/").mkdirs();
        new File(launcherPath.getAbsolutePath() + "/resourcepacks/").mkdirs();
        new File(launcherPath.getAbsolutePath() + "/saves/").mkdirs();
        new File(launcherPath.getAbsolutePath() + "/server-resource-packs/").mkdirs();
        new File(launcherPath.getAbsolutePath() + "/versions/").mkdirs();
    }

    public static void stop(){
        window.addToTextArea("You Game starts in a few seconds!");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
        window.dispose();
    }


    public static void restart() {
        window.dispose();
        main(new String[]{});
        window.addToTextArea("Wrong Username or Password!");
    }
}
