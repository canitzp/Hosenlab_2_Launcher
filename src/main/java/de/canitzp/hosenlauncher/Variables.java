package de.canitzp.hosenlauncher;

import de.canitzp.hosenlauncher.gui.controllers.ModpackInfoController;
import de.canitzp.hosenlauncher.gui.controllers.MainController;
import de.canitzp.hosenlauncher.gui.controllers.SettingsController;
import de.canitzp.hosenlauncher.gui.controllers.UpdateController;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Map;

@SuppressWarnings("ALL")
public class Variables {

    public static String username, password;
    public static int maxRam = 1024, minRam =  128;
    public static boolean debug, outputOn = false, isSettings, isDesc;
    public static File launcherPath = new File("Hosenlab2Launcher" + File.separator);
    public static Save save = new Save(launcherPath.getAbsolutePath() + "/launcher.ctp");
    public static InputStream input;
    public static File launcherSettings = new File(launcherPath.getAbsolutePath() + "/settings.txt");
    public static Map<String, Object> loginMap;

    public static void startup(){
        try {
            if(save.exists()){
                if(save.read("loginMap") != null){
                    Variables.loginMap = (Map<String, Object>) save.read("loginMap");
                } else loginMap = null;
                if(save.read("maxRam") != null){
                    maxRam = (int) save.read("maxRam");
                } else maxRam = 1024;
                if(save.read("minRam") != null){
                    minRam = (int) save.read("minRam");
                } else minRam = 128;
                if(save.read("debug") != null){
                    debug = (boolean) save.read("debug");
                } else debug = false;
            }
        } catch (Throwable e) {
            maxRam = 1024;
            minRam = 128;
            debug = false;
            loginMap = null;
        }

    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        Variables.debug = debug;
    }

    /*public static void startOutput(){
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
    }*/
}
