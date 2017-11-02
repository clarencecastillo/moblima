package view.ui;

/**
 * Represents a base interface that must be implemented by all the navigable classes.
 *
 * @version 1.0
 * @since 2017-10-20
 */
public interface Navigable {

    /**
     * Loads the navigable view by the given access level, intent and other necessary information.
     * @param accessLevel The access level of this view by the user.
     * @param intent The intent of accessing this view.
     * @param args Necessary information to load this view.
     */
    void onLoad(AccessLevel accessLevel, Intent intent, String... args);

    /**
     * Things to be performed when entering this view.
     */
    void onEnter();
}
