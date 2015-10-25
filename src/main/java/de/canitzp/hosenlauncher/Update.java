package de.canitzp.hosenlauncher;

import de.canitzp.hosenlauncher.gui.Controllers;
import de.canitzp.hosenlauncher.gui.GuiController;
import de.canitzp.hosenlauncher.gui.controllers.MainController;
import de.canitzp.hosenlauncher.gui.controllers.UpdateController;
import de.canitzp.hosenlauncher.gui.exceptions.ControllerLoadException;
import de.canitzp.hosenlauncher.gui.exceptions.InvalidFxmlException;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class Update {
    private final Modpacks modpack;

    // TODO: Rewrite modpack updater
    public Update(Modpacks modpack) {
        this.modpack = modpack;
        if (modpack != null) {
            updatePack(modpack);
        }
    }

    private void updatePack(Modpacks modpack) {
        File file = new File(Variables.launcherPath.getAbsolutePath() + File.separator + "Modpacks" + File.separator + modpack.getFolderName() + File.separator + "update.txt");
        if (file.exists() && new File(Variables.launcherPath + File.separator + "Modpacks" + File.separator + modpack.getFolderName() + File.separator + "bin/Minecraft-" + modpack.getForgeVersion().getMCVersion().getName() + ".jar").exists())
            isUpToDate(modpack);
        else update(modpack);
    }

    public static void update(Modpacks modpack) {
        MainController mainController = Hosenlauncher.getInstance().getGui().get(Controllers.MAIN);
        mainController.disableButtons();

        boolean b = false;
        if (!new File(Variables.launcherPath + File.separator + "Modpacks" + File.separator + modpack.getFolderName() + File.separator + "bin/Minecraft-" + modpack.getForgeVersion().getMCVersion().getName() + ".jar").exists()) {
            updateLibs(modpack);
            b = true;
        }
        mainController.addToLog("--> L\u00f6schen des alten Mods-Ordner!");
        FileManager.deleteFile(modpack + "/mods");
        //FileManager.deleteFile(modpack + "/config");
        mainController.addToLog("--> Download Mods und Konfigurationen!");
        FileManager.downloadFile(modpack.getFolderName() + File.separator + "bin" + File.separator + "Forge-" + modpack.getForgeVersion().getName() + ".jar", modpack.getForgeVersion().getDownloadUrl());
        FileManager.downloadModpack(modpack);
        FileManager.unZipIt(Variables.launcherPath + "/Modpacks/" + modpack.getFolderName() + "/cache/" + "/modpack.zip", Variables.launcherPath + "/Modpacks/" + modpack.getFolderName() + "/");
        FileManager.downloadFile("/" + modpack.getFolderName() + "/update.txt", modpack.getUrlVersionTXT());
        if (modpack.containsJmap()) {
            FileManager.downloadFile(modpack.getFolderName() + "/mods/JourneyMap-" + Modpacks.JOURNEYMAP.getFolderName() + ".jar", Modpacks.JOURNEYMAP.getUrlModsAndConfigs());
            mainController.addToLog("JourneyMap von CurseForge heruntergeladen!");
            Platform.runLater(() -> mainController.setProgress(0.0D));
        }
        mainController.addToLog("Download vollst\u00e4ndig! Viel Spa\u00df mit " + modpack.getDisplayName() + ".");
        mainController.enableButtons();
    }

    private static void updateLibs(Modpacks modpack) {
        MainController mainController = Hosenlauncher.getInstance().getGui().get(Controllers.MAIN);
        mainController.addToLog("--> Download Libraries!");
        FileManager.downloadFile(modpack.getFolderName() + File.separator + "bin" + File.separator + "Minecraft-" + modpack.getForgeVersion().getMCVersion().getName() + ".jar", modpack.getForgeVersion().getMCVersion().getDownloadClientUrl());
        FileManager.downloadLibs(modpack);
        FileManager.unZipIt(Variables.launcherPath.getAbsolutePath() + "/Modpacks/" + modpack.getFolderName() + "/cache/" + "/libs.zip", Variables.launcherPath.getAbsolutePath() + "/Modpacks/" + modpack.getFolderName() + "/libraries/");
        mainController.addToLog("--> Download Libraries vollst\u00e4ndig!");
        mainController.addToLog("--> Download Assets!");
        FileManager.downloadAssets(modpack);
        FileManager.unZipIt(Variables.launcherPath.getAbsolutePath() + "/Modpacks/" + modpack.getFolderName() + "/cache/" + "/assets.zip", Variables.launcherPath.getAbsolutePath() + "/Modpacks/" + modpack.getFolderName() + "/");
        mainController.addToLog("--> Download Assets vollst\u00e4ndig!");
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
            if (Integer.parseInt(newestVersion.replace(".", "")) > Integer.parseInt(installedVersion.replace(".", ""))) {
                Platform.runLater(() -> {
                    Hosenlauncher launcher = Hosenlauncher.getInstance();
                    UpdateController controller;
                    try {
                        controller = (UpdateController) launcher.getGui().load(Controllers.MODPACK_UPDATE, "/views/update.fxml", "title.launcher");
                    } catch (InvalidFxmlException | ControllerLoadException e) {
                        launcher.getLogger().error("Failed to load controller", e);
                        return;
                    }
                    controller.initModpack(modpack);
                });
            } else launch(modpack);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static void checkLauncherUpdate() {
        String newestVersion = "http://canitzp.de/MCLauncher/update.txt";
        try {
            URL newestURL = new URL(newestVersion);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(newestURL.openStream()));
            String newestVersion2 = bufferedReader.readLine();
            FileReader fr = new FileReader(Variables.launcherSettings);
            BufferedReader br = new BufferedReader(fr);
            String installedVersion = br.readLine();
            if (Integer.parseInt(newestVersion2.replace(".", "")) <= Integer.parseInt(installedVersion.replace(".", ""))) {
                System.out.println("Launcher is Up to Date");
            } else {
                File path = new File("");
                File file = new File(path.getAbsolutePath() + "/Updater.jar");
                if (file.exists()) file.delete();
                file.getParentFile().mkdirs();
                try {
                    URL lib = new URL("http://canitzp.de/MCLauncher/Updater.jar");
                    URLConnection urlConnection = lib.openConnection();
                    BufferedInputStream bis = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                    byte[] b = new byte[8 * 1024];
                    int read = 0;
                    while ((read = bis.read(b)) > -1) {
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

    public static String getLauncherVersion() {
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
