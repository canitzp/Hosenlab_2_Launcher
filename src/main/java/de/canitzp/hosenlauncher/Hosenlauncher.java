package de.canitzp.hosenlauncher;

import com.typesafe.config.Config;
import de.canitzp.hosenlauncher.gui.Gui;
import de.canitzp.hosenlauncher.util.ConfigUtils;
import de.canitzp.hosenlauncher.util.OperatingSystem;
import de.canitzp.hosenlauncher.util.Utf8Control;
import javafx.application.Platform;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author AtomSponge
 */
public class Hosenlauncher {
    private static Hosenlauncher INSTANCE;

    public static void setInstance(Hosenlauncher hosenlauncher) {
        assert INSTANCE == null;
        INSTANCE = hosenlauncher;
    }

    public static Hosenlauncher getInstance() {
        return INSTANCE;
    }

    @Getter
    private final Logger logger = LogManager.getLogger(getClass());

    @Getter
    private File dataDirectory;
    @Getter
    private File configFile;
    @Getter
    @Setter
    private Config config;

    @Getter
    @Setter
    private Gui gui;

    @Getter
    private ResourceBundle formsBundle;
    @Getter
    private ResourceBundle messagesBundle;

    @Getter
    @Setter
    private Map<String, Object> yggdrasilData;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void init(final String[] args) {
        dataDirectory = getApplicationDataDirectory();
        if (!dataDirectory.exists()) {
            dataDirectory.mkdirs();
        }

        configFile = new File(dataDirectory, "settings.conf");
        try {
            logger.info("Loading settings");
            config = ConfigUtils.load(getClass().getClassLoader(), "settings.conf", configFile);
        } catch (IOException e) {
            logger.error("Failed to load config", e);
            return;
        }

        if (config.getBoolean("debug")) {
            System.getProperties().put("debug", true);
        }

        String lang = config.getString("lang");
        logger.info("Setting locale to {}", lang);
        Locale.setDefault(new Locale(lang));

        logger.info("Loading resource bundles");
        formsBundle = ResourceBundle.getBundle("lang.forms", Utf8Control.INSTANCE);
        messagesBundle = ResourceBundle.getBundle("lang.messages", Utf8Control.INSTANCE);

        // TODO: Check for launcher updates

        Gui.initialize(args);
    }

    public void exit() {
        Platform.exit();
    }

    private File getApplicationDataDirectory() {
        if (Boolean.getBoolean("debug")) {
            return new File("launcher");
        }

        final String userHome = System.getProperty("user.home");
        final OperatingSystem system = OperatingSystem.getCurrentSystem();
        switch (system) {
            case WINDOWS:
                return new File(userHome + "\\AppData\\Roaming\\hosenlauncher");
            case UNIX:
                return new File(userHome + "/.config/hosenlauncher");
            case MAC:
                return new File(userHome + "/Library/Preferences/hosenlauncher");
            default:
                return new File("launcher");
        }
    }
}
