package de.canitzp.hosenlab2launcher;

import java.util.Objects;

public class UpdateChecker {

    public static void checkForUpdate(String modpack){
        if(Objects.equals(modpack, Modpacks.HOSENLAB2.getDisplayName())){
            new Update().updatePack(Modpacks.HOSENLAB2);
        } else if(Objects.equals(modpack, Modpacks.TECHNICUNIVERSE.getDisplayName())){
            new Update().updatePack(Modpacks.TECHNICUNIVERSE);
        }
        else {
            Main.window.addToTextArea("NO MODPACK FOUND!");
        }



    }


}

