import controller.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import manager.BookingManager;
import manager.CinemaManager;
import manager.CineplexManager;
import manager.MovieManager;
import manager.ShowtimeManager;
import manager.TicketManager;
import manager.UserManager;
import model.booking.Booking;
import model.booking.Showtime;
import model.booking.TicketType;
import model.cinema.Cinema;
import model.cinema.CinemaLayout;
import model.cinema.CinemaType;
import model.cinema.Cineplex;
import model.cinema.Seat;
import model.cinema.SeatType;
import model.commons.Language;
import model.commons.User;
import model.movie.Movie;
import model.movie.MoviePerson;
import model.movie.MovieRating;
import model.movie.MovieStatus;
import model.movie.MovieType;

public class Moblima {

    public static final String VERSION = "v1.0.0";

    private Navigation nav;
    private ArrayList<Controller> controllers;
    private Controller rootController;

    public Moblima() {
        controllers = new ArrayList<>();
        bootstrap();
    }

    public void bootstrap() {

        // DEBUG
        try {
            UserManager userManager = UserManager.getInstance();
            userManager.registerStaff("Anqi", "Tu", "91005071",
                                      "tuanqi@cinema.com", "tuanqi",  "513628");
            User user1 = userManager.registerUser("Tu", "Tu", "82678543",
                    "tuanqi96@cinema.com");

            MovieManager movieManager = MovieManager.getInstance();
            MoviePerson director = new MoviePerson("Ryan", "Coogler",
                                                   "Ryan Coogler was born on May 23, "
                                                   + "1986 in Oakland, California, USA as Ryan "
                                                   + "Kyle Coogler. He is a director and writer, "
                                                   + "known for Creed (2015), Fruitvale Station "
                                                   + "(2013) and Black Panther (2018). ");
            Movie movie1 = movieManager.createMovie("Black Panther", "T'Challa, after the death "
                                                       + "of his father, the King of Wakanda, "
                                                       + "returns home to the isolated, "
                                                       + "technologically advanced African nation "
                                                       + "to succeed to the throne and take his "
                                                       + "rightful place as king.", director,
                                     new MoviePerson[] {director}, MovieType.THREE_DIMENSION,
                                     MovieStatus.COMING_SOON, MovieRating.PG, 120);


            CineplexManager cineplexManager = CineplexManager.getInstance();
            Cineplex cineplex = cineplexManager.createCineplex("Cineplex1", "Address1");

            CinemaManager cinemaManager = CinemaManager.getInstance();
            Seat[] seats = new Seat[4];
            for (int i = 0; i < seats.length; i++) {
                seats[i] = new Seat('A',i+1, SeatType.SINGLE);
            }
            CinemaLayout layout1 = new CinemaLayout(seats,4,'A');
            Cinema cinema1 = cinemaManager.createCinema(cineplex.getId(), "Cinema1", CinemaType.REGULAR, layout1);

            ShowtimeManager showtimeManager = ShowtimeManager.getInstance();

            Calendar calendar = Calendar.getInstance();
            calendar.set(2017, 9, 20, 8, 8);

            Date startTime1 = calendar.getTime();
            Language[] subtitles = new Language[1];
            subtitles[0]= Language.ENGLISH;
            Showtime showtime1 = showtimeManager.createShowtime(movie1.getId(),cinema1.getId(), Language.ENGLISH,startTime1, false,false, subtitles);

            BookingManager bookingManager = BookingManager.getInstance();
            Booking booking1 = bookingManager.createBooking(user1.getId(),showtime1.getId());

            TicketManager ticketManager = TicketManager.getInstance();
            ticketManager.createTicket(booking1.getId(),seats[0], TicketType.STANDARD);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Debug Error!");
        }

        controllers.add(MainMenuController.getInstance());
        controllers.add(AdminMenuController.getInstance());
        controllers.add(MovieListController.getInstance());
        controllers.add(BookingListController.getInstance());
        controllers.add(ConfigMenuController.getInstance());

        nav = new Navigation();
        for (Controller controller: controllers)
            controller.init(nav);

        rootController = controllers.get(4);
    }

    public void run() {
        nav.goTo(rootController, VERSION);
    }
}
