package model.commons;

import view.ui.EnumerableMenuOption;

/**
 * Represents a standard set of languages for movies.
 *
 * @author Castillo Clarence Fitzgerald Gumtang
 * @version 1.0
 * @since 2017-10-20
 */

public enum Language implements EnumerableMenuOption {

    /**
     * Language English.
     */
    ENGLISH("English"),

    /**
     * Language Chinese.
     */
    CHINESE("Chinese"),

    /**
     * Language Tamil.
     */
    TAMIL("Tamil"),

    /**
     * Language Malay.
     */
    MALAY("Malay"),

    /**
     * Unavailable Language.
     */
    NONE("None");

    /**
     * The name of the Language.
     */
    private String name;

    /**
     * Creates the language with the given language name.
     *
     * @param name this Language's new name.
     */
    Language(String name) {
        this.name = name;
    }

    /**
     * Gets the name of this language.
     *
     * @return this Language's name.
     */
    @Override
    public String toString() {
        return name;
    }

    // TODO Javadoc
    @Override
    public String getDescription() {
        return name;
    }
}
