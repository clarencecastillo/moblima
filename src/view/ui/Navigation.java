package view.ui;

import exception.NavigationRejectedException;
import exception.RootControllerPopException;
import java.util.Stack;

public class Navigation {

    private Stack<Navigable> stack;

    public Navigation() {
        this.stack = new Stack<>();
    }

    public void reload(NavigationIntent intent, String... args) {
        Navigable recentNavigable = stack.peek();
        recentNavigable.onLoad(intent, args);
        enter(recentNavigable);
    }

    public void reload(String... args) {
        reload(null, args);
    }

    public void enter(Navigable navigable) {
        clearScreen();
        navigable.onEnter();
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void goTo(Navigable navigable, NavigationIntent intent, String... args) {
        clearScreen();
        stack.push(navigable);
        try {
            navigable.onLoad(intent, args);
            enter(navigable);
        } catch (NavigationRejectedException e) {
            goBack();
        }
    }

    public void goTo(Navigable navigable, String... args) {
        goTo(navigable, null, args);
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
