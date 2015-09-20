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

    public void updatePack(Modpacks modpack){
        File file = new File(Main.launcherPath.getAbsolutePath() + "/Modpacks/" + modpack.getFolderName() + "/update.txt");
        System.out.println(file.getAbsolutePath());
        if(file.exists())isUpToDate(modpack);
        else update(modpack);
    }

    private void isUpToDate(Modpacks modpack){
        String newest = modpack.getVersion();
        URL newestURL = null;
        File updateTXT = new File(Main.launcherPath + "/Modpacks/" + modpack + "/update.txt");
        try {
            //newestURL = new URL(newest);
            //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(newestURL.openStream()));
            //String newestVersion = bufferedReader.readLine().replace(".", "");
            FileReader fr = new FileReader(updateTXT);
            BufferedReader br = new BufferedReader(fr);
            String installedVersion = br.readLine().replace(".", "");
            //System.out.println(newestVersion);
            //System.out.println(installedVersion);
            if(!Objects.equals(newest.replace(".", ""), installedVersion)){
                Main.window.addToTextArea("There is an Update!\nInstalled Version: " + installedVersion + " Newest Version:" + newest);
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
                btn1.addActionListener(e -> {updateFrame.dispose(); update(modpack);});
                btn2.addActionListener(e -> {
                    launch(modpack);
                    updateFrame.dispose();
                });
            } else launch(modpack);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private void update(Modpacks modpack){
        if(!new File(Main.launcherPath + "/Modpacks/" + modpack.getFolderName() + "/libraries/gson-2.2.4.jar").exists()) updateLibs(modpack);
        Main.window.addToTextArea("==> Start to download Mods and Configurations! <==");
        Main.window.update();
        FileManager.downloadModpack(modpack);
        FileManager.unZipIt(Main.launcherPath + "/Modpacks/" + modpack.getFolderName() + "/cache/" + "/modpack.zip", Main.launcherPath + "/Modpacks/" + modpack.getFolderName() + "/");
        //new FileManager(modpack).downloadFile("/update.txt", "update.txt");
        FileManager.createFile(modpack, "/update.txt", modpack.getVersion());
    }

    private void updateLibs(Modpacks modpack){
        Main.window.addToTextArea("==> Start to download Libraries! <==");
        Main.window.update();
        FileManager.downloadLibs(modpack);
        FileManager.unZipIt(Main.launcherPath + "/Modpacks/" + modpack.getFolderName() + "/cache/" + "/libs.zip", Main.launcherPath + "/Modpacks/" + modpack.getFolderName() + "/libraries/");
        Main.window.clear();
        Main.window.addToTextArea("==> Downloading Libraries complete! <==");
        Main.window.addToTextArea("==> Start to download Assets! <==");
        Main.window.update();
        FileManager.downloadAssets(modpack);
        FileManager.unZipIt(Main.launcherPath + "/Modpacks/" + modpack.getFolderName() + "/cache/" + "/assets.zip", Main.launcherPath + "/Modpacks/" + modpack.getFolderName() + "/");
        Main.window.clear();
        Main.window.addToTextArea("==> Downloading Libraries complete! <==");
        Main.window.addToTextArea("==> Downloading Assets complete! <==");
    }

    private void launch(Modpacks modpack) {
        int i = Launch.launchGame(Main.username, Main.password, modpack);
        if(i == 0){
            Main.startMC();
        }
        if(i == 1){
            Main.restart("Wrong Username or Password!");
        }
        if(i == -1){
            Main.restart("Java Variable is Wrong. Please check this!");
        }
    }
}
