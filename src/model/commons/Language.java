package model.commons;

public enum Language {

    ENGLISH("English"),
    CHINESE("Chinese"),
    TAMIL("Tamil"),
    MALAY("Malay"),
    NONE("None");

    private String name;

    Language(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
