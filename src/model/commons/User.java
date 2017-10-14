package model.commons;

import java.util.ArrayList;
import model.booking.Booking;
import model.movie.MovieReview;

public class User extends Person {

    private String mobile;
    private String email;
    private ArrayList<MovieReview> reviews;
    private ArrayList<Booking> bookings;

    public User(String firstName, String lastName, String mobile, String email) {
        super(firstName, lastName);
        this.mobile = mobile;
        this.email = email;
        this.reviews = new ArrayList<MovieReview>();
        this.bookings = new ArrayList<Booking>();
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<MovieReview> getMovieReviews() {
        return reviews;
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addReview(MovieReview movieReview) {
        reviews.add(movieReview);
    }

    public void removeReview(MovieReview movieReview) {
        reviews.remove(movieReview);
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public void removeBooking(Booking booking) {
        bookings.remove(booking);
    }
}
