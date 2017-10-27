package manager;

import exception.*;
import model.cinema.Staff;
import model.commons.User;

/**
 Represents the controller of users, including the moviegoer and cinema staff.
 @author Castillo Clarence Fitzgerald Gumtang
 @version 1.0
 @since 2017-10-20
 */
public class UserController extends EntityController<User> {

    /**
     * A reference to this singleton instance.
     */
    private static UserController instance;

    /**
     * Creates the cineplex controller.
     */
    private UserController() {
        super();
    }

    /**
     * Initialize the cineplex controller.
     */
    public static void init() {
        instance = new UserController();
    }

    /**
     * Gets this User Controller's singleton instance.
     * @return this User Controller's singleton instance.
     */
    public static UserController getInstance() {
        if (instance == null)
            throw new UninitialisedSingletonException();
        return instance;
    }

    /**
     * Creates a user after capturing user information when they make bookings.
     * @param firstName The first name of this user.
     * @param lastName The last name of this user.
     * @param mobile The mobile number of this user.
     * @param email The email address of this user.
     * @return the newly created user.
     * @throws IllegalActionException if the email is already registered by another user,
     * or if the mobile phone is already registered by another user.
     */
    public User registerUser(String firstName, String lastName, String mobile, String email)
            throws IllegalActionException {

        if (findByMobile(mobile) != null)
            throw new IllegalActionException("This mobile number has already been registered.");

        User user = new User(firstName, lastName, mobile, email);
        entities.put(user.getId(), user);
        return user;
    }

    /**
     * Creates a staff with the given staff information.
     * @param firstName The first name of this staff.
     * @param lastName The last name of this staff.
     * @param mobile The mobile number of this staff.
     * @param email The email address of this staff.
     * @param username The log in username of this staff.
     * @param password The log in password of this staff.
     * @throws IllegalActionException if the email is already registered by another user,
     * orif the mobile phone is already registered by another user,
     * or if the username is already registered by another user.
     */
    public void registerStaff(String firstName, String lastName, String mobile, String email,
                              String username, String password) throws IllegalActionException {

        if (findByMobile(mobile) != null)
            throw new IllegalActionException("This mobile number has already been registered.");

        if (findByUsername(username) != null)
            throw new IllegalActionException("This username already exists");

        Staff staff = new Staff(firstName, lastName, mobile, email, username, password);
        entities.put(staff.getId(), staff);
    }

    /**
     * Finds a user by the given mobile number.
     * @param mobile The mobile number of the user to be searched for.
     * @return the user who registers with this mobile number.
     */
    public User findByMobile(String mobile) {
        for (User user : entities.values()) {
            if (user.getMobile().equals(mobile))
                return user;
        }
        return null;
    }

    /**
     * Finds a staff by the given username.
     * @param username The username of the staff to be searched for.
     * @return the staff who registers with this username.
     */
    public Staff findByUsername(String username) {
        for (User registeredUser : entities.values()) {
            if (registeredUser instanceof Staff) {
                Staff staff = (Staff) registeredUser;
                if (staff.getUsername().equals(username))
                    return staff;
            }
        }
        return null;
    }

    /**
     * Checks whether the login information is valid.
     * @param username The username to be checked.
     * @param password The password to be checked.
     * @return true if the username exists and the password matches.
     */
    public boolean login(String username, String password) {
        Staff staff = findByUsername(username);
        return staff != null && staff.getPassword().equals(password);
    }
}
