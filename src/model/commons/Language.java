package model.commons;

import view.ui.EnumerableMenuOption;

/**
 * Represents a standard set of languages for movies.
 *
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
    MALAY("Malay");

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

    /**
     * Gets the description of this language.
     * @return the description of this language.
     */
    @Override
    public String getDescription() {
        return name;
    }
}
