package manager;

import exception.IllegalMovieStatusException;
import exception.IllegalMovieStatusTransitionException;
import exception.UninitialisedSingletonException;
import model.booking.Booking;
import model.booking.Showtime;
import model.booking.ShowtimeStatus;
import model.booking.Ticket;
import model.cinema.Cineplex;
import model.movie.*;
import util.Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MovieController extends EntityController<Movie> {

    public static final int SIMILARITY_THRESHOLD = 5;

    private static MovieController instance;

    private MovieController() {
        super();
    }

    public static void init() {
        instance = new MovieController();
    }

    public static MovieController getInstance() {
        if (instance == null)
            throw new UninitialisedSingletonException();
        return instance;
    }

    public Movie createMovie(String title, String sypnosis, MoviePerson director,
                             MoviePerson[] actors, MovieType movieType,
                             MovieStatus status, MovieRating rating, int runtimeMinutes) {
        Movie movie = new Movie(title, sypnosis, director, movieType, actors, status, rating, runtimeMinutes);
        entities.put(movie.getId(), movie);
        return movie;
    }

    public void changeMovieStatus(UUID movieId, MovieStatus status) throws IllegalMovieStatusTransitionException {

        Movie movie = findById(movieId);
        if (movie.getStatus() == status)
            throw new IllegalMovieStatusTransitionException("Already in '" + status + "' state");

        switch (status) {
            case END_OF_SHOWING:
                for (Showtime showtime : movie.getShowtimes())
                    if (showtime.getStatus() == ShowtimeStatus.OPEN_BOOKING)
                        throw new IllegalMovieStatusTransitionException("Cannot remove movies with upcoming/ongoing showtimes");
                break;
            case PREVIEW:
                if (movie.getStatus() != MovieStatus.COMING_SOON)
                    throw new IllegalMovieStatusTransitionException();
                break;
            case NOW_SHOWING:
                if (movie.getStatus() == MovieStatus.END_OF_SHOWING)
                    throw new IllegalMovieStatusTransitionException();
                break;
            case COMING_SOON:
                throw new IllegalMovieStatusTransitionException();
        }

        movie.setStatus(status);
    }

    public void changeMovieDetails(UUID movieId, String title, String sypnosis,
                                   MoviePerson director, MoviePerson[] actors,
                                   MovieRating rating, int runtime) {
        Movie movie = findById(movieId);
        movie.setTitle(title);
        movie.setSynopsis(sypnosis);
        movie.setDirector(director);
        movie.setActors(actors);
        movie.setRating(rating);
        movie.setRuntime(runtime);
    }

    public void changeMovieType(UUID movieId, MovieType type) throws IllegalMovieStatusException {
        Movie movie = findById(movieId);
        if (movie.getStatus() != MovieStatus.COMING_SOON)
            throw new IllegalMovieStatusException("Can only change movie type when it is not yet available for screening");
        movie.setType(type);
    }

    public Movie[] findByStatus(MovieStatus... status) {
        ArrayList<Movie> movies = new ArrayList<>();
        List<MovieStatus> statuses = Arrays.asList(status);
        for (Movie movie : entities.values())
            if (statuses.contains(movie.getStatus()))
                movies.add(movie);
        return movies.toArray(new Movie[movies.size()]);
    }

    public Movie[] findByKeyword(String keyword) {
        ArrayList<Movie> movies = new ArrayList<>();
        for (Movie movie : entities.values()) {
            for (String tag : movie.getSearchTags())
                if (Utilities.levenshteinDistance(keyword, tag) <= SIMILARITY_THRESHOLD) {
                    movies.add(movie);
                    break;
                }
        }
        return movies.toArray(new Movie[movies.size()]);
    }

    /**
     * Finds all the movies shown in a given cineplex.
     *
     * @param cineplex The cineplex to be....
     * @return
     */
    public List<Movie> findByCineplex(Cineplex cineplex) {
        return entities.values().stream().filter(movie ->
                movie.getShowtimes().stream().anyMatch(showtime ->
                        showtime.getCineplex().equals(cineplex))).collect(Collectors.toList());
    }

    /**
     * Gets a given movie's overall rating which will only be shown when there is more than one rating.
     *
     * @param movieId the given ID of a movie.
     * @return this movie's overall rating.
     */
    public double getOverallReviewRating(UUID movieId) {
        Movie movie = findById(movieId);
        if (movie.getReviews().size() <= 1)
            return -1;

        int sum = 0;
        for (MovieReview review : movie.getReviews())
            sum += review.getRating();
        return sum / movie.getReviews().size();
    }

    /**
     * Gets a given movie's overall rating which will only be shown when there is more than one rating.
     * NA will be displayed if there is no more than one movie review.
     *
     * @param movieId the given ID of a movie.
     * @return this movie's total ticket sale.
     */
    public int getTicketSales(UUID movieId) {
        Movie movie = findById(movieId);
        int sum = 0;
        for (Showtime showtime : movie.getShowtimes())
            for (Booking booking : showtime.getBookings())
                for (Ticket ticket : booking.getTickets())
                    sum++;
        return sum;
    }
}
