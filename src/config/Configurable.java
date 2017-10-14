package config;

import java.io.Serializable;

public interface Configurable extends Serializable {

    public ConfigType getConfigType();
    public void reset();

}
