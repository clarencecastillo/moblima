package model.movie;

public enum MovieRating {

    G("General"),
    PG("Parental Guidance"),
    PG13("Parental Guidance 13"),
    NC16("No Children Under 16"),
    M18("Mature 18"),
    R21("Restricted 21");

    private String name;

    MovieRating(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
