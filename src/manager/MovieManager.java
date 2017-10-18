package manager;

import java.util.UUID;
import model.booking.Showtime;
import model.booking.ShowtimeStatus;
import model.movie.Movie;
import model.movie.MoviePerson;
import model.movie.MovieRating;
import model.movie.MovieStatus;
import model.movie.MovieType;

public class MovieManager extends EntityManager<Movie> {

    private static MovieManager instance = new MovieManager();

    private MovieManager() {
        super();
    }

    public static MovieManager getInstance() {
        return instance;
    }

    public Movie createMovie(String title, String sypnosis, MoviePerson director,
                             MoviePerson[] actors, MovieType movieType,
                             MovieStatus status, MovieRating rating) {
        Movie movie = new Movie(title, sypnosis, director, movieType, actors, status, rating);
        entities.put(movie.getId(), movie);
        return movie;
    }

    public void changeMovieStatus(UUID movieId, MovieStatus status) {

        Movie movie = findById(movieId);
        if (movie.getStatus() == status)
            return; // TODO Already in state

        switch (status) {
            case END_OF_SHOWING:
                for (Showtime showtime: movie.getShowtimes())
                    if (showtime.getStatus() == ShowtimeStatus.OPEN_BOOKING)
                        return; // TODO Can't remove movie with upcoming/ongoing showtimes
                break;
            case PREVIEW:
                if (movie.getStatus() != MovieStatus.COMING_SOON)
                    return; // TODO Illegal state transition
                break;
            case NOW_SHOWING:
                if (movie.getStatus() == MovieStatus.END_OF_SHOWING)
                    return; // TODO Illegal state transition
                break;
            case COMING_SOON:
                return; // TODO Illegal stata transition
        }

        movie.setStatus(status);
    }

    public void changeMovieDetails(UUID movieId, String title, String sypnosis,
                                   MoviePerson director, MoviePerson[] actors,
                                   MovieRating rating) {
        Movie movie = findById(movieId);
        movie.setTitle(title);
        movie.setSypnosis(sypnosis);
        movie.setDirector(director);
        movie.setActors(actors);
        movie.setRating(rating);
    }

    public void changeMovieType(UUID movieId, MovieType type) {
        Movie movie = findById(movieId);
        if (movie.getStatus() != MovieStatus.COMING_SOON)
            return; // TODO can only change movie type when not yet available for screening
        movie.setType(type);
    }
}
