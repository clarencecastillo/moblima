package manager;

import config.ConfigType;
import config.Configurable;
import java.util.Hashtable;

public class ConfigManager {

    private static ConfigManager instance;
    private Hashtable<ConfigType, Configurable> configurations;

    public ConfigManager() {
        configurations = new Hashtable<ConfigType, Configurable>();
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
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
