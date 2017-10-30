package view;

import exception.RejectedNavigationException;
import manager.EntityController;
import manager.UserController;
import model.cinema.Staff;
import util.Utilities;
import view.ui.*;

import java.io.ObjectOutputStream;

public class AdminMenuView extends MenuView {

    public static final int MAX_LOGIN_ATTEMPTS = 5;

    private Staff administrator;

    private UserController userController;

    public AdminMenuView(Navigation navigation) {
        super(navigation);
        userController = UserController.getInstance();
    }

    @Override
    public void onLoad(AccessLevel accessLevel, Intent intent, String... args) {
        setTitle("Admin Menu");
        setMenuItems(AdminMenuOption.values());
        View.displayInformation("Please enter your credentials");

        for (int loginAttempts = 1; loginAttempts <= MAX_LOGIN_ATTEMPTS; loginAttempts++) {

            String username = Form.getString("Username", 0);
            String password = Form.getCensoredString("Password");

            if (userController.login(username, password)) {
                administrator = userController.findByUsername(username);
                break;
            }
            String attemptsMessage = "[" + loginAttempts + " of " + MAX_LOGIN_ATTEMPTS + "]";
            View.displayError("Access denied. Please try again. " + attemptsMessage);
        }

        if (administrator == null) {
            View.displayError("Max login attempts reached!");
            Form.pressAnyKeyToContinue();
            throw new RejectedNavigationException();
        }

        setContent("Signed in: " + administrator.getUsername());
        View.displaySuccess("Access granted!");
    }

    @Override
    public void onEnter() {
        display();
        AdminMenuOption userChoice = AdminMenuOption.valueOf(getChoice());
        switch (userChoice) {
            case MANAGE_MOVIE_LISTINGS:
                navigation.goTo(new MovieListView(navigation), AccessLevel.ADMINISTRATOR,
                        MovieListView.MovieListIntent.VIEW_MOVIES);
                break;
            case MANAGE_SHOWTIMES:
                navigation.goTo(new ShowtimeListView(navigation), AccessLevel.ADMINISTRATOR,
                        ShowtimeListView.ShowtimeListIntent.VIEW_SHOWTIMES);
                break;
            case VIEW_REPORTS:
                break;
            case CONFIGURE_SETTINGS:
                navigation.goTo(new ConfigMenuView(navigation), AccessLevel.ADMINISTRATOR);
                break;
            case SAVE_DATA:
                View.displayWarning("Warning! This will irreversibly overwrite all previously saved data.");
                switch(Form.getConfirmOption("Save", "Cancel")) {
                    case CONFIRM:
                        ObjectOutputStream objectOutputStream = Utilities
                                .getObjectOutputStream(EntityController.DAT_FILENAME);
                        if (objectOutputStream != null) {
                            try {
                                EntityController.save(objectOutputStream);
                                View.displaySuccess("Successfully saved entities to file!");
                            } catch (Exception e) {
                                e.printStackTrace();
                                View.displayError("Error in saving entities to file.");
                            }
                        }
                        break;
                    case CANCEL:
                        break;
                }
                Form.pressAnyKeyToContinue();
                navigation.refresh();
                break;
            case LOGOUT:
                View.displaySuccess("You have been logged out successfully!");
                Form.pressAnyKeyToContinue();
                navigation.goBack();
                break;
        }
    }

    private enum AdminMenuOption implements EnumerableMenuOption {
        MANAGE_MOVIE_LISTINGS("Manage Movie Listings"),
        MANAGE_SHOWTIMES("Manage Showtimes"),
        VIEW_REPORTS("View Top 5"),
        CONFIGURE_SETTINGS("Configure Settings"),
        SAVE_DATA("Save Data"),
        LOGOUT("Log Out");

        private String description;

        AdminMenuOption(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }
}
