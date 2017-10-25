package model.commons;

/**
 * Represents a person.
 *
 * @author Castillo Clarence Fitzgerald Gumtang
 * @version 1.0
 * @since 2017-10-20
 */
public abstract class Person extends Entity {

    /**
     * The first name of this person.
     */
    protected String firstName;

    /**
     * The last name of this person.
     */
    protected String lastName;

    /**
     * Creates a person with the full name.
     * The name should include both first name and last name.
     *
     * @param fullName This person's full name.
     */
    public Person(String fullName) {
        String[] splitName = fullName.split(" ");
        if (splitName.length == 1)
            firstName = fullName;
        else {
            firstName = splitName[0];
            lastName = splitName[1];
        }
    }

    /**
     * Creates a person with the first name and last name.
     *
     * @param firstName This person's first name.
     * @param lastName  This person's last name.
     */
    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Gets this person's first name.
     *
     * @return this person's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Changes this person's first name.
     *
     * @param firstName This person's new first name.
     */
    protected void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets this person's last name.
     *
     * @return this person's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Changes this person's last name.
     *
     * @param lastName This person's new last name.
     */
    protected void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets this person's full name consisting first name and last name.
     *
     * @return
     */
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    /**
     * Gets this person's fullname.
     *
     * @return this person's fullname.
     */
    @Override
    public String toString() {
        return getFullName();
    }
}
