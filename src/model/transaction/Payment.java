package model.transaction;

import model.commons.Entity;
import util.Utilities;

import java.util.Date;

/**
 * Represents a payment that is to be paid for.
 *
 * @version 1.0
 * @since 2017-10-20
 */
public class Payment extends Entity {

    /**
     * The status of this payment.
     */
    private PaymentStatus status;

    /**
     * The time of making this payment.
     */
    private Date date;

    /**
     * The transaction ID of this payment.
     */
    private String transactionId;

    /**
     * The amount of this payment.
     */
    private double amount;

    /**
     * Creates a payment that is to be paid for with a given amount and a given transaction code.
     * A payment is pending when it is initially created.
     *
     * @param amount The amount of this payment.
     * @param transactionCode The transaction code of the payment.
     */
    public Payment(double amount, String transactionCode) {
        this.amount = amount;
        this.date = new Date();
        this.transactionId = transactionCode + Utilities.toFormat(date, "yyyyMMddHHmm");
        this.status = PaymentStatus.PENDING;
    }

    /**
     * Gets the status of this payment.
     *
     * @return the status of this payment.
     */
    public PaymentStatus getStatus() {
        return status;
    }

    /**
     * Changes the status of this payment.
     *
     * @param status The new status of this payment.
     */
    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    /**
     * Gets the time of making this payment.
     *
     * @return the time of making this payment.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the time of making this payment when it is paid.
     *
     * @param date The time of paying this payment.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets the amount of this payment.
     *
     * @return the amount of this payment.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Changes the amount of this payment.
     *
     * @param amount The new amount of this payment.
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Gets the transaction ID of this payment.
     *
     * @return the transaction ID of this payment.
     */
    public String getTransactionId() {
        return transactionId;
    }
}
