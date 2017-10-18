package manager.exception;

public class IllegalMovieStatusException extends Exception {
    public IllegalMovieStatusException() {
        super("Illegal Movie Status.");
    }

    public IllegalMovieStatusException(String message) {
        super(message);
    }
}
