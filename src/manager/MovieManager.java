package manager;

import java.util.UUID;

import exception.IllegalMovieStatusException;
import exception.IllegalMovieStatusTransitionException;
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

    public void changeMovieStatus(UUID movieId, MovieStatus status) throws IllegalMovieStatusTransitionException {

        Movie movie = findById(movieId);
        if (movie.getStatus() == status)
            throw new IllegalMovieStatusTransitionException("Already in '" + status + "' state");

        switch (status) {
            case END_OF_SHOWING:
                for (Showtime showtime: movie.getShowtimes())
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
                                   MovieRating rating) {
        Movie movie = findById(movieId);
        movie.setTitle(title);
        movie.setSypnosis(sypnosis);
        movie.setDirector(director);
        movie.setActors(actors);
        movie.setRating(rating);
    }

    public void changeMovieType(UUID movieId, MovieType type) throws IllegalMovieStatusException{
        Movie movie = findById(movieId);
        if (movie.getStatus() != MovieStatus.COMING_SOON)
            throw new IllegalMovieStatusException("Can only change movie type when it is not yet available for screening");
        movie.setType(type);
    }
}
