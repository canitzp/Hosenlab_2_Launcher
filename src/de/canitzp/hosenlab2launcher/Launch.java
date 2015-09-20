package de.canitzp.hosenlab2launcher;

import com.google.gson.GsonBuilder;
import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import de.canitzp.hosenlab2launcher.MINECRAFTFORGE.OldPropertyMapSerializer;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Launch {

    private static String natives;
    private static String libs;
    private static String mainClass = "net.minecraft.launchwrapper.Launch";
    private static String gameDir = Main.launcherPath.getAbsolutePath() + "";
    private static String assetsDir;
    private static String tweakClass = "cpw.mods.fml.common.launcher.FMLTweaker";
    private static String username;
    private static String password;
    private static Map<String, String> map = new HashMap<>();

    public static int launchGame(String user, String passcode, Modpacks modpack){
        username = user;
        password = passcode;
        gameDir = Main.launcherPath.getAbsolutePath()  + "/Modpacks/" + modpack.getFolderName();
        natives = Main.launcherPath.getAbsolutePath() + "/Modpacks/" + modpack.getFolderName() + "/libraries/natives";
        libs = FileManager.get1710Libs(modpack) + FileManager.get1710ForgeLibs();
        assetsDir = Main.launcherPath.getAbsolutePath() + "/Modpacks/" + modpack.getFolderName() + "/assets";
        if(requestUser()){
            try{
                FileManager.runProcess(commandCreator(), Main.launcherPath.getAbsolutePath() + "\\Modpacks\\" + modpack.getFolderName());
                Main.window.addToTextArea("Username and Password accepted!\nStarting Minecraft " + modpack.getDisplayName() + ".");
            } catch (Throwable e){
                return -1;
            }
        } else {
            Main.restart("Wrong Username or Password!");
            return 1;
        }
         return 0;
    }

    private static boolean requestUser(){
        YggdrasilUserAuthentication authentication = (YggdrasilUserAuthentication)(new YggdrasilAuthenticationService(Proxy.NO_PROXY, "1")).createUserAuthentication(Agent.MINECRAFT);
        authentication.setUsername(username);
        authentication.setPassword(password);
        try {
            authentication.logIn();
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return false;
        }
        map.put("accessToken", authentication.getAuthenticatedToken());
        map.put("uuid", authentication.getSelectedProfile().getId().toString().replace("-", ""));
        map.put("username", authentication.getSelectedProfile().getName());
        map.put("userType", authentication.getUserType().getName());
        map.put("userProperties", (new GsonBuilder()).registerTypeAdapter(PropertyMap.class, new OldPropertyMapSerializer()).create().toJson(authentication.getUserProperties()));
        return true;
    }

    private static String commandCreator(){
        String command = "";
        ArrayList<String> list = new ArrayList<>();
        list.add("\"" + Main.java.replace("\"", "") + "\"");
        list.add(" -XX:HeapDumpPath=MojangTricksIntelDriversForPerformance_javaw.exe_minecraft.exe.heapdump");
        list.add(" -Xmx" + Main.maxRam + "M");
        list.add(" -XX:+UseConcMarkSweepGC -XX:+CMSIncrementalMode -XX:-UseAdaptiveSizePolicy -Xmn512M");
        list.add(" -Djava.library.path=\"" + natives.replace("/", "\\") + "\"");
        list.add(" -cp " + libs.replace("/", "\\"));
        list.add(" " + mainClass);
        list.add(" --accessToken " + map.get("accessToken"));
        list.add(" --assetsDir \"" + assetsDir.replace("/", "\\") + "\"");
        list.add(" --assetIndex 1.7.10");
        list.add(" --gameDir \"" + gameDir.replace("/", "\\") + "\"");
        list.add(" --version 1.7.10");
        list.add(" --username " + map.get("username"));
        list.add(" --userProperties " + map.get("userProperties"));
        list.add(" --userType " + map.get("userType"));
        list.add(" --uuid " + map.get("uuid"));
        list.add(" --tweakClass " + tweakClass);
        //list.add(" --debug");

        for(String s : list){
            command += s;
        }
        return command;
    }
}