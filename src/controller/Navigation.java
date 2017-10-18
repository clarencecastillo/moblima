package controller;

import java.util.Stack;
import exception.RootControllerPopException;
import view.View;

public class Navigation {

    private Stack<Controller> stack;

    public Navigation() {
        this.stack = new Stack<Controller>();
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void goTo(Controller controller, String... args) {
        clearScreen();
        stack.push(controller);
        controller.onLoad(args);
        View controllerView = controller.getView();
        controllerView.displayHeader();
        controllerView.display();
        controller.onViewDisplay();
    }

    public void goBack(String... args) {
        goBack(1);
    }

    public void goBack(int levels, String... args) {
        clearScreen();
        if (stack.size() - levels == 0)
            throw new RootControllerPopException();
        for (int i = 0; i < levels; i++)
            stack.pop();
        Controller controller = stack.peek();
        controller.onLoad(args);
        View controllerView = controller.getView();
        controllerView.displayHeader();
        controllerView.display();
        controller.onViewDisplay();
    }

    public void goBackUpdate(Controller controller, String... args) {
        if (stack.size() == 1)
            throw new RootControllerPopException();
        stack.pop();
        stack.pop();
        goTo(controller, args);
    }
}
