package view;

import exception.RejectedNavigationException;
import manager.MovieController;
import model.movie.Movie;
import view.ui.*;

import java.util.UUID;
import java.util.stream.Collectors;

public class MovieReviewListView extends ListView {

    private Movie movie;

    private AccessLevel accessLevel;
    private MovieController movieController;

    public MovieReviewListView(Navigation navigation) {
        super(navigation);
        this.movieController = MovieController.getInstance();
    }

    @Override
    public void onLoad(AccessLevel accessLevel, Intent intent, String... args) {
        this.accessLevel = accessLevel;
        switch (accessLevel) {
            case ADMINISTRATOR:
                break;
            case PUBLIC:
                setMenuItems(MovieReviewMenuOption.WRITE_REVIEW);
                break;
        }

        if (args.length >= 1 && args[0] != null) {
            movie = movieController.findById(UUID.fromString(args[0]));
            if (movie == null) {
                View.displayError("Movie not found!");
                Form.pressAnyKeyToContinue();
                throw new RejectedNavigationException();
            }
        }

        setTitle("Reviews for " + new MovieView(movie).getTitle());
        setContent("The views shared reflects the personal opinion of the respective movie goers " +
                "and are not a representation of MOBLIMA.");
        addBackOption();
    }

    @Override
    public void onEnter() {

        setViewItems(movie.getReviews().stream().map(movieReview ->
                new ViewItem(new MovieReviewView(movieReview),
                        movieReview.getId().toString())).collect(Collectors.toList()));

        display();
        String userInput = getChoice();
        if (userInput.equals(BACK))
            navigation.goBack();
        else
            try {
                MovieReviewMenuOption userChoice = MovieReviewMenuOption.valueOf(userInput);
                switch (userChoice) {
                    case WRITE_REVIEW:
                        navigation.goTo(new MovieReviewMenuView(navigation), accessLevel,
                                MovieReviewMenuView.MovieReviewMenuIntent.WRITE_REVIEW,
                                movie.getId().toString());
                        break;
                }
            } catch (IllegalArgumentException e) {
                navigation.goTo(new MovieReviewMenuView(navigation), accessLevel,
                        MovieReviewMenuView.MovieReviewMenuIntent.VIEW_REVIEW, userInput);
            }
    }

    private enum MovieReviewMenuOption implements EnumerableMenuOption {

        WRITE_REVIEW("Write Review");

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
