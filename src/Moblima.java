import manager.*;
import model.booking.Showtime;
import model.cinema.CinemaLayout;
import model.cinema.CinemaType;
import model.cinema.Cineplex;
import model.commons.Language;
import model.movie.*;
import util.Utilities;
import view.MainMenuView;
import view.ui.Form;
import view.ui.Navigation;
import view.ui.View;

import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class Moblima {

    public static final String VERSION = "v1.0.0";

    private Navigation nav;

    public Moblima() {
        bootstrap();
    }

    public void bootstrap() {

        // Initialise Navigation
        nav = new Navigation();

        // Initialise Singleton Controllers
        BookingController.init();
        CineplexController.init();
        CinemaController.init();
        MovieController.init();
        MovieReviewController.init();
        PaymentController.init();
        ShowtimeController.init();
        UserController.init();

        // Load data
        try {
            ObjectInputStream objectInputStream = Utilities.getObjectInputStream(EntityController.DAT_FILENAME);
            if (objectInputStream != null)
                EntityController.load(objectInputStream);
        } catch (InvalidClassException e) {
            View.displayWarning("Incompatible save version!");
            Form.pressAnyKeyToContinue();
        } catch (Exception e) {
            View.displayWarning("Data not found!");
            Form.pressAnyKeyToContinue();
        }

        // Creates ROOT user if none exist
        UserController userController = UserController.getInstance();
        if (userController.getList().size() == 0) {
            userController.registerStaff("Root", "", "00000000",
                    "", "root", "root");
        }

        // Debug Data
        try {

            ShowtimeController showtimeController = ShowtimeController.getInstance();
            MovieController movieController = MovieController.getInstance();
            CineplexController cineplexController = CineplexController.getInstance();
            CinemaController cinemaController = CinemaController.getInstance();

            Cineplex cineplex1 = cineplexController.createCineplex("Orchard Hub", "Orchard 8 Grange Road, Singapore");
            Cineplex cineplex2 = cineplexController.createCineplex("Woodlands Hub", "1 Woodlands Square, Singapore");
            Cineplex cineplex3 = cineplexController.createCineplex("Jurong East Hub", "50 Jurong Gateway Road, Singapore");
            Cineplex cineplex4 = cineplexController.createCineplex("AMK Hub", "Amg Mo Kio Ave 3, Singapore");
            Cineplex cineplex5 = cineplexController.createCineplex("Downtown Hub", "Marine Parade Road, Singapore");
            CinemaLayout smallLayout = new CinemaLayout(Arrays.asList(4), null, 7, 'E');
            CinemaLayout mediumLayout = new CinemaLayout(Arrays.asList(7), null, 13, 'H');
            CinemaLayout bigLayout = new CinemaLayout(Arrays.asList(9), null, 17, 'K');
            for (Cineplex cineplex : cineplexController.getList()) {
                int cinemaCode = 1;
                for (; cinemaCode < 3; cinemaCode++)
                    cinemaController.createCinema(cineplex.getId(), String.valueOf(cinemaCode),
                            CinemaType.EXECUTIVE, smallLayout);
                for (; cinemaCode < 7; cinemaCode++)
                    cinemaController.createCinema(cineplex.getId(), String.valueOf(cinemaCode),
                            CinemaType.PLATINUM, mediumLayout);
                for (; cinemaCode < 13; cinemaCode++)
                    cinemaController.createCinema(cineplex.getId(), String.valueOf(cinemaCode),
                            CinemaType.REGULAR, bigLayout);
            }

            MoviePerson director1 = new MoviePerson("Ryan", "Coogler");
            Movie movie1 = movieController.createMovie("Black Panther", "T'Challa, after the death "
                            + "of his father, the King of Wakanda, "
                            + "returns home to the isolated, "
                            + "technologically advanced African nation "
                            + "to succeed to the throne and take his "
                            + "rightful place as king.", director1,
                    new MoviePerson[]{director1}, MovieType.THREE_DIMENSION,
                    MovieStatus.NOW_SHOWING, MovieRating.PG, 120);

            MoviePerson director2 = new MoviePerson("Dean", "Devlin");
            Movie movie2 = movieController.createMovie("Geostorm", "When the network of satellites " +
                            "designed to control the global climate starts to attack Earth, it's a race against " +
                            "the clock to uncover the real threat before a worldwide Geostorm wipes out " +
                            "everything and everyone.", director2,
                    new MoviePerson[]{director2}, MovieType.TWO_DIMENSION,
                    MovieStatus.COMING_SOON, MovieRating.PG, 109);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.HOUR, 1);

            Language[] subtitles = new Language[] { Language.ENGLISH };
            Showtime showtime1 = showtimeController.createShowtime(movie1.getId(), cineplex1.getId(),
                    cineplex1.getCinemas().get(0).getId(), Language.ENGLISH, calendar.getTime(),
                    false, subtitles);

            calendar.add(Calendar.HOUR, 3);
            Showtime showtime2 = showtimeController.createShowtime(movie1.getId(), cineplex1.getId(),
                    cineplex1.getCinemas().get(0).getId(), Language.ENGLISH, calendar.getTime(),
                    false, subtitles);

            calendar.add(Calendar.HOUR, 6);
            Showtime showtime4 = showtimeController.createShowtime(movie1.getId(), cineplex1.getId(),
                    cineplex1.getCinemas().get(0).getId(), Language.ENGLISH, calendar.getTime(),
                    false, subtitles);

            calendar.add(Calendar.HOUR, -3);
            Showtime showtime3 = showtimeController.createShowtime(movie1.getId(), cineplex1.getId(),
                    cineplex1.getCinemas().get(0).getId(), Language.ENGLISH, calendar.getTime(),
                    false, subtitles);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // DEBUG
//        try {
//
//            UserController userController = UserController.getInstance();
//            MovieController movieController = MovieController.getInstance();
//            MovieReviewController movieReviewController = MovieReviewController.getInstance();
//            CineplexController cineplexController = CineplexController.getInstance();
//            CinemaController cinemaController = CinemaController.getInstance();
//            ShowtimeController showtimeController = ShowtimeController.getInstance();
//            BookingController bookingController = BookingController.getInstance();
//            PaymentController paymentController = PaymentController.getInstance();
//
//
//
//            Booking booking1 = bookingController.createBooking(showtime1.getId());
//            Hashtable<TicketType, Integer> ticketTypesCount = new Hashtable<>();
//            ticketTypesCount.put(TicketType.PEAK, 1);
//            bookingController.selectTicketType(booking1.getId(), ticketTypesCount);
//            bookingController.selectSeats(booking1.getId(), Arrays.asList(layout1.getSeats()[0]));
//            paymentController.makePayment(booking1);
//            bookingController.confirmBooking(booking1.getId(), user.getId());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("Debug Error!");
//        }
    }

    public void run() {
        nav.goTo(new MainMenuView(nav), VERSION);
    }
}
