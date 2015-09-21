package de.canitzp.hosenlab2launcher;

import de.canitzp.hosenlab2launcher.frames.UpdateFrame;
import de.canitzp.hosenlab2launcher.frames.Window;

import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Map;

public class Main {

    public static final File launcherPath = new File("Hosenlab2Launcher/");
    public static final File cachePath = new File(launcherPath.getAbsolutePath() + "/cache/");
    public static Window window;
    public static String username, password;
    public static String modpack;
    public static Save save;
    public static Map<String, String> dataMap;
    public static int maxRam = 1024;
    public static String java = "java";
    public static int minRam = 256;

    public static void main(String[] strings){
        save = new Save(launcherPath.getAbsolutePath() + "/launcher.ctp");
        create();
        window = new Window(500, 500, "Hosenlab 2");
        window.addToTextArea("===== Hosenlab 2 Launcher-Log =====");
    }

    private static void create(){
            try {
                if(save.exists() && save.read("username") != null){
                username = (String) save.read("username");
                password = save.readPassword();
                maxRam = (int) save.read("maxRam");
                java = (String) save.read("java");
                minRam = (int) save.read("minRam");
                }
            } catch (Throwable e) {
                e.printStackTrace();
                save.deleteSaveRestart();
            }
    }

    public static void stop(){
        System.gc();
        window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
        window.dispose();
        System.exit(0);
    }

    public static void stopAndSave(){
        save.saveVariables();
        System.gc();
        window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
        window.dispose();
        System.exit(0);
    }

    public static void startMC(){
        window.addToTextArea("You Game starts in a few seconds!");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        save.saveVariables();
        System.gc();
        window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
        window.dispose();
        System.exit(0);
    }

    public static void restart(String message) {
        window.dispose();
        window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
        main(new String[]{});
        window.addToTextArea(message);
    }
    public static void restart(){
        restart("");
    }
    public static void restartSave(){
        save.saveVariables();
        restart();
    }
}