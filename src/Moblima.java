import manager.*;
import model.booking.Booking;
import model.booking.Showtime;
import model.booking.TicketType;
import model.cinema.CinemaLayout;
import model.cinema.CinemaType;
import model.cinema.Cineplex;
import model.commons.Language;
import model.commons.User;
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
import java.util.Hashtable;

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
//        try {
//
//            ShowtimeController showtimeController = ShowtimeController.getInstance();
//            MovieController movieController = MovieController.getInstance();
//            CineplexController cineplexController = CineplexController.getInstance();
//            CinemaController cinemaController = CinemaController.getInstance();
//            BookingController bookingController = BookingController.getInstance();
//            PaymentController paymentController = PaymentController.getInstance();
//            MovieReviewController movieReviewController = MovieReviewController.getInstance();
//
//            Cineplex cineplex1 = cineplexController.createCineplex("Orchard Hub", "Orchard 8 Grange Road, Singapore");
//            Cineplex cineplex2 = cineplexController.createCineplex("Woodlands Hub", "1 Woodlands Square, Singapore");
//            Cineplex cineplex3 = cineplexController.createCineplex("Jurong East Hub", "50 Jurong Gateway Road, Singapore");
//            Cineplex cineplex4 = cineplexController.createCineplex("AMK Hub", "Amg Mo Kio Ave 3, Singapore");
//            Cineplex cineplex5 = cineplexController.createCineplex("Downtown Hub", "Marine Parade Road, Singapore");
//            CinemaLayout smallLayout = new CinemaLayout(Arrays.asList(4), null, 7, 'E');
//            CinemaLayout mediumLayout = new CinemaLayout(Arrays.asList(7), null, 13, 'H');
//            CinemaLayout bigLayout = new CinemaLayout(Arrays.asList(9), null, 17, 'K');
//            for (Cineplex cineplex : cineplexController.getList()) {
//                int cinemaCode = 1;
//                for (; cinemaCode < 3; cinemaCode++)
//                    cinemaController.createCinema(cineplex.getId(), String.valueOf(cinemaCode),
//                            CinemaType.EXECUTIVE, smallLayout);
//                for (; cinemaCode < 7; cinemaCode++)
//                    cinemaController.createCinema(cineplex.getId(), String.valueOf(cinemaCode),
//                            CinemaType.PLATINUM, mediumLayout);
//                for (; cinemaCode < 13; cinemaCode++)
//                    cinemaController.createCinema(cineplex.getId(), String.valueOf(cinemaCode),
//                            CinemaType.REGULAR, bigLayout);
//            }
//
////            MoviePerson director1 = new MoviePerson("Ryan", "Coogler");
////            Movie movie1 = movieController.createMovie("Black Panther", "T'Challa, after the death "
////                            + "of his father, the King of Wakanda, "
////                            + "returns home to the isolated, "
////                            + "technologically advanced African nation "
////                            + "to succeed to the throne and take his "
////                            + "rightful place as king.", director1,
////                    new MoviePerson[]{director1}, MovieType.THREE_DIMENSION,
////                    MovieStatus.NOW_SHOWING, MovieRating.PG, 120);
////
////            MoviePerson director2 = new MoviePerson("Dean", "Devlin");
////            Movie movie2 = movieController.createMovie("Geostorm", "When the network of satellites " +
////                            "designed to control the global climate starts to attack Earth, it's a race against " +
////                            "the clock to uncover the real threat before a worldwide Geostorm wipes out " +
////                            "everything and everyone.", director2,
////                    new MoviePerson[]{director2}, MovieType.TWO_DIMENSION,
////                    MovieStatus.COMING_SOON, MovieRating.PG, 109);
////
////            MoviePerson director3 = new MoviePerson("Taika", "Waititi");
////            Movie movie3 = movieController.createMovie("Thor: Ragnarok", "Imprisoned, the almighty " +
////                            "Thor finds himself in a lethal gladiatorial contest against the Hulk, his former ally. " +
////                            "Thor must fight for survival and race against time to prevent the all-powerful Hela " +
////                            "from destroying his home and the Asgardian civilization.", director3,
////                    new MoviePerson[]{director3}, MovieType.BLOCKBUSTER,
////                    MovieStatus.NOW_SHOWING, MovieRating.PG, 130);
////
////            Calendar calendar = Calendar.getInstance();
////            calendar.setTime(new Date());
////            calendar.add(Calendar.HOUR, 1);
////
////            Language[] subtitles = new Language[] { Language.ENGLISH };
////            Showtime showtime1 = showtimeController.createShowtime(movie1.getId(), cineplex1.getId(),
////                    cineplex1.getCinemas().get(0).getId(), Language.ENGLISH, calendar.getTime(),
////                    true, subtitles);
////
////            calendar.add(Calendar.HOUR, 3);
////            Showtime showtime2 = showtimeController.createShowtime(movie1.getId(), cineplex1.getId(),
////                    cineplex1.getCinemas().get(0).getId(), Language.ENGLISH, calendar.getTime(),
////                    true, subtitles);
////
////            calendar.add(Calendar.HOUR, 6);
////            Showtime showtime4 = showtimeController.createShowtime(movie3.getId(), cineplex1.getId(),
////                    cineplex1.getCinemas().get(0).getId(), Language.ENGLISH, calendar.getTime(),
////                    true, subtitles);
////
////            calendar.add(Calendar.HOUR, -3);
////            Showtime showtime3 = showtimeController.createShowtime(movie1.getId(), cineplex1.getId(),
////                    cineplex1.getCinemas().get(0).getId(), Language.ENGLISH, calendar.getTime(),
////                    true, subtitles);
////
////            Hashtable<TicketType, Integer> ticketTypesCount = new Hashtable<>();
////            ticketTypesCount.put(TicketType.STANDARD, 1);
////
////            User user1 = userController.registerUser("Anqi", "Tu", "1", "a@a.com");
////            Booking booking1 = bookingController.createBooking(showtime1.getId());
////            bookingController.selectTicketType(booking1.getId(), ticketTypesCount);
////            bookingController.selectSeats(booking1.getId(),
////                    Arrays.asList(showtime1.getSeating().getSeatAt('A', 1)));
////            paymentController.makePayment(booking1);
////            bookingController.confirmBooking(booking1.getId(), user1.getId());
////
////            Booking booking3 = bookingController.createBooking(showtime4.getId());
////            bookingController.selectTicketType(booking3.getId(), ticketTypesCount);
////            bookingController.selectSeats(booking3.getId(),
////                    Arrays.asList(showtime4.getSeating().getSeatAt('A', 1)));
////            paymentController.makePayment(booking3);
////            bookingController.confirmBooking(booking3.getId(), user1.getId());
////
////            User user2 = userController.registerUser("Clarence", "Castillo", "2", "b@a.com");
////            Booking booking2 = bookingController.createBooking(showtime1.getId());
////            bookingController.selectTicketType(booking2.getId(), ticketTypesCount);
////            bookingController.selectSeats(booking2.getId(),
////                    Arrays.asList(showtime1.getSeating().getSeatAt('A', 2)));
////            paymentController.makePayment(booking2);
////            bookingController.confirmBooking(booking2.getId(), user2.getId());
////
////            Booking booking4 = bookingController.createBooking(showtime4.getId());
////            bookingController.selectTicketType(booking4.getId(), ticketTypesCount);
////            bookingController.selectSeats(booking4.getId(),
////                    Arrays.asList(showtime4.getSeating().getSeatAt('A', 2)));
////            paymentController.makePayment(booking4);
////            bookingController.confirmBooking(booking4.getId(), user2.getId());
////
////            movieReviewController.createReview("Good", 5, movie1.getId(), user1.getId());
////            movieReviewController.createReview("Good", 0, movie1.getId(), user2.getId());
////            movieReviewController.createReview("Good", 4, movie3.getId(), user1.getId());
////            movieReviewController.createReview("Good", 3, movie3.getId(), user2.getId());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

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
