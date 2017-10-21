//package controller;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import manager.MovieController;
//import model.movie.Movie;
//import view.ui.Describable;
//import view.Menu;
//import view.ViewItem;
//
//public class MovieListController extends Controller {
//
//    private static MovieListController instance = new MovieListController();
//
//    private Menu movieListMenu;
//
//    private MovieController movieManager;
//
//    private MovieListController() {
//        movieManager = MovieController.getInstance();
//    }
//
//    public static MovieListController getInstance() {
//        return instance;
//    }
//
//    @Override
//    public void setupView() {
//        movieListMenu = new Menu();
//        movieListMenu.setTitle("Movie Listings");
//    }
//
//    @Override
//    public void onEnter(String[] arguments) {
//
//
//
//
//
//    }
//
//    public enum MovieListIntent {
//        SEARCH,
//        ADMIN
//    }
//
//    public enum MovieListMenuOption implements Describable {
//
//        ADD_MOVIE("Add Movie"),
//        GO_BACK("Go Back");
//
//        private String description;
//        MovieListMenuOption(String description) {
//            this.description = description;
//        }
//
//        @Override
//        public String getDescription() {
//            return description;
//        }
//    }
//}
