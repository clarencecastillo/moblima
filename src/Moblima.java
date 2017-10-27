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

import java.io.*;
import java.util.*;
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
            User user = userManager.registerUser("Tu", "Tu", "1",
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

            Movie movie2 = movieManager.createMovie("Black Panther 2", "blabla", director,
                    new MoviePerson[]{director}, MovieType.THREE_DIMENSION,
                    MovieStatus.NOW_SHOWING, MovieRating.PG, 120);

            Movie movie3 = movieManager.createMovie("Black Panther 3", "blabla", director,
                    new MoviePerson[]{director}, MovieType.THREE_DIMENSION,
                    MovieStatus.NOW_SHOWING, MovieRating.PG, 120);

            Movie movie4 = movieManager.createMovie("Black Panther 4", "blabla", director,
                    new MoviePerson[]{director}, MovieType.THREE_DIMENSION,
                    MovieStatus.NOW_SHOWING, MovieRating.PG, 120);

            Movie movie5 = movieManager.createMovie("Black Panther 5", "blabla", director,
                    new MoviePerson[]{director}, MovieType.THREE_DIMENSION,
                    MovieStatus.NOW_SHOWING, MovieRating.PG, 120);

            Movie movie6 = movieManager.createMovie("Black Panther 6", "blabla", director,
                    new MoviePerson[]{director}, MovieType.THREE_DIMENSION,
                    MovieStatus.NOW_SHOWING, MovieRating.PG, 120);

            Movie movie7 = movieManager.createMovie("Black Panther 7", "blabla", director,
                    new MoviePerson[]{director}, MovieType.THREE_DIMENSION,
                    MovieStatus.NOW_SHOWING, MovieRating.PG, 120);

            movieReviewController.createReview("review blabla", 1, movie1.getId(), user.getId());
            movieReviewController.createReview("review blabla", 2, movie2.getId(), user.getId());
            movieReviewController.createReview("review blabla", 3, movie3.getId(), user.getId());
            movieReviewController.createReview("review blabla", 4, movie4.getId(), user.getId());
            movieReviewController.createReview("review blabla", 5, movie5.getId(), user.getId());
            movieReviewController.createReview("review blabla", 1, movie6.getId(), user.getId());
            movieReviewController.createReview("review blabla", 2, movie7.getId(), user.getId());

            movieReviewController.createReview("review blabla", 1, movie1.getId(), user.getId());
            movieReviewController.createReview("review blabla", 2, movie2.getId(), user.getId());
            movieReviewController.createReview("review blabla", 3, movie3.getId(), user.getId());
            movieReviewController.createReview("review blabla", 4, movie4.getId(), user.getId());
            movieReviewController.createReview("review blabla", 5, movie5.getId(), user.getId());
            movieReviewController.createReview("review blabla", 1, movie6.getId(), user.getId());
            movieReviewController.createReview("review blabla", 2, movie7.getId(), user.getId());

            Cineplex cineplex = cineplexController.createCineplex("AMK Hub", "Ang Mo Kio, Singapore");


            CinemaLayout layout1 = new CinemaLayout(Arrays.asList(9), null, 17, 'K');
            Cinema cinema1 = cinemaController.createCinema(cineplex.getId(), "1",
                    CinemaType.REGULAR, layout1);
            Calendar calendar = Calendar.getInstance();
            calendar.set(2017, 9, 27, 23, 8);
            Date startTime1 = calendar.getTime();
            Language[] subtitles = new Language[]{Language.ENGLISH};
            Showtime showtime1 = showtimeManager.createShowtime(movie1.getId(), cineplex.getId(), cinema1.getId(),
                    Language.ENGLISH, startTime1, false, false, subtitles);

            Booking booking1 = bookingController.createBooking(showtime1.getId());
            Hashtable<TicketType, Integer> ticketTypesCount = new Hashtable<>();
            ticketTypesCount.put(TicketType.PEAK, 1);
            bookingController.selectTicketType(booking1.getId(), ticketTypesCount);
            bookingController.selectSeats(booking1.getId(), Arrays.asList(layout1.getSeats()[0]));
            paymentController.makePayment(booking1);
            bookingController.confirmBooking(booking1.getId(), user.getId());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Debug Error!");
        }

//        ObjectOutputStream ooStream = null;
//        try {
//            ooStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("movies.dat")));
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println(e.getMessage());
//        }
//
//        try {
//            MovieController movieController = MovieController.getInstance();
//            ooStream.writeObject(movieController. ());
//            ooStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println(e.getMessage());
//        }
//
//        ObjectInputStream oiStream = null;
//        try {
//            oiStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream("movies.dat")));
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println(e.getMessage());
//        }
//
//        try {
//            movieManager.setEntities((Hashtable<UUID, Movie>)oiStream.readObject());
//            oiStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println(e.getMessage());
//        }

        nav = new Navigation();
    }

    public void run() {
        nav.goTo(new MainMenuView(nav), VERSION);
    }
}
