package de.canitzp.hosenlauncher;

import de.canitzp.hosenlauncher.controller.UpdateWindowController;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class Update{

    private Modpacks modpack = null;

    public Update(String modpack){
        if(modpack.equals("Hosenlab 2")) this.modpack = Modpacks.HOSENLAB2;
        else if(modpack.equals("TechnicUniverse"))this.modpack = Modpacks.TECHNICUNIVERSE;
        if(this.modpack != null) updatePack(this.modpack);
    }

    private void updatePack(Modpacks modpack) {
        if(Launch.requestUser(Variables.username, Variables.password)){
            File file = new File(Variables.launcherPath.getAbsolutePath() + File.separator + "Modpacks" + File.separator + modpack.getFolderName() + File.separator + "update.txt");
            if(file.exists() && new File(Variables.launcherPath + File.separator + "Modpacks" + File.separator + modpack.getFolderName() + File.separator + "bin/Minecraft-" + modpack.getForgeVersion().getMCVersion().getName() + ".jar").exists())isUpToDate(modpack);
            else update(modpack);
        } else Variables.mainController.addToLog("Falsche Anmeldedaten. Bitte diese \u00fcberpr\u00fcfen!");
    }

    public static void update(Modpacks modpack){
        boolean b = false;
        Variables.mainController.startBtn.setDisable(true);
        if(!new File(Variables.launcherPath + File.separator + "Modpacks" + File.separator + modpack.getFolderName() + File.separator + "bin/Minecraft-" + modpack.getForgeVersion().getMCVersion().getName() + ".jar").exists()) {updateLibs(modpack); b = true;}
        Variables.mainController.addToLog("--> L\u00f6schen alter Mods und Konfigurationsdateien!");
        FileManager.deleteFile(modpack + "/mods");
        FileManager.deleteFile(modpack + "/config");
        Variables.mainController.addToLog("--> Download Mods und Konfigurationen!");
        FileManager.downloadFile(modpack.getFolderName() + File.separator + "bin" + File.separator + "Forge-" + modpack.getForgeVersion().getName() + ".jar", modpack.getForgeVersion().getDownloadUrl());
        FileManager.downloadModpack(modpack);
        FileManager.unZipIt(Variables.launcherPath + "/Modpacks/" + modpack.getFolderName() + "/cache/" + "/modpack.zip", Variables.launcherPath + "/Modpacks/" + modpack.getFolderName() + "/");
        FileManager.downloadFile("/" + modpack.getFolderName() + "/update.txt", modpack.getUrlVersionTXT());
        if(modpack.containsJmap()){
            FileManager.downloadFile(modpack.getFolderName() + "/mods/JourneyMap-" + Modpacks.JOURNEYMAP.getFolderName() + ".jar", Modpacks.JOURNEYMAP.getUrlModsAndConfigs());
            Variables.mainController.addToLog("JourneyMap von CurseForge heruntergeladen!");
            Platform.runLater(() -> Variables.mainController.progress.setProgress(0.0D));
        }
        Variables.mainController.addToLog("Download vollst\u00e4ndig! Viel Spa\u00df mit " + modpack.getDisplayName() + ".");
        Variables.mainController.infoBtn.setDisable(false);
        Variables.mainController.settingsBtn.setDisable(false);
        Variables.mainController.startBtn.setDisable(false);
    }

    private static void updateLibs(Modpacks modpack){
        Variables.mainController.addToLog("--> Download Libraries!");
        FileManager.downloadFile(modpack.getFolderName() + File.separator + "bin" + File.separator + "Minecraft-" + modpack.getForgeVersion().getMCVersion().getName() + ".jar", modpack.getForgeVersion().getMCVersion().getDownloadClientUrl());
        FileManager.downloadLibs(modpack);
        FileManager.unZipIt(Variables.launcherPath.getAbsolutePath() + "/Modpacks/" + modpack.getFolderName() + "/cache/" + "/libs.zip", Variables.launcherPath.getAbsolutePath() + "/Modpacks/" + modpack.getFolderName() + "/libraries/");
        Variables.mainController.addToLog("--> Download Libraries vollst\u00e4ndig!");
        Variables.mainController.addToLog("--> Download Assets!");
        FileManager.downloadAssets(modpack);
        FileManager.unZipIt(Variables.launcherPath.getAbsolutePath() + "/Modpacks/" + modpack.getFolderName() + "/cache/" + "/assets.zip", Variables.launcherPath.getAbsolutePath() + "/Modpacks/" + modpack.getFolderName() + "/");
        Variables.mainController.addToLog("--> Download Assets vollst\u00e4ndig!");
    }

    private void isUpToDate(Modpacks modpack) {
        String newest = modpack.getUrlVersionTXT();
        URL newestURL = null;
        File updateTXT = new File(Variables.launcherPath.getAbsolutePath() + File.separator + "Modpacks" + File.separator + modpack + File.separator + "update.txt");
        try {
            newestURL = new URL(newest);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(newestURL.openStream()));
            String newestVersion = bufferedReader.readLine().replace(".", "");
            FileReader fr = new FileReader(updateTXT);
            BufferedReader br = new BufferedReader(fr);
            String installedVersion = br.readLine().replace(".", "");
            System.out.println(newestVersion + ",  " + installedVersion);
            if(Integer.parseInt(newestVersion.replace(".", "")) > Integer.parseInt(installedVersion.replace(".", ""))){
                Platform.runLater(() -> {
                    try {
                        UpdateWindow window = new UpdateWindow();
                        window.start(new Stage());
                        window.setChangelog(modpack);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                UpdateWindowController.modpacks = modpack;
            } else launch(modpack);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static void checkLauncherUpdate(){
        String newestVersion = "http://canitzp.de/MCLauncher/update.txt";
        try {
            URL newestURL = new URL(newestVersion);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(newestURL.openStream()));
            String newestVersion2 = bufferedReader.readLine();
            FileReader fr = new FileReader(Variables.launcherSettings);
            BufferedReader br = new BufferedReader(fr);
            String installedVersion = br.readLine();
            if(Integer.parseInt(newestVersion2.replace(".", "")) <= Integer.parseInt(installedVersion.replace(".", ""))){
                System.out.println("Launcher is Up to Date");
            } else {
                File path = new File("");
                File file = new File(path.getAbsolutePath() + "/Updater.jar");
                if(file.exists()) file.delete();
                file.getParentFile().mkdirs();
                try {
                    URL lib = new URL("http://canitzp.de/MCLauncher/Updater.jar");
                    URLConnection urlConnection = lib.openConnection();
                    BufferedInputStream bis = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                    byte[] b = new byte[8 *1024];
                    int read = 0;
                    while((read = bis.read(b)) > -1){
                        bos.write(b, 0, read);
                    }
                    bos.flush();
                    bos.close();
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Variables.save.saveVariables();
                Runtime.getRuntime().exec("java -jar Updater.jar");
                System.exit(0);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

    }

    public static String getLauncherVersion(){
        try {
            return new BufferedReader(new InputStreamReader(new URL("http://canitzp.de/MCLauncher/update.txt").openStream())).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void launch(Modpacks modpack) {
        Launch.launchGame(Variables.username, Variables.password, modpack);
    }
}
