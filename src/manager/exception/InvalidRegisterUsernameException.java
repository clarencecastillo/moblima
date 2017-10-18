package manager.exception;

public class InvalidRegisterUsernameException extends Exception {
    public InvalidRegisterUsernameException() {
        super("Invalid Username");
    }

    public InvalidRegisterUsernameException(String message) {
        super(message);
    }
}
