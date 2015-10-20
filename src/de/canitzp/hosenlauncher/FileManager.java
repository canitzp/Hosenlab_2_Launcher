package de.canitzp.hosenlauncher;

import javafx.application.Platform;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileManager {

    public static void downloadFile(String folder, String url) {
        File file = new File(Variables.launcherPath + File.separator + "Modpacks" + File.separator + folder);
        download(file, url);
    }

    public static void download(File file, String url){
        if (file.exists()) file.delete();
        file.getParentFile().mkdirs();
        try {
            URL lib;
            if(url.startsWith("http://") || url.startsWith("https://")){
                lib = new URL(url);
            } else {
                lib = new URL("http://" + url);
            }

            URLConnection urlConnection = lib.openConnection();
            BufferedInputStream bis = new BufferedInputStream(urlConnection.getInputStream());
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            byte[] b = new byte[8 * 1024];
            int read = 0;
            int onlineFileSize = urlConnection.getContentLength();
            System.out.println(onlineFileSize);
            long i = 0;
            while ((read = bis.read(b)) > -1) {
                bos.write(b, 0, read);
                double p = (file.length() * 100) / onlineFileSize;
                if (p == i) {
                    System.out.println(i + "%");
                    final double j = i;
                    Platform.runLater(() ->{
                        if(j < 99) Variables.mainController.progress.setProgress((p * onlineFileSize / 100) / onlineFileSize);
                        else Variables.mainController.progress.setProgress(0.0D);
                    });
                    i++;
                }
            }
            bos.flush();
            bos.close();
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void downloadModpack(Modpacks modpack) {
        downloadFile("/" + modpack.getFolderName() + "/cache/modpack.zip", modpack.getUrlModsAndConfigs());
    }

    public static void downloadLibs(Modpacks modpack) {
        downloadFile(File.separator + modpack.getFolderName() + File.separator + "cache" + File.separator + "libs.zip", Modpacks.LIBS.getUrlModsAndConfigs());
    }

    public static void downloadAssets(Modpacks modpack) {
        downloadFile("/" + modpack.getFolderName() + "/cache/assets.zip", Modpacks.ASSETS.getUrlModsAndConfigs());
    }

    private static String getLib(String name) {
        return "\"" + Variables.launcherPath.getAbsolutePath() + File.separator + "Modpacks" + File.separator + modpack.getFolderName() + File.separator + "libraries" + File.separator + name;
    }

    public static String getLibP(String name) {
        return getLib(name) + "\";";
    }

    public static String getBin(String name, Modpacks pack) {
        return "\"" + Variables.launcherPath.getAbsolutePath() + File.separator + "Modpacks" + File.separator + pack.getFolderName() + File.separator + "bin" + File.separator + name + "\";";
    }

    private static Modpacks modpack;

    public static String get1710Libs(Modpacks pack) {
        modpack = pack;
        return getLibP("gson-2.2.4.jar") + getLibP("guava-15-0.jar") + getLibP("icu4j-core-mojang-51.2.jar") + getLibP("authlib-1.5.21.jar") + getLibP("realms-1.3.5.jar") + getLibP("codecjorbis-20101023.jar")
                + getLibP("codecwav-20101023.jar") + getLibP("libraryjavasound-20101123.jar") + getLibP("librarylwjglopenal-20100824.jar") + getLibP("soundsystem-20120107.jar") + getLibP("commons-codec-1.9.jar")
                + getLibP("commons-io-2.4.jar") + getLibP("commons-logging-1.1.3.jar") + getLibP("netty-all-4.0.10.Final.jar") + getLibP("vecmath-1.3.1.jar") + getLibP("jinput-2.0.5.jar") + getLibP("jinput-platform-2.0.5-natives-windows.jar")
                + getLibP("jutils-1.0.0.jar") + getLibP("jopt-simple-4.5.jar") + getLibP("trove4j-3.0.3.jar") + getLibP("commons-compress-1.8.1.jar") + getLibP("commons-lang3-3.1.jar") + getLibP("httpclient-4.3.3.jar")
                + getLibP("httpcore-4.3.2.jar") + getLibP("log4j-api-2.0-beta9.jar") + getLibP("log4j-core-2.0-beta9.jar") + getLibP("lwjgl-2.9.1.jar") + getLibP("lwjgl_util-2.9.1.jar") + getLibP("lwjgl-platform-2.9.1-natives-windows.jar")
                + getLibP("twitch-5.16.jar") + getLibP("twitch-external-platform-4.5-natives-windows-64.jar") + getLibP("twitch-platform-5.16-natives-windows-64.jar");
    }

    public static String get1710ForgeLibs(Modpacks modpacks) {
        return getLibP("akka-actor_2.11-2.3.3.jar") + getLibP("asm-all-5.0.3.jar") + getLibP("commons-lang3-3.3.2.jar") + getLibP("config-1.2.1.jar") + getLibP("guava-17.0.jar") + getLibP("launchwrapper-1.11.jar")
                + getLibP("lzma-0.0.1.jar") + getLibP("scala-actors-migration_2.11-1.1.0.jar") + getLibP("scala-compiler-2.11.1.jar") + getLibP("scala-continuations-library_2.11-1.0.2.jar")
                + getLibP("scala-continuations-plugin_2.11.1-1.0.2.jar") + getLibP("scala-library-2.11.1.jar") + getLibP("scala-parser-combinators_2.11-1.0.1.jar") + getLibP("scala-reflect-2.11.1.jar")
                + getLibP("scala-swing_2.11-1.0.1.jar") + getLibP("scala-xml_2.11-1.0.2.jar")
                + getBin("Forge-" + modpacks.getForgeVersion().getName() + ".jar", modpacks) + getBin("Minecraft-" + modpacks.getForgeVersion().getMCVersion().getName() + ".jar", modpacks);
    }

    /*
    This UnZip Method came from: http://www.mkyong.com/java/how-to-decompress-files-from-a-zip-file/
     */
    public static void unZipIt(String zipFile, String outputFolder) {
        Variables.mainController.addToLog("Entpacken gestartet!");
        byte[] buffer = new byte[1024];
        try {
            //create input directory is not exists
            File folder = new File(outputFolder);
            if (!folder.exists()) {
                folder.getParentFile().mkdirs();
            }
            //get the zip file content
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {
                String fileName = ze.getName();
                if (ze.isDirectory()) {
                    ze = zis.getNextEntry();
                    continue;
                }
                File newFile = new File(outputFolder + File.separator + fileName);
                System.out.println("file unzip : " + newFile.getAbsoluteFile());
                //create all non exists folders
                //else you will hit FileNotFoundException for compressed folder
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                ze = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
            System.out.println("Done");
            Variables.mainController.addToLog("Entpacken vollst\u00e4ndig!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void deleteFile(String folder) {
        File file = new File(Variables.launcherPath.getAbsolutePath() + File.separator + "Modpacks" + File.separator + folder);
        try {
            FileUtils.deleteDirectory(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Modpacks getModpackFromString(String string) {
        if (string.equals("Hosenlab 2")) return Modpacks.HOSENLAB2;
        if (string.equals("TechnicUniverse")) return Modpacks.TECHNICUNIVERSE;
        return null;
    }

    public static String getDescription(Modpacks modpack) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            if (modpack.getDescLink() != null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(new URL("http://" + modpack.getDescLink()).openStream()));
                String line;
                while((line =  br.readLine()) != null){
                    stringBuffer.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

    public static String getChangelog(Modpacks modpack) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            if (modpack.getChangelog() != null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(new URL("http://" + modpack.getChangelog()).openStream()));
                String line;
                while((line =  br.readLine()) != null){
                    stringBuffer.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }


    public static void createFile(String folder, String toWrite){
        File file = new File(Variables.launcherPath.getAbsolutePath() + folder);
        if(file.exists()) file.delete();
        file.getParentFile().mkdirs();
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(file));
            br.append(toWrite);
            br.flush();
            br.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
