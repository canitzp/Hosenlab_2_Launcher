package de.canitzp.hosenlab2launcher;

import java.io.File;

public class Main {

    public static final File launcherPath = new File("Hosenlab 2 Launcher/");
    public static final File cachePath = new File(launcherPath + "cache/");
    public static Window window;

    public static void main(String[] strings){
        launcherPath.mkdirs();
        window = new Window(500, 500, "Hosenlab 2");
        //System.out.println(launcherPath.getAbsolutePath());
        window.addToTextArea("===== Hosenlab 2 Launcher-Log =====");
    }

}
