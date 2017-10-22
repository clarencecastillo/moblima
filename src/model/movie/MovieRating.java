package model.movie;

import view.ui.EnumerableMenuOption;

public enum MovieRating implements EnumerableMenuOption {

    G("G"),
    PG("PG"),
    PG13("PG13"),
    NC16("NC16"),
    M18("M18"),
    R21("R21");

    private String name;

    MovieRating(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String getDescription() {
        return name;
    }
}
