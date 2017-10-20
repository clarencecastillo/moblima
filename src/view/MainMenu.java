package view;

import config.AdminConfig;
import controller.Navigation;
import exception.InputUnrecognisedException;

public class MainMenu extends Menu implements ConsoleInterface {

    public String version;
    public Navigation navigation;

    public MainMenu(Navigation navigation) {
        this.navigation = navigation;
    }

    public void onEnter(String... args) {
        this.version = version;

        setMenuItems(MainMenuOption.values());
        setTitle("MOBLIMA " + version);
        setContent("TODO Description here");
    }

    public void display() {
        displayHeader();
        displayContent();
        displayItems();
    }

    public void onDisplay() {

        MainMenuOption userChoice = null;
        while(true)
            try {
                userChoice = MainMenuOption.valueOf(getChoiceIgnoreMismatch());
                break;
            } catch (InputUnrecognisedException e) {
                String mismatchInput = e.getMismatchInput().toString();
                if (mismatchInput.equals(AdminConfig.getAdminSecret())) {
                    userChoice = MainMenuOption.ADMIN;
                    break;
                }
                View.displayError(Menu.UNRECOGNIZED_ERROR);
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

    public enum MainMenuOption implements Describable {

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
