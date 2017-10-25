package model.transaction;

/**
 Represents a standard set of payment status.
 @author Castillo Clarence Fitzgerald Gumtang
 @version 1.0
 @since 2017-10-20
 */
public enum PaymentStatus {

    /**
     * The pending status is when the payment is initially created and has not been paid.
     *
     */
    PENDING("Pending"),
    /**
     * The accepting status is when the payment is paid and valid.
     */
    ACCEPTED("Accepted"),

    /**
     * The rejected status is when the payment is paid and is invalid.
     */
    REJECTED("Rejected");

    /**
     * The name of this payment status.
     */
    private String name;

    /**
     * Creates a payment status with s given status name.
     * @param name The name of this payment status.
     */
    PaymentStatus(String name) {
        this.name = name;
    }

    /**
     * Gets the name of this payment status.
     * @return the name of this payment status.
     */
    @Override
    public String toString() {
        return name;
    }
}
