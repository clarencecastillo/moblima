package controller;

import view.Menu;
import view.MenuOption;
import view.View;

public class MovieViewController extends Controller {

    private static MovieViewController instance = new MovieViewController();

    protected View movieView;
    protected Menu movieMenu;

    protected MovieViewController() {}

    public static MovieViewController getInstance() {
        return instance;
    }

    @Override
    public void onLoad(String[] arguments) {}

    @Override
    public void setupView() {
        movieView = new View("Movie View");
        movieMenu = new Menu();
        movieMenu.setContent(Menu.getDescriptions(MovieMenuOption.values()));
    }

    @Override
    public View getView() {
        return movieView;
    }

    @Override
    public void onViewDisplay() {

        movieMenu.display();

        MovieMenuOption userChoice = MovieMenuOption.values()[movieMenu.getChoice()];

        switch (userChoice) {
            case VIEW_SHOWTIMES:
                break;
            case SEE_REVIEWS:
                break;
            case WRITE_REVIEWS:
                View reviewerLogInView;
                reviewerLogInView = new View("Reviewer Log In Menu");
                Menu reviewerLogInMenu = new Menu();
                reviewerLogInMenu.setContent(Menu.getDescriptions(ReviewerLogInMenuOption.values()));
                reviewerLogInMenu.display();

                ReviewerLogInMenuOption userLogInChoice = ReviewerLogInMenuOption.values()[reviewerLogInMenu.getChoice()];
                switch (userLogInChoice) {
                    case LOG_IN_VIA_PHONE_NUMBER:
                        break;
                    case LOG_IN_VIA_EMAIL:
                        break;
                    case ANONYMOUS:
                        break;
                }
                break;
        }
    }

    private enum MovieMenuOption implements MenuOption {

        VIEW_SHOWTIMES("View Showtimes"),
        SEE_REVIEWS("See Reviews"),
        WRITE_REVIEWS("Write Reviews");

        private String description;
        MovieMenuOption(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }

    private enum ReviewerLogInMenuOption implements MenuOption {

        LOG_IN_VIA_PHONE_NUMBER("Log in via phone number"),
        LOG_IN_VIA_EMAIL("Log in via email"),
        ANONYMOUS("Anonymous");

        private String description;
        ReviewerLogInMenuOption(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }
}

