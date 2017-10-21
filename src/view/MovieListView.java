package view;

import java.util.ArrayList;
import java.util.Arrays;
import manager.MovieController;
import model.movie.Movie;
import view.ui.Describable;
import view.ui.Form;
import view.ui.ListView;
import view.ui.Navigation;
import view.ui.View;

public class MovieListView extends ListView {

    private MovieListIntent intent;
    private ArrayList<Movie> movies;

    private MovieController movieController;

    public MovieListView(Navigation navigation) {
        super(navigation);
        this.movies = new ArrayList<>();
        this.movieController = MovieController.getInstance();
    }

    @Override
    public void onLoad(String... args) {
        intent = MovieListIntent.valueOf(args[0]);
        setTitle("Movie Listings");
        switch (intent) {
            case SEARCH:
                View.displayInformation("Please enter search terms. Keywords may include movie "
                                        + "title, director, and actors.");
                String searchKeyword = Form.getString("Search");
                movies.addAll(Arrays.asList(movieController.findByKeyword(searchKeyword)));
                setContent("Your search for '" + searchKeyword + "' yielded "
                           + movies.size() + " movie items.");
                setMenuItems(new MovieListMenuOption[] {
                    MovieListMenuOption.GO_BACK
                });
                break;

            case ADMIN:
                movies.addAll(movieController.getList());
                setMenuItems(new MovieListMenuOption[] {
                    MovieListMenuOption.ADD_MOVIE,
                    MovieListMenuOption.GO_BACK
                });
                break;
        }

        setViewItems(movies.stream().map(MovieItemView::new).toArray(MovieItemView[]::new));
    }

    @Override
    public void onEnter() {
        display();
        String userInput = getChoice();
        try {
            MovieListMenuOption userChoice = MovieListMenuOption.valueOf(userInput);
            switch (userChoice) {
                case ADD_MOVIE:
                    System.out.println("Add movie!");
                    break;
                case GO_BACK:
                    navigation.goBack();
                    break;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Selected movie ID " + userInput);
//            navigation.goTo(MovieViewController.getInstance(), userInput);
        }
    }

    public enum MovieListIntent {
        SEARCH,
        ADMIN
    }

    public enum MovieListMenuOption implements Describable {

        ADD_MOVIE("Add Movie"),
        GO_BACK("Go Back");

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
