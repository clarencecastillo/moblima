package view;

import config.ConfigType;
import view.ui.MenuView;
import view.ui.Navigation;
import view.ui.NavigationIntent;

public class ConfigMenuView extends MenuView {

    public ConfigMenuView(Navigation navigation) {
        super(navigation);
    }

    @Override
    public void onLoad(NavigationIntent intent, String... args) {

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
                    navigation.goTo(new HolidayListMenuView(navigation));
                    break;
                case TICKET:
                    navigation.goTo(new TicketConfigMenuView(navigation));
                    break;
                case BOOKING:
                    navigation.goTo(new BookingConfigListView(navigation));
                    break;
                case PAYMENT:
                    navigation.goTo(new PaymentConfigListView(navigation));
                    break;
                case ADMIN:
                    navigation.goTo(new AdminConfigListView(navigation));
                    break;
            }
    }
}
