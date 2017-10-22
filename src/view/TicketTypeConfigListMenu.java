package view;

import config.TicketConfig;
import model.booking.TicketType;
import model.cinema.CinemaType;
import view.ui.*;

import java.util.ArrayList;
import java.util.Arrays;

public class TicketTypeConfigListMenu extends ListView {

    private static final String VALUE_DELIMITER = "-";

    private TicketConfig ticketConfig;

    public TicketTypeConfigListMenu(Navigation navigation) {
        super(navigation);
        this.ticketConfig = TicketConfig.getInstance();
    }

    @Override
    public void onLoad(NavigationIntent intent, String... args) {
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
                        String.join(VALUE_DELIMITER, cinemaType.name(), ticketType.name()),
                        cinemaType.isAvailable(ticketType) ? "ALLOWED" : "NOT ALLOWED"));
        setViewItems(viewItems.toArray(new ViewItem[viewItems.size()]));

        display();
        String userChoice = getChoice();
        if (userChoice.equals(BACK))
            navigation.goBack();
        else {
            String[] ticketTypeConfig = userChoice.split(VALUE_DELIMITER);
            CinemaType cinemaType = CinemaType.valueOf(ticketTypeConfig[0]);
            TicketType ticketType = TicketType.valueOf(ticketTypeConfig[1]);

            ArrayList<TicketType> ticketTypes = new ArrayList<>(Arrays.asList(cinemaType.getTicketTypes()));
            if (cinemaType.isAvailable(ticketType))
                ticketTypes.remove(ticketType);
            else
                ticketTypes.add(ticketType);

            ticketConfig.setAvailableTicketTypes(cinemaType, ticketTypes.toArray(new TicketType[ticketTypes.size()]));
            View.displaySuccess(String.format("Successfully toggled!"));
            Form.pressAnyKeyToContinue();
            navigation.reload();
        }
    }
}
