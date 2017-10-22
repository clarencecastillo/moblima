package config;

public class PaymentConfig implements Configurable {

    private static PaymentConfig instance = new PaymentConfig();
    private static double gst;

    private PaymentConfig() {
        reset();
    }

    public static PaymentConfig getInstance() {
        return instance;
    }

    public static double getGst() {
        return gst;
    }

    public void setGst(double gst) {
        PaymentConfig.gst = gst;
    }

    @Override
    public ConfigType getConfigType() {
        return ConfigType.PAYMENT;
    }

    @Override
    public void reset() {
        gst = 0.7;
    }
}
