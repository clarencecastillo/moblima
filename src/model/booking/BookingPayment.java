package model.booking;

import model.cinema.Cinema;
import model.transaction.Payment;
import util.Utilities;

public class BookingPayment extends Payment {

    private String transactionId;

    public BookingPayment(Payment payment) {
        super(payment.getStatus(), payment.getPayable());
        Booking booking = (Booking) payable;
        Cinema cinema = booking.getShowtime().getCinema();
        this.transactionId = cinema.getCode() + Utilities.toFormat(date, "yyyyMMddHHmm");
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionId() {
        return transactionId;
    }

}
