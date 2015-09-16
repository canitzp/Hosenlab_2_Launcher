package de.canitzp.hosenlab2launcher;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Objects;

public class UpdateChecker {

    public static void updatePack(){
        isUpToDate();
    }

    private static void isUpToDate(){
        String newest = "http://canitzp.de/Modpacks/Hosenlab%202/update.txt";
        URL newestURL = null;
        File updateTXT = new File(Main.launcherPath + "/update.txt");
        try {
            newestURL = new URL(newest);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(newestURL.openStream()));
            String newestVersion = bufferedReader.readLine().replace(".", "");
            FileReader fr = new FileReader(updateTXT);
            BufferedReader br = new BufferedReader(fr);
            String installedVersion = br.readLine().replace(".", "");
            //System.out.println(newestVersion);
            //System.out.println(installedVersion);
            if(!Objects.equals(newestVersion, installedVersion)){
                Main.window.addToTextArea("There is an Update!\nInstalled Version: " + installedVersion + " Newest Version:" + newestVersion);
                JFrame updateFrame = new JFrame("Update?");
                JButton btn1 = new JButton("Yes");
                JButton btn2 = new JButton("No");
                updateFrame.setSize(250, 100);
                Container container = updateFrame.getContentPane();
                container.setLayout(new BorderLayout());
                container.add(btn1, BorderLayout.WEST);
                container.add(btn2, BorderLayout.EAST);
                updateFrame.setVisible(true);
                updateFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                btn1.addActionListener(e -> {update(); updateFrame.dispose();});
                btn2.addActionListener(e -> {Launch.launchGame(); updateFrame.dispose();});
            } else Launch.launchGame();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static void update(){

    }

}

