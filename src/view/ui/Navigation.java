package view.ui;

import exception.RootControllerPopException;
import java.util.Stack;

public class Navigation {

    private Stack<Navigable> stack;

    public Navigation() {
        this.stack = new Stack<>();
    }

    public void enter(Navigable navigable) {
        clearScreen();
        navigable.onEnter();
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void goTo(Navigable navigable, String... args) {
        stack.push(navigable);
        navigable.onLoad(args);
        enter(navigable);
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
