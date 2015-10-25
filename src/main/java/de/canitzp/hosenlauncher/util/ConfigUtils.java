package de.canitzp.hosenlauncher.util;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigRenderOptions;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author AtomSponge
 */
public class ConfigUtils {
    private static final ConfigRenderOptions RENDER_OPTIONS = ConfigRenderOptions.defaults().setJson(false).setOriginComments(false);

    /**
     * Loads a {@link com.typesafe.config.Config} from a specified {@link java.io.File}.
     * Creates the file with the default values if it does not exist.
     * If a value does not exist in the file, the value from the default config will be added to the file.
     *
     * @param loader The class loader
     * @param resource The path to the default config resource
     * @param file The file
     * @return The loaded config
     * @throws java.io.IOException
     */
    public static Config load(ClassLoader loader, String resource, File file) throws IOException {
        Config defaults = ConfigFactory.parseResources(loader, resource);
        Config config = ConfigFactory.parseFile(file).withFallback(defaults);

        try (PrintWriter printWriter = new PrintWriter(file)) {
            String[] lines = config.root().render(RENDER_OPTIONS).split(System.lineSeparator());
            for (int i = 0; i < lines.length; i++) {
                lines[i] = lines[i].replace("    ", "  ");
            }
            printWriter.write(StringUtils.join(lines, System.lineSeparator()));
        }
        return config;
    }

    public static void save(Config config, File file) throws FileNotFoundException {
        try (PrintWriter printWriter = new PrintWriter(file)) {
            printWriter.write(config.root().render(RENDER_OPTIONS));
        }
    }
}
