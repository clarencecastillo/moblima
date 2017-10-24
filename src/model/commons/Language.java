package model.commons;

/**
 Represents option of languages
 @author Castillo Clarence Fitzgerald Gumtang
 @version 1.0
 @since 2017-10-20
 */

public enum Language {

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
     *
     */
    private String name;

    Language(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
