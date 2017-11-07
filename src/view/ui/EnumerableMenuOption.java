package view.ui;

/**
 * Represents a base interface that must be implemented by all classes that contains enumerable menu options.
 *
 * @version 1.0
 * @since 2017-10-20
 */
public interface EnumerableMenuOption extends MenuOption {

    /**
     * Gets the name of this enumerable menu option.
     * @return the name of this enumerable menu option.
     */
    String name();
}
