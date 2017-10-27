package model.transaction;

/**
 * Represents a base interface that should be implemented by all classes whose price can be paid.
 *
 * @author Castillo Clarence Fitzgerald Gumtang
 * @version 1.0
 * @since 2017-10-20
 */
public interface Payable extends Priceable {
    /**
     * Gets the payment of this priceable.
     *
     * @return the payment of this priceable.
     */
    Payment getPayment();

    /**
     * Sets the payment of this priceable.
     *
     * @param payment he payment of this priceable.
     */
    void setPayment(Payment payment);

    // TODO Javadoc
    String getTransactionCode();
}
