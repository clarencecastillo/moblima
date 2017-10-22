package model.transaction;

import model.booking.Booking;
import model.cinema.Cinema;
import model.commons.Entity;
import util.Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Payment extends Entity {

    private PaymentStatus status;
    private Date date;
    private String transactionId;
    private double amount;

    public Payment(double amount) {
        this.amount = amount;
        this.status = PaymentStatus.PENDING;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {return amount; }

    public void setAmount(double amount) {this.amount = amount; }

    public void setTransactionId(Booking booking) {
        Cinema cinema = booking.getShowtime().getCinema();
        this.transactionId = cinema.getCode() + Utilities.toFormat(date, "yyyyMMddHHmm");
    }

    public String getTransactionId() {
        return transactionId;
    }
}
