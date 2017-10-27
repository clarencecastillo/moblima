package view.ui;

/**
 * Represents a base interface that must be implemented by all the navigable classes.
 *
 * @author Castillo Clarence Fitzgerald Gumtang
 * @version 1.0
 * @since 2017-10-20
 */
public interface Navigable {

    void onLoad(AccessLevel accessLevel, Intent intent, String... args);

    void onEnter();
}
