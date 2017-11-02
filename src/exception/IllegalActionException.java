package exception;

/**
 Signals that an illegal action is taken by the user.
 @version 1.0
 @since 2017-10-20
 */
public class IllegalActionException extends RuntimeException {
    /**
     * Constructs a IllegalActionException with the specified message.
     * @param message The specified message to be displayed for this IllegalActionException.
     */
    public IllegalActionException(String message) {
        super(message);
    }
}
