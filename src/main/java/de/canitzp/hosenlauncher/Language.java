package de.canitzp.hosenlauncher;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Locale;

/**
 * @author AtomSponge
 */
@RequiredArgsConstructor
@Getter
public enum Language {
    ENGLISH("en", Locale.ENGLISH),
    GERMAN("de", Locale.GERMAN);

    private static final String LOCALIZATION_KEY_PREFIX = "language.";
    private static final Language[] VALUES = values();

    private final String key;
    private final Locale locale;

    public String getLocalizationKey() {
        return LOCALIZATION_KEY_PREFIX + name().toLowerCase();
    }

    public String getLocalizedName() {
        return Hosenlauncher.getInstance().getMessagesBundle().getString(getLocalizationKey());
    }

    public static Language getByKey(String key) {
        for (Language language : VALUES) {
            if (language.key.equalsIgnoreCase(key)) {
                return language;
            }
        }
        return null;
    }
}
