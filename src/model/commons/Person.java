package model.commons;

import java.util.UUID;

public abstract class Person extends Entity {

    protected String firstName;
    protected String lastName;

    public Person(String firstName, String lastName) {
        super(UUID.randomUUID());
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return this.firstName + this.lastName;
    }

    protected void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    protected void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
