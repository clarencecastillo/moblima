package config;

/**
 Represents the configuration of admin settings.
 @author Castillo Clarence Fitzgerald Gumtang
 @version 1.0
 @since 2017-10-20
 */
public class AdminConfig implements Configurable {

    /**
     * A reference to this singleton instance.
     */
    private static AdminConfig instance = new AdminConfig();

    /**
     * The string that must be entered to go to admin menu which is hidden from the main menu. Only the cinema staff
     * has access to the admin menu with this admin secret.
     */
    private static String adminSecret;

    /**
     * Initializes the admin configuration by resetting.
     */
    private AdminConfig() {
        reset();
    }

    /**
     * Gets this AdminConfig's singleton instance.
     * @return this AdminConfig's singleton instance.
     */
    public static AdminConfig getInstance() {
        return instance;
    }

    /**
     * Gets the type of this configuration.
     * @return the type of this configuration which is admin.
     */
    @Override
    public ConfigType getConfigType() {
        return ConfigType.ADMIN;
    }

    /**
     * Gets the admin secret.
     * @return the admin secret.
     */
    public static String getAdminSecret() {
        return adminSecret;
    }

    /**
     * Changes the admin secret.
     * @param adminSecret The new admin secret used to enter the admin menu.
     */
    public void setAdminSecret(String adminSecret) {
        AdminConfig.adminSecret = adminSecret;
    }

    /**
     * Resets the admin secret to "admin".
     */
    @Override
    public void reset() {
        adminSecret = "admin";
    }
}
