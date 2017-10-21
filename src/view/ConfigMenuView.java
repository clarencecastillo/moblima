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
                    break;
                case TICKET:
                    break;
                case BOOKING:
                    break;
                case PAYMENT:
                    break;
                case ADMIN:
                    break;
            }
    }
}
