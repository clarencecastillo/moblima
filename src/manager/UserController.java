package manager;

import exception.InvalidRegisterEmailException;
import exception.InvalidRegisterMobileException;
import exception.InvalidRegisterUsernameException;
import exception.UninitialisedSingletonException;
import model.cinema.Staff;
import model.commons.User;

public class UserController extends EntityController<User> {

    private static UserController instance;

    private UserController() {
        super();
    }

    public static void init() {
        instance = new UserController();
    }

    public static UserController getInstance() {
        if (instance == null)
            throw new UninitialisedSingletonException();
        return instance;
    }

    // Create user after capturing user information when they make bookings
    public User registerUser(String firstName, String lastName, String mobile, String email)
            throws InvalidRegisterEmailException, InvalidRegisterMobileException {

        if (findByEmail(email) != null)
            throw new InvalidRegisterEmailException("Registered email already exists");

        if (findByMobile(mobile) != null)
            throw new InvalidRegisterMobileException("Registered phone number already exists");

        User user = new User(firstName, lastName, mobile, email);
        entities.put(user.getId(), user);
        return user;
    }

    public void registerStaff(String firstName, String lastName, String mobile, String email,
                              String username, String password) throws InvalidRegisterEmailException,
            InvalidRegisterMobileException, InvalidRegisterUsernameException {

        if (findByEmail(email) != null)
            throw new InvalidRegisterEmailException("Registered email already exists");

        if (findByMobile(mobile) != null)
            throw new InvalidRegisterMobileException("Registered phone number already exists");

        if (findByUsername(username) != null)
            throw new InvalidRegisterUsernameException("Registered username alreadye exists");

        Staff staff = new Staff(firstName, lastName, mobile, email, username, password);
        entities.put(staff.getId(), staff);
    }

    public User findByMobile(String mobile) {
        for (User user : entities.values()) {
            if (user.getMobile().equals(mobile))
                return user;
        }
        return null;
    }

    public User findByEmail(String email) {
        for (User user : entities.values()) {
            if (user.getEmail().equals(email))
                return user;
        }
        return null;
    }

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

    public boolean login(String username, String password) {
        Staff staff = findByUsername(username);
        return staff != null && staff.getPassword().equals(password);
    }
}
