package model.movie;

import model.commons.Person;

/**
 Represents a movie-related person who can be a director or an actor.
 @author Castillo Clarence Fitzgerald Gumtang
 @version 1.0
 @since 2017-10-20
 */
public class MoviePerson extends Person {

    /**
     * Creates a movie person by a given full name.
     * @param fullName The full name of this movie person.
     */
    public MoviePerson(String fullName) {
        super(fullName);
    }

    /**
     * Creates a movie person by a given first name and last name.
     * @param firstName The first name of this movie person.
     * @param lastName The last name of this movie person.
     */
    public MoviePerson(String firstName, String lastName) {
        super(firstName, lastName);
    }
}
