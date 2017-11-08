package view;

import config.TicketConfig;
import exception.UnauthorisedNavigationException;
import model.booking.TicketType;
import model.cineplex.CinemaType;
import model.movie.MovieType;
import view.ui.*;

import java.util.ArrayList;
/**
 * This moblima.view displays the user interface for the user to configure pricing settings.
 *
 * @version 1.0
 * @since 2017-10-30
 */
public class PricingConfigListMenu extends ListView {

    private static final String VALUE_DELIMITER = "-";

    private TicketConfig ticketConfig;

    public PricingConfigListMenu(Navigation navigation) {
        super(navigation);
        this.ticketConfig = TicketConfig.getInstance();
    }

    @Override
    public void onLoad(AccessLevel accessLevel, Intent intent, String... args) {

        if (accessLevel != AccessLevel.ADMINISTRATOR)
            throw new UnauthorisedNavigationException();

        setTitle("Pricing Config");
        setContent("Select the item to change price. All values are displayed in SGD.");
        addBackOption();
    }

    @Override
    public void onEnter() {

        ArrayList<ViewItem> viewItems = new ArrayList<>();
        for (MovieType movieType : MovieType.values())
            viewItems.add(new ViewItem(movieType.toString() + " Movies",
                    String.format("$%.2f", movieType.getPrice()),
                    String.join(VALUE_DELIMITER, "MovieType", movieType.name(),
                            movieType.toString() + " Movies")));
        for (TicketType ticketType : TicketType.values())
            viewItems.add(new ViewItem(ticketType.toString() + "s",
                    String.format("$%.2f", ticketType.getPrice()),
                    String.join(VALUE_DELIMITER, "TicketType", ticketType.name(),
                            ticketType.toString() + "s")));
        for (CinemaType cinemaType : CinemaType.values())
            viewItems.add(new ViewItem(cinemaType.toString() + " Cinemas",
                    String.format("$%.2f", cinemaType.getPrice()),
                    String.join(VALUE_DELIMITER, "CinemaType", cinemaType.name(),
                            cinemaType.toString() + " Cinemas")));
        setViewItems(viewItems);

        display();
        String userChoice = getChoice();
        if (userChoice.equals(BACK))
            navigation.goBack();
        else {
            String[] priceable = userChoice.split(VALUE_DELIMITER);
            View.displayInformation("Please enter new pricing value in SGD.");
            double newPrice = Form.getDouble("Enter new pricing for " + priceable[2] + "",
                    0, 10);
            switch (priceable[0]) {
                case "MovieType":
                    ticketConfig.setPriceableRate(MovieType.valueOf(priceable[1]), newPrice);
                    break;
                case "TicketType":
                    ticketConfig.setPriceableRate(TicketType.valueOf(priceable[1]), newPrice);
                    break;
                case "CinemaType":
                    ticketConfig.setPriceableRate(CinemaType.valueOf(priceable[1]), newPrice);
                    break;
            }
            View.displaySuccess(String.format("Successfully changed pricing of " + priceable[2] + " to $%.2f",
                    newPrice));
            Form.pressAnyKeyToContinue();
            navigation.reload(AccessLevel.ADMINISTRATOR);
        }


    }
}
