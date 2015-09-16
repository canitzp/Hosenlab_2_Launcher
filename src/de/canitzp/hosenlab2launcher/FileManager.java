package de.canitzp.hosenlab2launcher;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileManager {

    public static void downloadFile(String clientName, String serverName){
        File file = new File(Main.launcherPath + clientName);
        file.getParentFile().mkdirs();
        try {
            URL lib = new URL("http://canitzp.de/Modpacks/Hosenlab%202/" + serverName);
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
                    System.out.println(p + "%");
                    Main.window.addToTextArea(p + "%");
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

    public static void download1710Libs(String modpack){
        downloadFile("modpacks/" + modpack + "/cache/Libraries.zip", "libs.zip");
        downloadFile("modpacks/" + modpack + "/cache/Assets.zip", "assets.zip");
        unZipIt(System.getProperty("user.home") + "/AppData/Roaming/.dqm-launcher/modpacks/" + modpack + "/cache/Libraries.zip", System.getProperty("user.home") + "/AppData/Roaming/.dqm-launcher/modpacks/" + modpack + "/libs/");
        unZipIt(System.getProperty("user.home") + "/AppData/Roaming/.dqm-launcher/modpacks/" + modpack + "/cache/Assets.zip", System.getProperty("user.home") + "/AppData/Roaming/.dqm-launcher/modpacks/" + modpack + "/");
    }

    public static boolean checkDatei(String name){
        File file = new File(System.getProperty("user.home") + "/AppData/Roaming/.dqm-launcher/" + name);
        return file.exists();
    }

    public static void dateianlage(String name){
        File file = new File(System.getProperty("user.home") + "/AppData/Roaming/.dqm-launcher/" + name);
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void runProcess(String command) {
        try {
            Process pro = Runtime.getRuntime().exec(command);
            pro.waitFor();
            pro.destroy();
            pro.destroyForcibly();
            System.out.println(command);
        } catch (Throwable t) {
            System.out.println(command);
            t.printStackTrace();
        }

    }

    public static String getLib(String name){
        return Main.launcherPath + "libraries\\" + name;
    }

    public static String getLibP(String name){
        return getLib(name) + " ";
    }

    public static String get1710Libs(){
        return getLibP("gson-2.2.4.jar") + getLibP("guava-15-0.jar") + getLibP("icu4j-core-mojang-51.2.jar") + getLibP("authlib-1.5.21.jar") + getLibP("realms-1.3.5.jar") + getLibP("codecjorbis-20101023.jar")
                + getLibP("codecwav-20101023.jar") + getLibP("libraryjavasound-20101123.jar") + getLibP("librarylwjglopenal-20100824.jar") + getLibP("soundsystem-20120107.jar") + getLibP("commons-codec-1.9.jar")
                + getLibP("commons-io-2.4.jar") + getLibP("commons-logging-1.1.3.jar") + getLibP("netty-all-4.0.10.Final.jar") + getLibP("vecmath-1.3.1.jar") + getLibP("jinput-2.0.5.jar") + getLibP("jinput-platform-2.0.5-natives-windows.jar")
                + getLibP("jutils-1.0.0.jar") + getLibP("jopt-simple-4.5.jar") + getLibP("trove4j-3.0.3.jar") + getLibP("commons-compress-1.8.1.jar") + getLibP("commons-lang3-3.1.jar") + getLibP("httpclient-4.3.3.jar")
                + getLibP("httpcore-4.3.2.jar") + getLibP("log4j-api-2.0-beta9.jar") + getLibP("log4j-core-2.0-beta9.jar") + getLibP("lwjgl-2.9.1.jar") + getLibP("lwjgl_util-2.9.1.jar") + getLibP("lwjgl-platform-2.9.1-natives-windows.jar")
                + getLibP("twitch-5.16.jar") + getLibP("twitch-external-platform-4.5-natives-windows-64.jar") + getLibP("twitch-platform-5.16-natives-windows-64.jar");
    }

    public static String get1710ForgeLibs(){
        return getLibP("akka-actor_2.11-2.3.3.jar") + getLibP("asm-all-5.0.3.jar") + getLibP("commons-lang3-3.3.2.jar") + getLibP("config-1.2.1.jar") + getLibP("guava-17.0.jar") + getLibP("launchwrapper-1.11.jar")
                + getLibP("lzma-0.0.1.jar") + getLibP("modpack.jar") + getLibP("scala-actors-migration_2.11-1.1.0.jar") + getLibP("scala-compiler-2.11.1.jar") + getLibP("scala-continuations-library_2.11-1.0.2.jar")
                + getLibP("scala-continuations-plugin_2.11.1-1.0.2.jar") + getLibP("scala-library-2.11.1.jar") + getLibP("scala-parser-combinators_2.11-1.0.1.jar") + getLibP("scala-reflect-2.11.1.jar")
                + getLibP("scala-swing_2.11-1.0.1.jar") + getLibP("scala-xml_2.11-1.0.2.jar");
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
                folder.getParentFile().mkdir();
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
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
