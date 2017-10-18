package view;

import exception.InputOutOfBoundsException;
import java.util.ArrayList;

public class Menu extends Form {

    public static final String INPUT_PROMPT = "Choose option";
    public static final String INVALID_ERROR = "Invalid user input! Please try again.";
    public static final String UNRECOGNIZED_ERROR = "Unrecognized user input! Please try again.";
    public static final String BACK_OPTION = "Go Back";
    public static final int BACK = -99999;

    protected boolean canGoBack;

    public Menu(String title, String[] options, boolean canGoBack) {
        super(title, options);
        this.canGoBack = canGoBack;
    }

    public Menu(String title, String[] options) {
        this(title, options, false);
    }

    public Menu(String title) {
        super(title);
    }

    public Menu(String title, boolean canGoBack) {
        super(title);
        this.canGoBack = canGoBack;
    }

    public Menu() {
        super();
    }

    public static String[] getDescriptions(MenuOption[] options) {
        ArrayList<String> descriptions = new ArrayList<>();
        for (int i = 0; i < options.length ; i++) {
            String description = options[i].getDescription();
            if (description != null)
                descriptions.add(description);
        }
        return descriptions.toArray(new String[descriptions.size()]);
    }

    public int getChoice() {
        while(true)
            try {
                return getInt(INPUT_PROMPT, 1, content.length + (canGoBack ? 1 : 0)) - 1;
            } catch (InputOutOfBoundsException e) {
                if (canGoBack && (int) e.getOutOfBoundsInput() == BACK)
                    return BACK;
                System.out.println(INVALID_ERROR);
            }
    }

    @Override
    public void display() {
        for (int i = 0; i < content.length; i++)
            System.out.println(Line.format(String.valueOf(i + 1),
                                           View.VERTICAL_SEPARATOR, content[i]));
        if (canGoBack)
            System.out.println(Line.format(String.valueOf(0),
                                           View.VERTICAL_SEPARATOR, BACK_OPTION));
        System.out.println(Line.format('-', View.VIEW_WIDTH));
    }
}
