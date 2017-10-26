import manager.*;
import model.booking.Booking;
import model.booking.Showtime;
import model.booking.TicketType;
import model.cinema.*;
import model.commons.Language;
import model.commons.User;
import model.movie.*;
import view.MainMenuView;
import view.ui.Navigation;

import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.stream.IntStream;

//import manager.TicketController;

public class Moblima {

    public static final String VERSION = "v1.0.0";

    private Navigation nav;

    public Moblima() {
        bootstrap();
    }

    public void bootstrap() {

        BookingController.init();
        CineplexController.init();
        CinemaController.init();
        MovieController.init();
        MovieReviewController.init();
        PaymentController.init();
        ShowtimeController.init();
        UserController.init();

        // DEBUG
        try {

            UserController userManager = UserController.getInstance();
            MovieController movieManager = MovieController.getInstance();
            MovieReviewController movieReviewController = MovieReviewController.getInstance();
            CineplexController cineplexController = CineplexController.getInstance();
            CinemaController cinemaController = CinemaController.getInstance();
            ShowtimeController showtimeManager = ShowtimeController.getInstance();
            BookingController bookingController = BookingController.getInstance();
            PaymentController paymentController = PaymentController.getInstance();

            userManager.registerStaff("Anqi", "Tu", "91005071",
                    "tuanqi@cinema.com", "tuanqi", "513628");
            User user = userManager.registerUser("Tu", "Tu", "82678543",
                    "tuanqi96@cinema.com");

            MoviePerson director = new MoviePerson("Ryan", "Coogler");
            Movie movie1 = movieManager.createMovie("Black Panther", "T'Challa, after the death "
                            + "of his father, the King of Wakanda, "
                            + "returns home to the isolated, "
                            + "technologically advanced African nation "
                            + "to succeed to the throne and take his "
                            + "rightful place as king.", director,
                    new MoviePerson[]{director}, MovieType.THREE_DIMENSION,
                    MovieStatus.NOW_SHOWING, MovieRating.PG, 120);

            movieReviewController.createReview("Plots dealing with the irreversible damage to our Mother " +
                    "Earth is realistic in the sense that we create new technology to continue sustaining life. " +
                    "What happens when it fails, reverting back and shocking the natural phenomenon that sustain us",
                    4, movie1.getId(), user.getId());

            movieReviewController.createReview("Plots dealing with the irreversible damage to our Mother " +
                            "Earth is realistic in the sense that we create new technology to continue sustaining life. " +
                            "What happens when it fails, reverting back and shocking the natural phenomenon that sustain us",
                    4, movie1.getId(), user.getId());

            Cineplex cineplex = cineplexController.createCineplex("AMK Hub", "Ang Mo Kio, Singapore");
            Seat[] seats = IntStream.range(0, 4).mapToObj(col ->
                    new Seat('A', col + 1, SeatType.SINGLE)).toArray(Seat[]::new);
            CinemaLayout layout1 = new CinemaLayout(seats, 4, 'A');
            Cinema cinema1 = cinemaController.createCinema(cineplex.getId(), "1",
                    CinemaType.REGULAR, layout1);
            Calendar calendar = Calendar.getInstance();
            calendar.set(2017, 9, 25, 23, 8);
            Date startTime1 = calendar.getTime();
            Language[] subtitles = new Language[]{Language.ENGLISH};
            Showtime showtime1 = showtimeManager.createShowtime(movie1.getId(), cineplex.getId(), cinema1.getId(),
                    Language.ENGLISH, startTime1, false, false, subtitles);

            Booking booking1 = bookingController.createBooking(showtime1.getId());
            Hashtable<TicketType, Integer> ticketTypesCount = new Hashtable<>();
            ticketTypesCount.put(TicketType.STANDARD, 4);
            bookingController.selectTicketType(booking1.getId(), ticketTypesCount);
            bookingController.selectSeat(booking1.getId(), seats);
            paymentController.makePayment(booking1);
            bookingController.confirmBooking(booking1.getId(), user.getId());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Debug Error!");
        }

        nav = new Navigation();
    }

    public void run() {
        nav.goTo(new MainMenuView(nav), VERSION);
    }
}
