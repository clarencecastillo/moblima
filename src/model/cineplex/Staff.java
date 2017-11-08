package model.cineplex;

import model.commons.User;

/**
 * Represents a cineplex staff who has the admin right.
 *
 * @version 1.0
 * @since 2017-10-20
 */
public class Staff extends User {

    /**
     * The login username of this staff.
     */
    private String username;

    /**
     * The login password of this staff.
     */
    private String password;

    /**
     * Creates a staff with the given first name, last name, mobile number, email address, login username and login password.
     *
     * @param firstName This's staff's first name.
     * @param lastName  This's staff's last name.
     * @param mobile    This's staff's mobile number.
     * @param email     This's staff's email address.
     * @param username  This's staff's login username.
     * @param password  This's staff's login password.
     */
    public Staff(String firstName, String lastName, String mobile,
                 String email, String username, String password) {
        super(firstName, lastName, mobile, email);
        this.username = username;
        this.password = password;
    }

    /**
     * Gets this staff's login username.
     *
     * @return this staff's login username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Changes this staff's login username.
     *
     * @param username This staff's new login username.
     */
    public void setUsername(String username) {

        this.username = username;
    }

    /**
     * Gets this staff's login password.
     *
     * @return this staff's login password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Changes this staff's login password.
     *
     * @param password This staff's new login password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
