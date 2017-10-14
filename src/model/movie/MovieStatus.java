package model.movie;

public enum MovieStatus {

    COMING_SOON("Coming Soon"),
    PREVIEW("Preview"),
    NOW_SHOWING("Now Showing"),
    END_OF_SHOWING("End of Showing");

    private String name;

    MovieStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
