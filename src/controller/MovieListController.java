package controller;

import java.util.ArrayList;
import java.util.Arrays;
import manager.MovieManager;
import model.movie.Movie;
import model.movie.MoviePerson;
import view.Describable;
import view.Menu;
import view.ViewItem;

public class MovieListController extends Controller {

    private static MovieListController instance = new MovieListController();

    private Menu movieListMenu;

    private MovieManager movieManager;

    private MovieListController() {
        movieManager = MovieManager.getInstance();
    }

    public static MovieListController getInstance() {
        return instance;
    }

    @Override
    public void setupView() {
        movieListMenu = new Menu();
        movieListMenu.setTitle("Movie Listings");
    }

    @Override
    public void onLoad(String[] arguments) {

        MovieListIntent intent = MovieListIntent.valueOf(arguments[0]);
        ArrayList<Movie> movies = new ArrayList<>();

        switch (intent) {
            case SEARCH:

                movieListMenu.setTitle("Search Movies");
                movieListMenu.setContent(new String[] {
                    "Please enter search terms. Keywords may include movie "
                    + "title, director, and actors."
                });

                movieListMenu.displayHeader();
                movieListMenu.displayContent();

                String searchKeyword = movieListMenu.getString("Enter keywords");
                movies.addAll(Arrays.asList(movieManager.findByKeyword(searchKeyword)));

                movieListMenu.setTitle("Search Results");
                movieListMenu.setContent(new String[] {
                    "Your search '" + searchKeyword + "' yielded "
                    + movies.size() + " movie items."
                });
                break;
            case ADMIN:

                movies.addAll(movieManager.getList());

                break;
        }

        navigation.clearScreen();
        movieListMenu.displayHeader();
        movieListMenu.display();

        ArrayList<Item> items = new ArrayList<>();
        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);

            ListMenuItem movieView = new ListMenuItem(i + 1);
            movieView.setTitle(String.format("%s [%s] %s", movie.getTitle(),
                                             movie.getType(), movie.getRating()));
            MoviePerson[] movieActors = movie.getActors();
            String[] actorNames = new String[movieActors.length];
            for (int a = 0; a < movieActors.length; a++)
                actorNames[a] = movieActors[a].getFullName();
            String directorName = movie.getDirector().getFullName();
            String movieScore = movie.getOverallReviewRating() == -1 ? "NA" :
                                String.format("%.1f/5.0", movie.getOverallReviewRating());
            movieView.setContent(new String[] {
                "Director: " + directorName,
                "Actors: " + String.join(",", actorNames),
                "Score: " + movieScore,
                "Sypnosis",
                movie.getSypnosis()
            });
            items.add(movieView);
        }

        if (intent == MovieListIntent.ADMIN) {
            MovieListAdminOption[] options = MovieListAdminOption.values();
            for (int i = 0; i < options.length; i++)
                items.add(new MenuItem(i+1, options[i].getDescription()));
        }

        movieListMenu.setMenuItems(items.toArray(new ListMenuItem[items.size()]));
        movieListMenu.displayMenuItemsWithBack();
        movieListMenu.getChoice();
    }

    public enum MovieListIntent {
        SEARCH,
        ADMIN
    }

    public enum MovieListAdminOption implements Describable {

        ADD_MOVIE("Add Movie");

        private String description;
        MovieListAdminOption(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }
}
