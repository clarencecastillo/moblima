package view.ui;

import exception.RejectedNavigationException;
import exception.RootControllerPopException;

import java.util.Stack;

/**
 * Represents a navigation history by the user.
 *
 * @version 1.0
 * @since 2017-10-20
 */
public class Navigation {

    /**
     * A stack of navigable views visited by the user in this navigation.
     */
    private Stack<Navigable> stack;

    /**
     * Creates the navigation history by initializing an empty stack.
     */
    public Navigation() {
        this.stack = new Stack<>();
    }

    /**
     * Reloads the navigation view with the given access level, intent and necessary information.
     * @param accessLevel The access level of this view by the user.
     * @param intent The intent of accessing this view.
     * @param args The necessary information to reload this navigation view.
     */
    public void reload(AccessLevel accessLevel, Intent intent, String... args) {
        Navigable recentNavigable = stack.peek();
        clearScreen();
        recentNavigable.onLoad(accessLevel, intent, args);
        enter(recentNavigable);
    }

    /**
     * Reloads the navigation view with necessary information that can be accessed by the public.
     * @param args The necessary information to reload this navigation view.
     */
    public void reload(String... args) {
        reload(AccessLevel.PUBLIC, null, args);
    }

    /**
     * Reloads the navigation view with the given access level and necessary information and no intent.
     * @param accessLevel The access level of this view by the user.
     * @param args The necessary information to reload this navigation view.
     */
    public void reload(AccessLevel accessLevel, String... args) {
        reload(accessLevel, null, args);
    }

    /**
     * Refreshes a view by entering the most recently view that is on the top of the navigation stack.
     */
    public void refresh() {
        enter(stack.peek());
    }

    /**
     * Clear the screen and enter the given navigable view.
     * @param navigable The navigable view to be entered.
     */
    public void enter(Navigable navigable) {
        clearScreen();
        navigable.onEnter();
    }

    /**
     * Clear the screen by flushing.
     */
    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Go to the given navigable view with the given access level, necessary information when no intent.
     * @param navigable The navigable view to go to.
     * @param accessLevel The access level of the navigable view by the user.
     * @param args The necessary information for loading the navigable view.
     */
    public void goTo(Navigable navigable, AccessLevel accessLevel, String... args) {
        goTo(navigable, accessLevel, null, args);
    }

    /**
     * Go to the given navigable view with the given access level, intent and necessary information.
     * @param navigable The navigable view to go to.
     * @param accessLevel The access level of the navigable view by the user.
     * @param intent The intent of accessing this navigable view.
     * @param args The necessary information for loading the navigable view.
     */
    public void goTo(Navigable navigable, AccessLevel accessLevel, Intent intent, String... args) {
        clearScreen();
        stack.push(navigable);
        try {
            navigable.onLoad(accessLevel, intent, args);
            enter(navigable);
        } catch (RejectedNavigationException e) {
            goBack();
        }
    }

    /**
     * Go to the given navigable view with the public access level, no intent and necessary information.
     * @param navigable The navigable view to go to.
     * @param args The necessary information for loading the navigable view.
     */
    public void goTo(Navigable navigable, String... args) {
        goTo(navigable, AccessLevel.PUBLIC, null, args);
    }

    /**
     * Go back to the previous view.
     */
    public void goBack() { goBack(1); }

    /**
     * Go back to the view which is before the current view by the given number of levels.
     * @param levels The number of views by which the returned view is before the current view.
     */
    public void goBack(int levels) {
        if (stack.size() - levels == 0)
            throw new RootControllerPopException();
        for (int i = 0; i < levels; i++)
            stack.pop();
        enter(stack.peek());
    }
}
