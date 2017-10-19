package controller;

import java.util.AbstractMap.SimpleEntry;
import java.util.Stack;
import exception.RootControllerPopException;

public class Navigation {

    private Stack<SimpleEntry<Controller, String[]>> stack;
    private String[] lastArgs;

    public Navigation() {
        this.stack = new Stack<>();
    }

    public void reload() {
        SimpleEntry<Controller, String[]> stackTop = stack.peek();
        load(stackTop.getKey(), stackTop.getValue());
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void load(Controller controller, String... args) {
        lastArgs = args;
        clearScreen();
        controller.onEnter(args);
    }

    public void goTo(Controller controller, String... args) {
        stack.push(new SimpleEntry<>(controller, args));
        load(controller, args);
    }

    public void goBack(String... args) {
        goBack(1, args);
    }

    public void goBack(int levels, String... args) {
        clearScreen();
        if (stack.size() - levels == 0)
            throw new RootControllerPopException();
        for (int i = 0; i < levels; i++)
            stack.pop();
        SimpleEntry<Controller, String[]> stackTop = stack.peek();
        load(stackTop.getKey(), args.length == 0 ? stackTop.getValue() : args);
    }
}
