package view;

import exception.RejectedNavigationException;
import manager.MovieController;
import manager.MovieReviewController;
import manager.UserController;
import model.commons.User;
import model.movie.Movie;
import model.movie.MovieReview;
import view.ui.*;

import java.util.UUID;

/**
 * This view displays the user interface for the user to enter movie reviews.
 *
 * @version 1.0
 * @since 2017-10-30
 */
public class MovieReviewMenuView extends MenuView {

    private AccessLevel accessLevel;
    private MovieReviewMenuIntent intent;

    private User user;
    private Movie movie;
    private MovieReview movieReview;

    private MovieReviewController movieReviewController;
    private UserController userController;
    private MovieController movieController;

    public MovieReviewMenuView(Navigation navigation) {
        super(navigation);
        this.movieReviewController = MovieReviewController.getInstance();
        this.userController = UserController.getInstance();
        this.movieController = MovieController.getInstance();
    }

    @Override
    public void onLoad(AccessLevel accessLevel, Intent intent, String... args) {

        this.accessLevel = accessLevel;
        switch (accessLevel) {
            case ADMINISTRATOR:
                setMenuItems(MovieReviewMenuOption.DELETE_REVIEW);
                break;
            case PUBLIC:
                setMenuItems(MovieReviewMenuOption.REPORT_REVIEW);
                break;
        }

        this.intent = (MovieReviewMenuIntent) intent;
        switch (this.intent) {

            case VIEW_REVIEW:
                movieReview = movieReviewController.findById(UUID.fromString(args[0]));
                if (movieReview == null) {
                    View.displayError("Movie Review not found!");
                    Form.pressAnyKeyToContinue();
                    throw new RejectedNavigationException();
                }
                break;
            case WRITE_REVIEW:

                movie = movieController.findById(UUID.fromString(args[0]));
                if (movie == null) {
                    View.displayError("Movie not found!");
                    Form.pressAnyKeyToContinue();
                    throw new RejectedNavigationException();
                }

                View.displayInformation("Please enter your mobile number.");
                String mobileNumber = Form.getString("Mobile number");
                user = userController.findByMobile(mobileNumber);
                if (user == null) {
                    View.displayError("User with mobile '" + mobileNumber + "' not found!");
                    Form.pressAnyKeyToContinue();
                    throw new RejectedNavigationException();
                }

                View.displayInformation("Please enter review details.");
                String review = Form.getString("Review");
                int rating = Form.getInt("Rating", 1, 5);

                movieReview = movieReviewController.createReview(review, rating, movie.getId(), user.getId());
                View.displaySuccess("Successfully submitted movie review!");
                Form.pressAnyKeyToContinue();
                break;
        }

        MovieReviewView movieReviewView = new MovieReviewView(movieReview);
        setTitle(movieReviewView.getTitle());
        setContent(movieReviewView.getContent());
        addBackOption();
    }

    @Override
    public void onEnter() {
        display();
        String userInput = getChoice();
        if (userInput.equals(BACK))
            navigation.goBack();
        else {
            MovieReviewMenuOption userChoice = MovieReviewMenuOption.valueOf(userInput);
            switch (userChoice) {
                case DELETE_REVIEW:
                    movieReviewController.removeReview(movieReview.getId());
                    View.displaySuccess("Successfully deleted movie review!");
                    Form.pressAnyKeyToContinue();
                    navigation.goBack();
                    break;
                case REPORT_REVIEW:
                    View.displaySuccess("Report submitted! Thank you for making MOBLIMA a safer place.");
                    Form.pressAnyKeyToContinue();
                    navigation.goBack();
                    break;
            }
        }
    }

    public enum MovieReviewMenuIntent implements Intent {
        VIEW_REVIEW,
        WRITE_REVIEW
    }

    public enum MovieReviewMenuOption implements EnumerableMenuOption {

        DELETE_REVIEW("Delete Review"),
        REPORT_REVIEW("Report");

        private String description;
        MovieReviewMenuOption(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }
}
