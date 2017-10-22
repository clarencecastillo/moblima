import java.util.Calendar;
import java.util.Date;
import manager.BookingController;
import manager.CinemaController;
import manager.CineplexController;
import manager.MovieController;
import manager.ShowtimeController;
import manager.TicketController;
import manager.UserController;
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
import view.MainMenuView;
import view.ui.Navigation;

public class Moblima {

    public static final String VERSION = "v1.0.0";

    private Navigation nav;

    public Moblima() {
        bootstrap();
    }

    public void bootstrap() {

        // DEBUG
        try {
            UserController userManager = UserController.getInstance();
            userManager.registerStaff("Anqi", "Tu", "91005071",
                                      "tuanqi@cinema.com", "tuanqi",  "513628");
            User user1 = userManager.registerUser("Tu", "Tu", "82678543",
                    "tuanqi96@cinema.com");

            MovieController movieManager = MovieController.getInstance();
            MoviePerson director = new MoviePerson("Ryan", "Coogler");
            Movie movie1 = movieManager.createMovie("Black Panther", "T'Challa, after the death "
                                                       + "of his father, the King of Wakanda, "
                                                       + "returns home to the isolated, "
                                                       + "technologically advanced African nation "
                                                       + "to succeed to the throne and take his "
                                                       + "rightful place as king.", director,
                                     new MoviePerson[] {director}, MovieType.THREE_DIMENSION,
                                     MovieStatus.COMING_SOON, MovieRating.PG, 120);


            CineplexController cineplexController = CineplexController.getInstance();
            Cineplex cineplex = cineplexController.createCineplex("Cineplex1", "Address1");

            CinemaController cinemaController = CinemaController.getInstance();
            Seat[] seats = new Seat[4];
            for (int i = 0; i < seats.length; i++) {
                seats[i] = new Seat('A',i+1, SeatType.SINGLE);
            }
            CinemaLayout layout1 = new CinemaLayout(seats,4,'A');
            Cinema cinema1 = cinemaController.createCinema(cineplex.getId(), "Cinema1", CinemaType.REGULAR, layout1);

            ShowtimeController showtimeManager = ShowtimeController.getInstance();

            Calendar calendar = Calendar.getInstance();
            calendar.set(2017, 9, 24, 8, 8);

            Date startTime1 = calendar.getTime();
            Language[] subtitles = new Language[1];
            subtitles[0]= Language.ENGLISH;
            Showtime showtime1 = showtimeManager.createShowtime(movie1.getId(),cineplex.getId(),cinema1.getId(), Language.ENGLISH,startTime1, false,false, subtitles);

            BookingController bookingController = BookingController.getInstance();
            Booking booking1 = bookingController.createBooking(showtime1.getId());

            TicketController ticketManager = TicketController.getInstance();
            ticketManager.createTicket(booking1.getId(),seats[0], TicketType.STANDARD);

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
