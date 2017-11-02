package view;

import config.TicketConfig;
import exception.UnauthorisedNavigationException;
import model.booking.TicketType;
import model.cinema.CinemaType;
import view.ui.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This view displays the user interface for the user to configure available ticket types for each cinema.
 *
 * @version 1.0
 * @since 2017-10-30
 */
public class TicketTypeConfigListMenu extends ListView {

    private static final String VALUE_DELIMITER = "-";

    private TicketConfig ticketConfig;

    public TicketTypeConfigListMenu(Navigation navigation) {
        super(navigation);
        this.ticketConfig = TicketConfig.getInstance();
    }

    @Override
    public void onLoad(AccessLevel accessLevel, Intent intent, String... args) {

        if (accessLevel != AccessLevel.ADMINISTRATOR)
            throw new UnauthorisedNavigationException();

        setTitle("Available Ticket Types Config");
        setContent("Select the item to toggle its current value.");
        addBackOption();
    }

    @Override
    public void onEnter() {

        ArrayList<ViewItem> viewItems = new ArrayList<>();
        for (CinemaType cinemaType : CinemaType.values())
            for (TicketType ticketType : TicketType.values())
                viewItems.add(new ViewItem(cinemaType.toString() + " - " + ticketType.toString(),
                        cinemaType.isAvailable(ticketType) ? "ALLOWED" : "NOT ALLOWED",
                        String.join(VALUE_DELIMITER, cinemaType.name(), ticketType.name())));
        setViewItems(viewItems);

        display();
        String userChoice = getChoice();
        if (userChoice.equals(BACK))
            navigation.goBack();
        else {
            String[] ticketTypeConfig = userChoice.split(VALUE_DELIMITER);
            CinemaType cinemaType = CinemaType.valueOf(ticketTypeConfig[0]);
            TicketType ticketType = TicketType.valueOf(ticketTypeConfig[1]);

            ArrayList<TicketType> ticketTypes = new ArrayList<>(cinemaType.getTicketTypes());
            if (cinemaType.isAvailable(ticketType))
                ticketTypes.remove(ticketType);
            else
                ticketTypes.add(ticketType);

            ticketConfig.setAvailableTicketTypes(cinemaType, ticketTypes);
            View.displaySuccess(String.format("Successfully toggled!"));
            Form.pressAnyKeyToContinue();
            navigation.reload(AccessLevel.ADMINISTRATOR);
        }
    }
}
