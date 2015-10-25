package de.canitzp.hosenlauncher.util;

/**
 * @author AtomSponge
 */
public enum OperatingSystem {
    WINDOWS("win"),
    UNIX("nix", "nux"),
    MAC("mac"),
    UNKNOWN;

    private static final OperatingSystem[] VALUES;
    static {
        VALUES = values();
    }

    private final String[] keywords;

    OperatingSystem(String... keywords) {
        this.keywords = keywords;
    }

    public static OperatingSystem getByName(String input) {
        for (OperatingSystem system : VALUES) {
            if (system.equals(UNKNOWN)) {
                continue;
            }

            for (String keyword : system.keywords) {
                if (input.toLowerCase().contains(keyword)) {
                    return system;
                }
            }
        }
        return UNKNOWN;
    }

    public static OperatingSystem getCurrentSystem() {
        final String system = System.getProperty("os.name").toLowerCase();
        return getByName(system);
    }
}
