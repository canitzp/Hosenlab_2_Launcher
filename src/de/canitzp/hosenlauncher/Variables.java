package de.canitzp.hosenlauncher;

import de.canitzp.hosenlauncher.controller.DescriptionController;
import de.canitzp.hosenlauncher.controller.MainController;
import de.canitzp.hosenlauncher.controller.SettingsController;
import de.canitzp.hosenlauncher.controller.UpdateWindowController;
import org.apache.commons.io.IOUtils;

import java.io.*;

public class Variables {

    public static String username, password;
    public static int maxRam, minRam;
    public static boolean debug, outputOn = false, isSettings, isDesc;
    public static File launcherPath = new File("Hosenlab2Launcher" + File.separator);
    public static Thread startThread, launchThread;
    public static Save save = new Save(launcherPath.getAbsolutePath() + "/launcher.ctp");
    public static MainController mainController;
    public static SettingsController settingsController;
    public static UpdateWindowController updateController;
    public static InputStream input;
    public static DescriptionController descriptionController;
    public static File launcherSettings = new File(launcherPath.getAbsolutePath() + "/settings.txt");

    public static void startup(){
        try {
            if (save.exists() && save.read("username") != null) {
                username = (String) save.read("username");
                password = save.readPassword();
                maxRam = (int) save.read("maxRam");
                minRam = (int) save.read("minRam");
                debug = (boolean) save.read("debug");
            } else {
                username = null;
                password = null;
                maxRam = 1024;
                minRam = 128;
                debug = false;
            }
        } catch (Throwable e) {
            username = null;
            password = null;
            maxRam = 1024;
            minRam = 128;
            debug = false;
        }
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        Variables.debug = debug;
    }

    public static void startOutput(){
        outputOn = true;
        Thread thread = new Thread(){
            @Override
        public void run(){
                while(outputOn){
                    try {
                        if(input != null){
                            mainController.addToLog(IOUtils.toString(input));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }
}
