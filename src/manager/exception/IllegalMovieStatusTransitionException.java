package manager.exception;

public class IllegalMovieStatusTransitionException extends Exception {
    public IllegalMovieStatusTransitionException(String message) {
        super(message);
    }

    public IllegalMovieStatusTransitionException() {
        super("Illegal Movie Status Transition.");
    }
}
