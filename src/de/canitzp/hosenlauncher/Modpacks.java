package de.canitzp.hosenlauncher;

public enum Modpacks {

    LIBS("Libraries", "libraries", "canitzp.de/Modpacks/libs.zip", "1", ForgeVersion.F1448, false, null, null, null),
    ASSETS("Assets", "assets" , "canitzp.de/Modpacks/assets.zip", "1", ForgeVersion.F1448, false, null, null, null),
    HOSENLAB2("Hosenlab 2", "hosenlab2", "canitzp.de/Modpacks/Hosenlab%202/modpack.zip", "canitzp.de/Modpacks/Hosenlab%202/update.txt", ForgeVersion.F1448, true, "canitzp.de/Modpacks/Hosenlab%202/server.zip", "canitzp.de/Modpacks/Hosenlab%202/desc.txt", "canitzp.de/Modpacks/Hosenlab%202/changelog.txt"),
    TECHNICUNIVERSE("TechnicUniverse", "technicuniverse", "ni62659_2.fastdownload.nitrado.net/TeamFactionModpack.zip", "ni62659_2.fastdownload.nitrado.net/Version.txt", ForgeVersion.F1448, true, "ni62659_2.fastdownload.nitrado.net/ServerdatenTeamFaction.zip", "ni62659_2.fastdownload.nitrado.net/Description.txt", "ni62659_2.fastdownload.nitrado.net/Changelog.txt"),
    JOURNEYMAP("Journey Map", "1.7.10-5.1.0-unlimited", "minecraft.curseforge.com/mc-mods/32274-journeymap-32274/files/2245168/download", "1.7.10-5.1.0-unlimited", ForgeVersion.F1448, false, null, null, null);

    private final ForgeVersion forgeVersion;
    private final boolean jmap;
    private String displayName, folderName, urlModsAndConfigs, urlVersionTXT, serverLink, descLink, changelog;

    Modpacks(String displayName, String folderName, String urlModsAndConfigs, String urlVersionTXT, ForgeVersion forgeVersion, boolean jmap, String serverLink, String descLink, String changelog){
        this.displayName = displayName;
        this.folderName = folderName;
        this.urlModsAndConfigs = urlModsAndConfigs;
        this.urlVersionTXT = urlVersionTXT;
        this.forgeVersion = forgeVersion;
        this.jmap = jmap;
        this.serverLink = serverLink;
        this.descLink = descLink;
        this.changelog = changelog;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getFolderName() {
        return folderName;
    }

    public String getUrlModsAndConfigs() {
        return "http://" + urlModsAndConfigs;
    }

    public String getUrlVersionTXT() {
        return "http://" + urlVersionTXT;
    }

    public ForgeVersion getForgeVersion() {
        return forgeVersion;
    }

    public boolean containsJmap() {
        return jmap;
    }

    public String getServerLink() {
        return serverLink;
    }

    public String getDescLink() {
        return descLink;
    }

    public String getChangelog() {
        return changelog;
    }
}
