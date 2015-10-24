package de.canitzp.hosenlauncher;

public enum ForgeVersion {

    F1448(MinecraftVersion.MC1710, "1448" ,"files.minecraftforge.net/maven/net/minecraftforge/forge/1.7.10-10.13.4.1448-1.7.10/forge-1.7.10-10.13.4.1448-1.7.10-universal.jar");

    private MinecraftVersion mcVersion;
    private String downloadUrl, name;

    ForgeVersion(MinecraftVersion mcVersion, String name, String downloadUrl){
        this.mcVersion = mcVersion;
        this.downloadUrl = downloadUrl;
        this.name = name;
    }

    public MinecraftVersion getMCVersion() {
        return mcVersion;
    }

    public String getDownloadUrl() {
        return "http://" + downloadUrl;
    }

    public MinecraftVersion getMcVersion() {
        return mcVersion;
    }

    public String getName() {
        return name;
    }
}
