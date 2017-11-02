package model.transaction;

import config.PaymentConfig;

/**
 * Represents a base interface that should be implemented by all classes which can be set pricing rate.
 *
 * @version 1.0
 * @since 2017-10-20
 */
public interface Priceable {

    /**
     * Gets the total price of this priceable by all the individual priceables of this priceable.
     *
     * @param priceables All the individual priceables that make up of this priceable.
     * @return the total price of this priceable.
     */
    static double getPrice(Priceable... priceables) {
        double sum = 0;
        for (Priceable priceable : priceables)
            sum += priceable.getPrice();
        return sum;
    }

    /**
     * Gets the pricing rate of this priceable.
     *
     * @return the pricing rate of this priceable.
     */
    double getPrice();

    /**
     * Gets the price of this priceable with GST.
     * @param priceable The GST to be added to the price of this priceable.
     * @return the price of this priceable with GST.
     */
    static double getPriceWithGst(Priceable priceable) {
        return priceable.getPrice() * (1 + PaymentConfig.getGst());
    }
}
