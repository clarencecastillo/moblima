package model.cinema;

import model.commons.User;

public class Staff extends User {

    private String username;
    private String password;

    public Staff(String firstName, String lastName, String mobile,
                 String email, String username, String password) {
        super(firstName, lastName, mobile, email);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
