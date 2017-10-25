package view.ui;

import exception.NavigationRejectedException;
import exception.RootControllerPopException;

import java.util.Stack;

public class Navigation {

    private Stack<Navigable> stack;

    public Navigation() {
        this.stack = new Stack<>();
    }

    public void reload(AccessLevel accessLevel, Intent intent, String... args) {
        Navigable recentNavigable = stack.peek();
        recentNavigable.onLoad(accessLevel, intent, args);
        enter(recentNavigable);
    }

    public void reload(String... args) {
        reload(AccessLevel.PUBLIC, null, args);
    }

    public void reload(AccessLevel accessLevel, String... args) {
        reload(accessLevel, null, args);
    }

    public void refresh() {
        enter(stack.peek());
    }

    public void enter(Navigable navigable) {
        clearScreen();
        navigable.onEnter();
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void goTo(Navigable navigable, AccessLevel accessLevel, String... args) {
        goTo(navigable, accessLevel, null, args);
    }

    public void goTo(Navigable navigable, AccessLevel accessLevel, Intent intent, String... args) {
        clearScreen();
        stack.push(navigable);
        try {
            navigable.onLoad(accessLevel, intent, args);
            enter(navigable);
        } catch (NavigationRejectedException e) {
            goBack();
        }
    }

    public void goTo(Navigable navigable, String... args) {
        goTo(navigable, AccessLevel.PUBLIC, null, args);
    }

    public void goBack() {
        goBack(1);
    }

    public void goBack(int levels) {
        if (stack.size() - levels == 0)
            throw new RootControllerPopException();
        for (int i = 0; i < levels; i++)
            stack.pop();
        enter(stack.peek());
    }
}
