package de.canitzp.hosenlab2launcher;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileManager extends Thread{

    private static Modpacks modpack;

    public FileManager(Modpacks pack){
        this.setName("FileManager");
        this.setDaemon(true);
        this.start();
        modpack = pack;
    }

    private static void downloadFile(String folder, String url){
        File file = new File(Main.launcherPath + "/Modpacks/" + folder);
        if(file.exists()) file.delete();
        file.getParentFile().mkdirs();
        try {
            URL lib = new URL("http://" + url);
            URLConnection urlConnection = lib.openConnection();
            BufferedInputStream bis = new BufferedInputStream(urlConnection.getInputStream());
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            byte[] b = new byte[8 *1024];
            int read = 0;
            int onlineFileSize = urlConnection.getContentLength();
            System.out.println(onlineFileSize);
            long i = 0;
            while((read = bis.read(b)) > -1){
                bos.write(b, 0, read);
                long p = (file.length() * 100) / onlineFileSize;
                if(p == i){
                    System.out.println(i + "%");
                    if(i == 10 || i == 20 || i == 30 || i == 40 || i == 50 || i == 60 || i == 70 || i == 80 || i == 90 ) Main.window.addToTextArea(p + "%");
                    if(i >= 99) Main.window.addToTextArea("100% - Download Complete!\nStarting unzipping!");
                    //Main.window.update();
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

    public static void downloadModpack(Modpacks modpack){
        downloadFile("/" + modpack.getFolderName() + "/cache/modpack.zip", modpack.getUrlModsAndConfigs());
    }
    public static void downloadLibs(Modpacks modpack){
        downloadFile("/" + modpack.getFolderName() + "/cache/libs.zip", Modpacks.LIBS.getUrlModsAndConfigs());
    }
    public static void downloadAssets(Modpacks modpack){
        downloadFile("/" + modpack.getFolderName() + "/cache/assets.zip", Modpacks.ASSETS.getUrlModsAndConfigs());
    }


    public static void runProcess(String command, String dir) throws IOException{
        new Process(true).runProcess(command, dir);
    }

    private static String getLib(String name){
        return "\"" + Main.launcherPath.getAbsolutePath() + "/Modpacks/" + modpack.getFolderName() + "/libraries/" + name;
    }

    public static String getLibP(String name){
        return getLib(name) + "\";";
    }

    public static String get1710Libs(Modpacks pack){
        modpack = pack;
        return getLibP("gson-2.2.4.jar") + getLibP("guava-15-0.jar") + getLibP("icu4j-core-mojang-51.2.jar") + getLibP("authlib-1.5.21.jar") + getLibP("realms-1.3.5.jar") + getLibP("codecjorbis-20101023.jar")
                + getLibP("codecwav-20101023.jar") + getLibP("libraryjavasound-20101123.jar") + getLibP("librarylwjglopenal-20100824.jar") + getLibP("soundsystem-20120107.jar") + getLibP("commons-codec-1.9.jar")
                + getLibP("commons-io-2.4.jar") + getLibP("commons-logging-1.1.3.jar") + getLibP("netty-all-4.0.10.Final.jar") + getLibP("vecmath-1.3.1.jar") + getLibP("jinput-2.0.5.jar") + getLibP("jinput-platform-2.0.5-natives-windows.jar")
                + getLibP("jutils-1.0.0.jar") + getLibP("jopt-simple-4.5.jar") + getLibP("trove4j-3.0.3.jar") + getLibP("commons-compress-1.8.1.jar") + getLibP("commons-lang3-3.1.jar") + getLibP("httpclient-4.3.3.jar")
                + getLibP("httpcore-4.3.2.jar") + getLibP("log4j-api-2.0-beta9.jar") + getLibP("log4j-core-2.0-beta9.jar") + getLibP("lwjgl-2.9.1.jar") + getLibP("lwjgl_util-2.9.1.jar") + getLibP("lwjgl-platform-2.9.1-natives-windows.jar")
                + getLibP("twitch-5.16.jar") + getLibP("twitch-external-platform-4.5-natives-windows-64.jar") + getLibP("twitch-platform-5.16-natives-windows-64.jar");
    }

    public static String get1710ForgeLibs(){
        return getLibP("akka-actor_2.11-2.3.3.jar") + getLibP("asm-all-5.0.3.jar") + getLibP("commons-lang3-3.3.2.jar") + getLibP("config-1.2.1.jar") + getLibP("guava-17.0.jar") + getLibP("launchwrapper-1.11.jar")
                + getLibP("lzma-0.0.1.jar") + getLibP("scala-actors-migration_2.11-1.1.0.jar") + getLibP("scala-compiler-2.11.1.jar") + getLibP("scala-continuations-library_2.11-1.0.2.jar")
                + getLibP("scala-continuations-plugin_2.11.1-1.0.2.jar") + getLibP("scala-library-2.11.1.jar") + getLibP("scala-parser-combinators_2.11-1.0.1.jar") + getLibP("scala-reflect-2.11.1.jar")
                + getLibP("scala-swing_2.11-1.0.1.jar") + getLibP("scala-xml_2.11-1.0.2.jar") + getLibP("1.7.10.jar") + getLibP("modpack.jar");
    }

    /*
    This UnZip Method came from: http://www.mkyong.com/java/how-to-decompress-files-from-a-zip-file/
     */
    public static void unZipIt(String zipFile, String outputFolder){
        byte[] buffer = new byte[1024];
        try{
            //create output directory is not exists
            File folder = new File(outputFolder);
            if(!folder.exists()){
                folder.getParentFile().mkdirs();
            }
            //get the zip file content
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();
            while(ze!=null){
                String fileName = ze.getName();
                if (ze.isDirectory()) {
                    ze = zis.getNextEntry();
                    continue;
                }
                File newFile = new File(outputFolder + File.separator + fileName);
                System.out.println("file unzip : "+ newFile.getAbsoluteFile());
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
            Main.window.addToTextArea("UnZip Complete! Now you can play!");
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public static void createFile(Modpacks modpack, String folder, String toWrite){
        File file = new File(Main.launcherPath.getAbsolutePath() + "/Modpacks/" + modpack.getFolderName() + folder);
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
