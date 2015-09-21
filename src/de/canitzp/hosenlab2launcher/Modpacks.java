package de.canitzp.hosenlab2launcher;

public enum Modpacks {

    LIBS("Libraries", "libraries", "canitzp.de/Modpacks/libs.zip", "1"),
    ASSETS("Assets", "assets" , "canitzp.de/Modpacks/assets.zip", "1"),
    HOSENLAB2("Hosenlab 2", "hosenlab2", "canitzp.de/Modpacks/Hosenlab%202/modpack.zip", "canitzp.de/Modpacks/Hosenlab%202/update.txt"),
    TECHNICUNIVERSE("TechnicUniverse", "technicuniverse", "ni62659_2.fastdownload.nitrado.net/TeamFactionModpack.zip", "null");


    private String displayName, folderName, urlModsAndConfigs, urlVersionTXT;
    Modpacks(String displayName, String folderName, String urlModsAndConfigs, String urlVersionTXT){
        this.displayName = displayName;
        this.folderName = folderName;
        this.urlModsAndConfigs = urlModsAndConfigs;
        this.urlVersionTXT = urlVersionTXT;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getFolderName() {
        return folderName;
    }

    public String getUrlModsAndConfigs() {
        return urlModsAndConfigs;
    }

    public String getUrlVersionTXT() {
        return urlVersionTXT;
    }
}
