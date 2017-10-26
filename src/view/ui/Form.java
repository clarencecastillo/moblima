package view.ui;

import java.io.Console;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Represents a base interface that must be implemented by all classes that are user interfaces.
 * It has static methods that are useful for getting inputs.
 *
 * @author Castillo Clarence Fitzgerald Gumtang
 * @version 1.0
 * @since 2017-10-20
 */
public interface Form {

    /**
     * The delimiter for prompt.
     */
    String PROMPT_DELIMITER = " > ";

    /**
     * The format of date.
     */
    String DATE_FORMAT = "dd/MM/yyyy HH:mm";

    /**
     * The message to be displayed with the user input is invalid.
     */
    String INVALID_ERROR = "Invalid user input! Please try again.";

    /**
     * message to be displayed with the user input is unrecognized.
     */
    String UNRECOGNIZED_ERROR = "Unrecognized user input! Please try again.";

    /**
     * Gets integer input which has a maximum limit. A invalid error message will be displayed
     * if the input integer exceeding the maximum limit.
     * @param prompt The message to prompt the user to enter input.
     * @param max The maximum limit that the integer cannot be.
     * @return The integer input from the user which is not larger than the maximum limit.
     */
    static int getIntWithMax(String prompt, int max) {
        while (true) {
            int input = getInt(prompt + " [ <=" + max + " ]");
            if (input <= max)
                return input;
            else
                View.displayError(INVALID_ERROR);
        }

    }

    /**
     * Gets integer input which has a minimum limit. A invalid error message will be displayed
     * if the input integer is below the minimum limit.
     * @param prompt The message to prompt the user to enter input.
     * @param min The minimum limit that the integer can be.
     * @return The integer input from the user which is smaller than the minimum limit.
     */
    static int getIntWithMin(String prompt, int min) {
        while (true) {
            int input = getInt(prompt + " [ >=" + min + " ]");
            if (input >= min)
                return input;
            else
                View.displayError(INVALID_ERROR);
        }

    }

    static int getInt(String prompt) {
        while (true) {
            System.out.print(prompt + PROMPT_DELIMITER);
            Scanner sc = new Scanner(System.in);
            try {
                return sc.nextInt();
            } catch (InputMismatchException e) {
                View.displayError(UNRECOGNIZED_ERROR);
            }
        }
    }

    static int getInt(String prompt, int min, int max) {
        while (true) {
            int input = getInt(prompt + " [ " + min + "-" + max + " ]");
            if (input >= min && input <= max)
                return input;
            else
                View.displayError(INVALID_ERROR);
        }

    }

    static double getDouble(String prompt) {

        while (true) {
            System.out.print(prompt + PROMPT_DELIMITER);
            Scanner sc = new Scanner(System.in);
            try {
                return sc.nextDouble();
            } catch (InputMismatchException e) {
                View.displayError(UNRECOGNIZED_ERROR);
            }
        }
    }

    static double getDouble(String prompt, double min, double max) {
        while (true) {
            double input = getDouble(prompt + " [ " + min + "-" + max + " ]");
            if (input >= min && input <= max)
                return input;
            else
                View.displayError(INVALID_ERROR);
        }

    }

    static double getDoubleWithMin(String prompt, double min) {
        while (true) {
            double input = getDouble(prompt + " [ <=" + min + " ]");
            if (input >= min)
                return input;
            else
                View.displayError(INVALID_ERROR);
        }
    }

    static String getCensoredString(String prompt) {
        Console console = System.console();
        return new String(console.readPassword(prompt + PROMPT_DELIMITER));
    }

    static String getString(String prompt) {
        return getString(prompt, 1);
    }

    static String getString(String prompt, int minWords) {

        while (true) {
            System.out.print(prompt + PROMPT_DELIMITER);
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();
            if (minWords == 0)
                return input;
            else if (!input.trim().equals("") && input.split(" ").length >= minWords)
                return input;
            else
                View.displayError(INVALID_ERROR);
        }
    }

    static String getOption(String prompt, GenericMenuOption... genericMenuOptions) {

        char itemIndex = 'A';
        MenuItem[] options = new MenuItem[genericMenuOptions.length];
        for (int i = 0; i < genericMenuOptions.length; i++, itemIndex++) {
            String description = genericMenuOptions[i].getDescription();
            if (description != null) {
                options[i] = new MenuItem(description, genericMenuOptions[i].getValue());
                options[i].display(itemIndex);
            }
        }

        int index = Form.getChar(prompt, 'A', (char) ('A' + options.length - 1)) - 'A';
        return options[index].getValue();
    }

    static String getOption(String prompt, EnumerableMenuOption... enumerableMenuOptions) {
        return getOption(prompt, Arrays.stream(enumerableMenuOptions).map(menuOption ->
                new GenericMenuOption(menuOption.getDescription(),
                        menuOption.name())).toArray(GenericMenuOption[]::new));
    }

    static char getChar(String prompt) {
        while (true) {
            System.out.print(prompt + PROMPT_DELIMITER);
            Scanner sc = new Scanner(System.in);
            String input = sc.next();
            if (input.length() == 1)
                return input.charAt(0);
            else
                View.displayError(UNRECOGNIZED_ERROR);
        }


    }

    static char getChar(String prompt, char min, char max) {
        while (true) {
            char input = getChar(prompt + " [ " + min + "-" + max + " ]");
            if (input >= min && input <= max)
                return input;
            else
                View.displayError(INVALID_ERROR);
        }
    }

    static Date getDate(String prompt, String format) {
        while (true) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            System.out.print(prompt + " [ " + format + " ]" + PROMPT_DELIMITER);
            Scanner sc = new Scanner(System.in);
            try {
                return dateFormat.parse(sc.nextLine());
            } catch (ParseException e) {
                View.displayError(UNRECOGNIZED_ERROR);
            }
        }

    }

    static void pressAnyKeyToContinue() {
        System.out.print("Press ENTER key to continue...");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
    }
}
