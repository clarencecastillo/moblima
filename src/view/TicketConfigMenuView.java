package view;

import view.ui.*;

public class TicketConfigMenuView extends MenuView {

    public TicketConfigMenuView(Navigation navigation) {
        super(navigation);
    }

    @Override
    public void onLoad(NavigationIntent intent, String... args) {
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
                    navigation.goTo(new PricingConfigListMenu(navigation));
                    break;
                case SET_ALLOWED_TICKET_TYPES:
                    navigation.goTo(new TicketTypeConfigListMenu(navigation));
                    break;
            }
    }

    private enum TicketConfigMenuOption implements Describable {

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
