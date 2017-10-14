package config;

public class PaymentConfig implements Configurable {

    private static PaymentConfig instance;
    private static boolean refundsAllowed;
    private static double gst;

    private PaymentConfig() {
        reset();
    }

    public static PaymentConfig getInstance() {
        if (instance == null)
            instance = new PaymentConfig();
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
        return null;
    }

    @Override
    public void reset() {
        refundsAllowed = false;
        gst = 0.7;
    }
}
