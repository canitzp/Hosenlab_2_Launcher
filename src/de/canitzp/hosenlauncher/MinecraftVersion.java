package de.canitzp.hosenlauncher;

public enum  MinecraftVersion {

    MC1710("1.7.10", "https://s3.amazonaws.com/Minecraft.Download/versions/1.7.10/1.7.10.jar", "https://s3.amazonaws.com/Minecraft.Download/versions/1.7.10/minecraft_server.1.7.10.jar"),
    MC18("1.8", "https://s3.amazonaws.com/Minecraft.Download/versions/1.8/1.8.jar", "https://s3.amazonaws.com/Minecraft.Download/versions/1.8/minecraft_server.1.8.jar");

    private String downloadClientUrl, downloadServerUrl, name;

    MinecraftVersion(String name, String downloadClientUrl, String downloadServerUrl){
        this.downloadClientUrl = downloadClientUrl;
        this.downloadServerUrl = downloadServerUrl;
        this.name = name;
    }

    public String getDownloadClientUrl() {
        return downloadClientUrl;
    }

    public String getDownloadServerUrl() {
        return downloadServerUrl;
    }

    public String getName() {
        return name;
    }
}
