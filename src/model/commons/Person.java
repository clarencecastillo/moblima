package model.commons;

public abstract class Person extends Entity {

    protected String firstName;
    protected String lastName;

    public Person(String fullName) {
        String[] splitName = fullName.split(" ");
        if (splitName.length == 1)
            firstName = fullName;
        else {
            firstName = splitName[0];
            lastName = splitName[1];
        }
    }

    public Person(String firstName, String lastName) {
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
        return this.firstName + " " + this.lastName;
    }

    protected void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    protected void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return getFullName();
    }
}
