package config;

/**
 * Represents the configuration of payment setting.
 *
 * @version 1.0
 * @since 2017-10-20
 */
public class PaymentConfig implements Configurable {

    /**
     * A reference to this singleton instance.
     */
    private static PaymentConfig instance = new PaymentConfig();

    /**
     * The GST to be paid for each booking.
     */
    private static double gst;

    /**
     * Initializes the payment configuration by resetting.
     */
    private PaymentConfig() {
        reset();
    }

    /**
     * Gets this PaymentConfig's singleton instance.
     *
     * @return this PaymentConfig's singleton instance.
     */
    public static PaymentConfig getInstance() {
        return instance;
    }

    /**
     * Gets the GST to be paid for each booking.
     *
     * @return the GST to be paid for each booking.
     */
    public static double getGst() {
        return gst;
    }

    /**
     * Changse the GST to be paid for each booking.
     *
     * @param gst the new GST to be paid for each booking.
     */
    public void setGst(double gst) {
        PaymentConfig.gst = gst;
    }

    /**
     * Gets the type of this configuration.
     *
     * @return the type of this configuration which is payment.
     */
    @Override
    public ConfigType getConfigType() {
        return ConfigType.PAYMENT;
    }

    /**
     * Resets the payment settings to the default.
     */
    @Override
    public void reset() {
        gst = 0.07;
    }
}
