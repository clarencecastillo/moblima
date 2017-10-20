package manager;

import config.ConfigType;
import config.Configurable;
import java.util.Hashtable;

public class ConfigController {

    private static ConfigController instance;
    private Hashtable<ConfigType, Configurable> configurations;

    public ConfigController() {
        configurations = new Hashtable<ConfigType, Configurable>();
    }

    public static ConfigController getInstance() {
        if (instance == null) {
            instance = new ConfigController();
        }
        return instance;
    }

    public Configurable getConfig(ConfigType type) {
        return configurations.get(type);
    }

    public void setConfig(Configurable configuration) {
        configurations.put(configuration.getConfigType(), configuration);
    }
}
