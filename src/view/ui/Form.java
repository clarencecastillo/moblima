package view.ui;

import java.io.Console;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public interface Form {

    String PROMPT_DELIMETER = " > ";
    String DATE_FORMAT = "dd/MM/yyyy HH:mm";

    String INVALID_ERROR = "Invalid user input! Please try again.";
    String UNRECOGNIZED_ERROR = "Unrecognized user input! Please try again.";

    String CONFIRM = "CONFIRM";
    String CANCEL = "CANCEL";

    static int getIntWithMax(String prompt, int max) {
        while (true) {
            int input = getInt(prompt + " [ <=" + max + " ]");
            if (input <= max)
                return input;
            else
                View.displayError(INVALID_ERROR);
        }

    }

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
            System.out.print(prompt + PROMPT_DELIMETER);
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
            System.out.print(prompt + PROMPT_DELIMETER);
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
        return new String(console.readPassword(prompt + PROMPT_DELIMETER));
    }

    static String getString(String prompt) {
        return getString(prompt, 1);
    }

    static String getString(String prompt, int minWords) {

        while (true) {
            System.out.print(prompt + PROMPT_DELIMETER);
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

    static String getConfirmOption(String confirmText, String cancelText) {
        return getOption("Confirmation", new GenericMenuOption(confirmText, CONFIRM),
                new GenericMenuOption(cancelText, CANCEL));
    }

    static char getChar(String prompt) {
        while (true) {
            System.out.print(prompt + PROMPT_DELIMETER);
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
            System.out.print(prompt + " [ " + format + " ]" + PROMPT_DELIMETER);
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
