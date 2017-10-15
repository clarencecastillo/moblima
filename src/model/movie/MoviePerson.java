package model.movie;

import model.commons.Person;

// Director and Cast
public class MoviePerson extends Person {

    private String biography;

    public MoviePerson(String firstName, String lastName, String biography) {
        super(firstName, lastName);
        this.biography = biography;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }
}
