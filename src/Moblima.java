import controller.AdminMenuController;
import controller.Controller;
import controller.MainMenuController;
import controller.MovieListController;
import controller.Navigation;
import java.util.ArrayList;
import manager.MovieManager;
import manager.UserManager;
import model.movie.MoviePerson;
import model.movie.MovieRating;
import model.movie.MovieStatus;
import model.movie.MovieType;

public class Moblima {

    public static final String VERSION = "v1.0.0";

    private Navigation nav;
    private ArrayList<Controller> controllers;
    private Controller rootController;

    public Moblima() {
        controllers = new ArrayList<>();
        bootstrap();
    }

    public void bootstrap() {

        // DEBUG
        try {
            UserManager userManager = UserManager.getInstance();
            userManager.registerStaff("Anqi", "Tu", "91005071",
                                      "tuanqi@cinema.com", "tuanqi",  "513628");

            MovieManager movieManager = MovieManager.getInstance();
            MoviePerson director = new MoviePerson("Ryan", "Coogler",
                                                   "Ryan Coogler was born on May 23, "
                                                   + "1986 in Oakland, California, USA as Ryan "
                                                   + "Kyle Coogler. He is a director and writer, "
                                                   + "known for Creed (2015), Fruitvale Station "
                                                   + "(2013) and Black Panther (2018). ");
            movieManager.createMovie("Black Panther", "T'Challa, after the death "
                                                       + "of his father, the King of Wakanda, "
                                                       + "returns home to the isolated, "
                                                       + "technologically advanced African nation "
                                                       + "to succeed to the throne and take his "
                                                       + "rightful place as king.", director,
                                     new MoviePerson[] {director}, MovieType.THREE_DIMENSION,
                                     MovieStatus.COMING_SOON, MovieRating.PG);
        } catch (Exception e) {
            System.out.println("Debug Error!");
        }


        controllers.add(MainMenuController.getInstance());
        controllers.add(AdminMenuController.getInstance());
        controllers.add(MovieListController.getInstance());

        nav = new Navigation();
        for (Controller controller: controllers)
            controller.init(nav);

        rootController = controllers.get(8);
    }

    public void run() {
        nav.goTo(rootController, VERSION);
    }
}
