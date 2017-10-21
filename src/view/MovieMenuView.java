package view;

import java.util.UUID;
import manager.MovieController;
import model.movie.Movie;
import view.ui.Describable;
import view.ui.MenuView;
import view.ui.Navigation;
import view.ui.NavigationIntent;

public class MovieMenuView extends MenuView {

    private Movie movie;

    private MovieController movieController;

    public MovieMenuView(Navigation navigation) {
        super(navigation);
        this.movieController = MovieController.getInstance();

    }

    @Override
    public void onLoad(NavigationIntent intent, String... args) {
        movie = movieController.findById(UUID.fromString(args[0]));
        switch ((MovieMenuIntent) intent) {
            case ADMIN:
                setMenuItems(MovieMenuOption.values());
                break;
            case VIEW:
                setMenuItems(MovieMenuOption.VIEW_SHOWTIMES,
                             MovieMenuOption.SEE_REVIEWS,
                             MovieMenuOption.WRITE_REVIEW);
                break;
        }

        MovieView movieView = new MovieView(movie);
        setTitle(movieView.getTitle());
        setContent(movieView.getContent());
        addBackOption();
    }

    @Override
    public void onEnter() {
        display();
        String userChoice = getChoice();
        if (userChoice.equals(BACK))
            navigation.goBack();
        else
            switch (MovieMenuOption.valueOf(userChoice)) {
                case VIEW_SHOWTIMES:
                    break;
                case SEE_REVIEWS:
                    break;
                case WRITE_REVIEW:
                    break;
                case UPDATE:
                    break;
                case REMOVE:
                    break;
            }
    }

    public enum MovieMenuIntent implements NavigationIntent {
        ADMIN,
        VIEW
    }

    private enum MovieMenuOption implements Describable {

        VIEW_SHOWTIMES("View Showtimes"),
        SEE_REVIEWS("See Reviews"),
        WRITE_REVIEW("Write Review"),
        UPDATE("Update"),
        REMOVE("Remove");

        private String description;
        MovieMenuOption(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }
}
