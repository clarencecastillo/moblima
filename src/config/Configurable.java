package moblima.config;

import java.io.Serializable;

/**
 * Represents a base interface that must be implemented bu all the classes that are configurable by the admin.
 *
 * @version 1.0
 * @since 2017-10-20
 */
public interface Configurable extends Serializable {

    /**
     * Gets the type of this configurable.
     *
     * @return the type of this configurable.
     */
    ConfigType getConfigType();

    /**
     * Resets the configuration of this configurable to the default.
     */
    void reset();

}
