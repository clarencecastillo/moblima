package exception;

public class InvalidReviewRatingException extends Exception {
    public InvalidReviewRatingException() {
        super("Invalid Review Rating.");
    }
}
