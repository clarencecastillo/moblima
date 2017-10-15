package model.booking;

import java.util.UUID;
import model.commons.Entity;
import model.transaction.Payable;
import model.transaction.Payment;
import model.transaction.PaymentStatus;
import model.transaction.Priceable;

public class BookingCharge extends Entity implements Priceable, Payable {

    private double price;
    private String description;
    private Payment payment;
    private boolean refundable;

    public BookingCharge(double price, String description, boolean refundable) {
        this.price = price;
        this.description = description;
        this.payment = null;
        this.refundable = refundable;
    }

    @Override
    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public Payment getPayment() {
        return payment;
    }

    public boolean isRefundable() {
        return refundable;
    }

    @Override
    public boolean isPendingPayment() {
        return (payment == null || payment.getStatus() != PaymentStatus.ACCEPTED);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void setRefundable(boolean refundable) {
        this.refundable = refundable;
    }
}
