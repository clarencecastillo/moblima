package exception;

public class InvalidRegisterEmailException extends Exception {
    public InvalidRegisterEmailException() {
        super("Invalid Email");
    }

    public InvalidRegisterEmailException(String message) {
        super(message);
    }
}
