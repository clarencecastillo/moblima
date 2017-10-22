package view;

import config.TicketConfig;
import exception.InputOutOfBoundsException;
import exception.InputUnrecognisedException;
import model.booking.TicketType;
import model.cinema.CinemaType;
import model.cinema.SeatType;
import model.movie.MovieType;
import view.ui.*;

import java.util.ArrayList;

public class PricingConfigListMenu extends ListView {

    private static final String VALUE_DELIMITER = "-";

    private TicketConfig ticketConfig;

    public PricingConfigListMenu(Navigation navigation) {
        super(navigation);
        this.ticketConfig = TicketConfig.getInstance();
    }

    @Override
    public void onLoad(NavigationIntent intent, String... args) {
        setTitle("Pricing Config");
        setContent("Select the item to change price. All values are displayed in SGD.");
        addBackOption();
    }

    @Override
    public void onEnter() {

        ArrayList<ViewItem> viewItems = new ArrayList<>();
        for (MovieType movieType: MovieType.values())
            viewItems.add(new ViewItem(movieType.toString() + " Movies",
                    String.join(VALUE_DELIMITER, "MovieType", movieType.name(),
                            movieType.toString() + " Movies"),
                    String.format("$%.2f", movieType.getPrice())));
        for (TicketType ticketType: TicketType.values())
            viewItems.add(new ViewItem(ticketType.toString() + "s",
                    String.join(VALUE_DELIMITER, "TicketType", ticketType.name(),
                            ticketType.toString() + "s"),
                    String.format("$%.2f", ticketType.getPrice())));
        for (CinemaType cinemaType: CinemaType.values())
            viewItems.add(new ViewItem(cinemaType.toString() + " Cinemas",
                    String.join(VALUE_DELIMITER, "CinemaType", cinemaType.name(),
                            cinemaType.toString() + " Cinemas"),
                    String.format("$%.2f", cinemaType.getPrice())));
        for (SeatType seatType: SeatType.values())
            viewItems.add(new ViewItem(seatType.toString() + " Seats",
                    String.join(VALUE_DELIMITER, "SeatType", seatType.name(),
                            seatType.toString() + " Seats"),
                    String.format("$%.2f", seatType.getPrice())));
        setViewItems(viewItems.toArray(new ViewItem[viewItems.size()]));

        display();
        String userChoice = getChoice();
        if (userChoice.equals(BACK))
            navigation.goBack();
        else {
            String[] priceable = userChoice.split(VALUE_DELIMITER);
            View.displayInformation("Please enter new pricing value in SGD.");
            while (true)
                try {
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
                        case "SeatType":
                            ticketConfig.setPriceableRate(SeatType.valueOf(priceable[1]), newPrice);
                            break;
                    }
                    View.displaySuccess(String.format("Successfully changed pricing of " + priceable[2] + " to $%.2f",
                            newPrice));
                    Form.pressAnyKeyToContinue();
                    break;
                } catch (InputOutOfBoundsException e) {
                    View.displayError("Invalid price! Please enter pricing value from $0 to $10.");
                } catch (InputUnrecognisedException e) {
                    View.displayError("Unrecognised pricing value! Please enter a valid pricing value.");
                }

            navigation.reload();
        }


    }
}
