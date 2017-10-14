package manager;

import config.BookingConfig;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import model.booking.Booking;
import model.booking.BookingStatus;
import model.booking.Showtime;
import model.booking.ShowtimeStatus;
import model.cinema.Cinema;
import model.commons.Language;
import model.movie.Movie;
import model.movie.MovieStatus;
import util.Utilities;

public class ShowtimeManager extends EntityManager<Showtime> {

    private static ShowtimeManager instance;

    private ShowtimeManager() {
        super();
    }

    public static ShowtimeManager getInstance() {
        if (instance == null)
            instance = new ShowtimeManager();
        return instance;
    }

    public Showtime createShowtime(UUID movieId, UUID cinemaId, Language language,
                                   Date startTime, Date endTime, boolean noFreePasses,
                                   boolean isPreview, Language[] subtitles) {

        MovieManager movieManager = MovieManager.getInstance();
        CinemaManager cinemaManager = CinemaManager.getInstance();

        Movie movie = movieManager.findById(movieId);

        if (movie.getStatus() == MovieStatus.PREVIEW && !isPreview)
            return null; // TODO Movie is still in preview

        if (movie.getStatus() == MovieStatus.END_OF_SHOWING)
            return null; // TODO Cant create showtime for removed movies

        Cinema cinema = cinemaManager.findById(cinemaId);

        Showtime showtime = new Showtime(movie, cinema, language, startTime, endTime,
                                         noFreePasses, isPreview, subtitles);

        movie.addShowtime(showtime);
        entities.put(showtime.getId(), showtime);
        return showtime;
    }

    public void cancelShowtime(UUID showtimeId) {

        BookingManager bookingManager = BookingManager.getInstance();
        Showtime showtime = findById(showtimeId);

        if (showtime.getStatus() == ShowtimeStatus.CANCELLED)
            return; // TODO showtime already cancelled

        int minutesBeforeClosedBooking = BookingConfig.getMinutesBeforeClosedBooking();
        Date lastBookingMinute = Utilities.getDateBefore(showtime.getStartTime(), Calendar.MINUTE,
                                                         minutesBeforeClosedBooking);
        Date now = new Date();
        if (now.after(lastBookingMinute))
            return; // TODO last booking minute already passed,

        showtime.setCancelled(true);
        for (Booking booking: showtime.getBookings())
            bookingManager.changeBookingStatus(booking.getId(), BookingStatus.CANCELLED);
    }
}
