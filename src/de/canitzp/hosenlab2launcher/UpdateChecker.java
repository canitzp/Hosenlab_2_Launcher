package de.canitzp.hosenlab2launcher;

public class UpdateChecker {

    public static void checkForUpdate(String username, String password){
        new Update().updatePack(username, password);
    }


}

