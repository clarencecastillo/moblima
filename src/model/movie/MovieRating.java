package model.movie;

public enum MovieRating {

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
}
