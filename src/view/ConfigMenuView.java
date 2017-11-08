package view;

import config.ConfigType;
import exception.UnauthorisedNavigationException;
import view.ui.AccessLevel;
import view.ui.Intent;
import view.ui.MenuView;
import view.ui.Navigation;

/**
 * This moblima.view displays the user interface for the user to select the configuration option.
 *
 * @version 1.0
 * @since 2017-10-30
 */
public class ConfigMenuView extends MenuView {

    public ConfigMenuView(Navigation navigation) {
        super(navigation);
    }

    @Override
    public void onLoad(AccessLevel accessLevel, Intent intent, String... args) {

        if (accessLevel != AccessLevel.ADMINISTRATOR)
            throw new UnauthorisedNavigationException();

        setTitle("Configuration Settings Menu");
        setMenuItems(ConfigType.values());
        addBackOption();
    }

    @Override
    public void onEnter() {
        display();
        String userChoice = getChoice();
        if (userChoice.equals(BACK))
            navigation.goBack();
        else
            switch (ConfigType.valueOf(userChoice)) {
                case HOLIDAY:
                    navigation.goTo(new HolidayConfigListView(navigation), AccessLevel.ADMINISTRATOR);
                    break;
                case TICKET:
                    navigation.goTo(new TicketConfigMenuView(navigation), AccessLevel.ADMINISTRATOR);
                    break;
                case BOOKING:
                    navigation.goTo(new BookingConfigListView(navigation), AccessLevel.ADMINISTRATOR);
                    break;
                case PAYMENT:
                    navigation.goTo(new PaymentConfigListView(navigation), AccessLevel.ADMINISTRATOR);
                    break;
                case ADMIN:
                    navigation.goTo(new AdminConfigListView(navigation), AccessLevel.ADMINISTRATOR);
                    break;
            }
    }
}
