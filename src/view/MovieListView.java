package view;

import java.util.ArrayList;
import java.util.Arrays;
import manager.MovieController;
import model.movie.Movie;
import model.movie.MovieStatus;
import view.MovieMenuView.MovieMenuIntent;
import view.ui.EnumerableMenuOption;
import view.ui.Form;
import view.ui.NavigationIntent;
import view.ui.ListView;
import view.ui.Navigation;
import view.ui.View;
import view.ui.ViewItem;

public class MovieListView extends ListView {

    private MovieListIntent intent;
    private ArrayList<Movie> movies;
    private String searchKeyword;

    private MovieController movieController;

    public MovieListView(Navigation navigation) {
        super(navigation);
        this.movies = new ArrayList<>();
        this.movieController = MovieController.getInstance();
    }

    @Override
    public void onLoad(NavigationIntent intent, String... args) {
        this.intent = (MovieListIntent) intent;
        setTitle("Movie Listings");
        switch (this.intent) {
            case SEARCH:
                View.displayInformation("Please enter search terms. Keywords may include movie "
                        + "title, director, and actors.");
                searchKeyword = Form.getString("Search");
                break;
            case ADMIN:
                setMenuItems(MovieListMenuOption.ADD_MOVIE);
                break;
            case LIST:
                break;
        }
        addBackOption();
    }

    @Override
    public void onEnter() {

        movies = new ArrayList<>();
        switch (this.intent) {
            case SEARCH:
                movies.addAll(Arrays.asList(movieController.findByKeyword(searchKeyword)));
                setContent("Your search for '" + searchKeyword + "' yielded "
                        + movies.size() + " movie item(s).");
                break;

            case LIST:
                movies.addAll(Arrays.asList(movieController.findByStatus(MovieStatus.PREVIEW,
                        MovieStatus.COMING_SOON,
                        MovieStatus.NOW_SHOWING)));
                setContent("Displaying " + movies.size() + " movie item(s).");
                break;
            case ADMIN:
                movies.addAll(movieController.getList());
                setContent("Displaying " + movies.size() + " movie item(s).");
                break;
        }
        setViewItems(movies.stream().map(
                movie -> new ViewItem(new MovieView(movie),
                        movie.getId().toString())).toArray(ViewItem[]::new));

        display();
        String userInput = getChoice();
        if (userInput.equals(BACK))
            navigation.goBack();
        else
            try {
                MovieListMenuOption userChoice = MovieListMenuOption.valueOf(userInput);
                switch (userChoice) {
                    case ADD_MOVIE:
                        navigation.goTo(new MovieMenuView(navigation), MovieMenuIntent.CREATE);
                        break;
                }
            } catch (IllegalArgumentException e) {
                navigation.goTo(new MovieMenuView(navigation),
                                this.intent == MovieListIntent.ADMIN ?
                                MovieMenuIntent.MANAGE : MovieMenuIntent.VIEW, userInput);
            }
    }

    public enum MovieListIntent implements NavigationIntent {
        SEARCH,
        ADMIN,
        LIST
    }

    public enum MovieListMenuOption implements EnumerableMenuOption {

        ADD_MOVIE("Add Movie");

        private String description;
        MovieListMenuOption(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }
}
