package view;

import exception.UnauthorisedNavigationException;
import view.ui.*;

/**
 * This view displays the user interface for the user to select action for configuring ticket settings.
 *
 * @version 1.0
 * @since 2017-10-30
 */

public class TicketConfigMenuView extends MenuView {

    public TicketConfigMenuView(Navigation navigation) {
        super(navigation);
    }

    @Override
    public void onLoad(AccessLevel accessLevel, Intent intent, String... args) {

        if (accessLevel != AccessLevel.ADMINISTRATOR)
            throw new UnauthorisedNavigationException();

        setTitle("Ticket Config");
        setMenuItems(TicketConfigMenuOption.values());
        addBackOption();
    }

    @Override
    public void onEnter() {
        display();
        String userChoice = getChoice();
        if (userChoice.equals(BACK))
            navigation.goBack();
        else
            switch (TicketConfigMenuOption.valueOf(userChoice)) {
                case SET_PRICING:
                    navigation.goTo(new PricingConfigListMenu(navigation), AccessLevel.ADMINISTRATOR);
                    break;
                case SET_ALLOWED_TICKET_TYPES:
                    navigation.goTo(new TicketTypeConfigListMenu(navigation), AccessLevel.ADMINISTRATOR);
                    break;
            }
    }

    private enum TicketConfigMenuOption implements EnumerableMenuOption {

        SET_PRICING("Set Pricing"),
        SET_ALLOWED_TICKET_TYPES("Set Allowed Ticket Types");

        private String description;

        TicketConfigMenuOption(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }
}
