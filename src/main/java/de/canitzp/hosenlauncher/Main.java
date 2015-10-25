package de.canitzp.hosenlauncher;

import java.io.File;

/**
 * @author AtomSponge
 */
public class Main {
    public static void main(String[] args) {
        System.setProperty("launcher.path", new File(".").getAbsolutePath()); // Logback doesn't like relative paths

        Hosenlauncher launcher = new Hosenlauncher();
        Hosenlauncher.setInstance(launcher);
        launcher.init(args);
    }
}
