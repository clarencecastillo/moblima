package view;

import config.AdminConfig;
import view.MovieListView.MovieListIntent;
import view.ui.Navigation;
import exception.InputUnrecognisedException;
import view.ui.Describable;
import view.ui.MenuView;
import view.ui.View;

public class MainMenuView extends MenuView {

    public String version;

    public MainMenuView(Navigation navigation) {
        super(navigation);
    }

    @Override
    public void onLoad(String... args) {
        this.version = args[0];

        setMenuItems(MainMenuOption.values());
        setTitle("MOBLIMA " + version);
        setContent("TODO Description here");
    }

    @Override
    public void onEnter() {
        display();
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
                View.displayError(UNRECOGNIZED_ERROR);
            }

        switch (userChoice) {
            case SEARCH_MOVIES:
                navigation.goTo(new MovieListView(navigation), MovieListIntent.SEARCH.toString());
                break;
            case LIST_MOVIES:
                navigation.goTo(new MovieListView(navigation));
                break;
            case VIEW_SHOWTIMES:
                break;
            case VIEW_BOOKING_HISTORY:
                break;
            case VIEW_TOP_5_MOVIES:
                navigation.goTo(new MovieListView(navigation));
                break;
            case ADMIN:
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
