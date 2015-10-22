package de.canitzp.hosenlauncher;

import com.google.common.collect.Maps;
import com.google.gson.*;
import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import javafx.application.Platform;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class Launch {

    private static Map map = Variables.loginMap;
    private static String natives, mainClass = "net.minecraft.launchwrapper.Launch", gameDir, assetsDir, tweakClass = "cpw.mods.fml.common.launcher.FMLTweaker", libs;

    public static void launchGame(String username, String password, Modpacks modpack) {
        System.out.println("Starting Game");
        gameDir = Variables.launcherPath.getAbsolutePath() + File.separator + "Modpacks" + File.separator + modpack.getFolderName();
        assetsDir = gameDir + File.separator + "assets";
        natives = gameDir + File.separator + "libraries" + File.separator + "natives";
        libs = FileManager.get1710Libs(modpack) + FileManager.get1710ForgeLibs(modpack);
        Variables.mainController.addToLog("Das Spiel wird in ein paar Sekunden gestartet!");
        Variables.save.saveVariables();
        System.out.println(commandCreator());
        Variables.launchThread = new Thread(() -> {
            try {
                Runtime.getRuntime().exec(commandCreator(), null, new File(gameDir));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Variables.launchThread.setDaemon(true);
        Variables.launchThread.start();
        Main.close();
    }

    private static String commandCreator(){
        String command = "";
        ArrayList<String> list = new ArrayList<String>();
        list.add("\"java\"");
        list.add(" -XX:HeapDumpPath=MojangTricksIntelDriversForPerformance_javaw.exe_minecraft.exe.heapdump");
        list.add(" -Xmx" + Variables.maxRam + "M");
        list.add(" -XX:+UseConcMarkSweepGC -XX:+CMSIncrementalMode -XX:-UseAdaptiveSizePolicy -Xmn" + Variables.minRam + "M");
        list.add(" -Djava.library.path=\"" + natives + "\"");
        list.add(" -cp " + libs);
        list.add(" " + mainClass);
        list.add(" --accessToken " + map.get("accessToken"));
        list.add(" --assetsDir \"" + assetsDir + "\"");
        list.add(" --assetIndex 1.7.10");
        list.add(" --gameDir \"" + gameDir + "\"");
        list.add(" --version 1.7.10");
        list.add(" --username " + map.get("displayName"));
        list.add(" --userProperties " + map.get("prop"));
        list.add(" --userType " + map.get("userType"));
        list.add(" --uuid " + map.get("uuid"));
        list.add(" --tweakClass " + tweakClass);

        for(String s : list){
            command += s;
        }
        return command;
    }


    public static class OldPropertyMapSerializer implements JsonSerializer<PropertyMap> {
        public OldPropertyMapSerializer() {
        }

        public JsonElement serialize(PropertyMap var1, Type var2, JsonSerializationContext var3) {
            JsonObject var4 = new JsonObject();
            Iterator var5 = var1.keySet().iterator();

            while(var5.hasNext()) {
                String var6 = (String)var5.next();
                JsonArray var7 = new JsonArray();
                Iterator var8 = var1.get(var6).iterator();

                while(var8.hasNext()) {
                    Property var9 = (Property)var8.next();
                    var7.add(new JsonPrimitive(var9.getValue()));
                }

                var4.add(var6, var7);
            }

            return var4;
        }
    }

}
