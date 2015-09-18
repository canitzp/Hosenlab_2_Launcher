package de.canitzp.hosenlab2launcher;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Objects;

public class Update extends Thread{

    public static JTextArea textArea = new JTextArea();

    public Update(){
        this.setName("Update");
        this.setDaemon(true);
        this.start();
    }

    public void updatePack(String username, String password){
        File file = new File(Main.launcherPath + "/update.txt");
        if(file.exists())isUpToDate(username, password);
        else update();
    }

    private void isUpToDate(String username, String password){
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
                textArea.setEditable(false);
                DefaultCaret caret = (DefaultCaret)textArea.getCaret();
                caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
                JButton btn1 = new JButton("Yes");
                JButton btn2 = new JButton("No");
                updateFrame.setSize(250, 100);
                Container container = updateFrame.getContentPane();
                container.setLayout(new BorderLayout());
                container.add(btn1, BorderLayout.WEST);
                container.add(btn2, BorderLayout.EAST);
                container.add(textArea, BorderLayout.CENTER);
                updateFrame.setVisible(true);
                updateFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                btn1.addActionListener(e -> {updateFrame.dispose(); update();});
                btn2.addActionListener(e -> {
                    launch(username, password);
                    updateFrame.dispose();
                });
            } else launch(username, password);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private void update(){
        if(!new File(Main.launcherPath + "/libraries/gson-2.2.4.jar").exists()) updateLibs();
        Main.window.addToTextArea("==> Start to download Mods and Configurations! <==");
        Main.window.update();
        new FileManager().downloadFile("/cache/modpack.zip", "modpack.zip");
        new FileManager().unZipIt(Main.cachePath + "/modpack.zip", Main.launcherPath + "/");
        new FileManager().downloadFile("/update.txt", "update.txt");
    }

    private void updateLibs(){
        Main.window.addToTextArea("==> Start to download Libraries! <==");
        Main.window.update();
        new FileManager().downloadFile("/cache/libs.zip", "libs.zip");
        new FileManager().unZipIt(Main.cachePath + "/libs.zip", Main.launcherPath + "/libraries/");
        Main.window.addToTextArea("==> Start to download Assets! <==");
        Main.window.update();
        new FileManager().downloadFile("/cache/assets.zip", "assets.zip");
        new FileManager().unZipIt(Main.cachePath + "/assets.zip", Main.launcherPath + "/");
    }

    private boolean launch(String username, String password){
        if(Launch.launchGame(username, password)){
            Main.stop();
            return true;
        } else {
            Main.restart();
            return false;
        }

    }
}
