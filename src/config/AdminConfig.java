package config;

public class AdminConfig implements Configurable {

    private static AdminConfig instance = new AdminConfig();
    private static String adminSecret;

    private AdminConfig() {
        reset();
    }

    public static AdminConfig getInstance() {
        return instance;
    }

    @Override
    public ConfigType getConfigType() {
        return ConfigType.ADMIN;
    }

    public static String getAdminSecret() {
        return adminSecret;
    }

    public void setAdminSecret(String adminSecret) {
        AdminConfig.adminSecret = adminSecret;
    }

    @Override
    public void reset() {
        adminSecret = "admin";
    }
}
