package config;

public class PaymentConfig implements Configurable {

    private static PaymentConfig instance = new PaymentConfig();
    private static boolean refundsAllowed;
    private static double gst;

    private PaymentConfig() {
        reset();
    }

    public static PaymentConfig getInstance() {
        return instance;
    }

    public static boolean isRefundsAllowed() {
        return refundsAllowed;
    }

    public static double getGst() {
        return gst;
    }

    public void setAllowedRefunds(boolean allowedRefunds) {
        PaymentConfig.refundsAllowed = allowedRefunds;
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
        refundsAllowed = false;
        gst = 0.7;
    }
}
