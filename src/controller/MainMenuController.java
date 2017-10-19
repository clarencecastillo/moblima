package controller;

import config.AdminConfig;
import controller.MovieListController.MovieListIntent;
import exception.InputUnrecognisedException;
import view.Describable;
import view.Menu;

public class MainMenuController extends Controller {

    private static MainMenuController instance = new MainMenuController();

    private String version;

    private Menu mainMenu;

    private MainMenuController() { }

    public static MainMenuController getInstance() {
        return instance;
    }

    @Override
    public void setupView() {

        mainMenu = new Menu();
        mainMenu.setMenuItems(MainMenuOption.values());
    }

    @Override
    public void onEnter(String[] arguments) {

        version = arguments[0];

        mainMenu.setTitle("MOBLIMA " + version);
        mainMenu.displayHeader();

        mainMenu.setContent("TODO Description here");
        mainMenu.displayContent();

        mainMenu.displayItems();

        MainMenuOption userChoice = null;
        while(true)
            try {
                userChoice = MainMenuOption.valueOf(mainMenu.getChoiceIgnoreMismatch());
                break;
            } catch (InputUnrecognisedException e) {
                String mismatchInput = e.getMismatchInput().toString();
                if (mismatchInput.equals(AdminConfig.getAdminSecret())) {
                    userChoice = MainMenuOption.ADMIN;
                    break;
                }
                mainMenu.displayError(Menu.UNRECOGNIZED_ERROR);
            }

        switch (userChoice) {
            case SEARCH_MOVIES:

                navigation.clearScreen();

                mainMenu.setTitle("Search Movies");
                mainMenu.displayHeader();

                mainMenu.setContent("Please enter search terms. Keywords may include movie "
                                    + "title, director, and actors.");
                mainMenu.displayContent();

                navigation.goTo(MovieListController.getInstance(),
                                MovieListIntent.SEARCH.toString(),
                                mainMenu.getString("Enter keywords"));
                break;
            case LIST_MOVIES:
                break;
            case VIEW_SHOWTIMES:
                break;
            case VIEW_BOOKING_HISTORY:
                break;
            case VIEW_TOP_5_MOVIES:
                break;
            case ADMIN:
                navigation.goTo(AdminMenuController.getInstance());
                break;
        }
    }

    private enum MainMenuOption implements Describable {

        SEARCH_MOVIES("Search Movies"),
        LIST_MOVIES("List Movies"),
        VIEW_SHOWTIMES("View Showtimes"),
        VIEW_BOOKING_HISTORY("View Booking History"),
        VIEW_TOP_5_MOVIES("View Top 5 Movies"),
        ADMIN(null);

        private String description;
        MainMenuOption(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }
}
