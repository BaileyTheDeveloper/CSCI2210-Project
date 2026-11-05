import java.util.Scanner;

/**
 * Defines the methods used by application menus. A menu that provides getScanner gets the
 * repeat-until-0 menu loop and the prompt helpers for free.
 */
public interface Menu {
    void displayOptions();
    void processOption(int option);
    Scanner getScanner();

    /**
     * Repeats the menu until the user picks option 0 or the input ends.
     */
    default void showMenu() {
        Scanner scanner = getScanner();
        while (true) {
            displayOptions();
            if (!scanner.hasNextLine()) {
                return;
            }
            try {
                int option = Integer.parseInt(scanner.nextLine().trim());
                if (option == 0) {
                    return;
                }
                processOption(option);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    default String prompt(String label) {
        System.out.print(label + ": ");
        Scanner scanner = getScanner();
        return scanner.hasNextLine() ? scanner.nextLine().trim() : "";
    }

    /**
     * Prompts the user repeatedly until a valid whole number is entered.
     */
    default int promptInt(String label) {
        while (true) {
            String input = prompt(label);
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid whole number.");
            }
        }
    }

    /**
     * Prompts the user repeatedly until a valid decimal number is entered.
     */
    default double promptDouble(String label) {
        while (true) {
            String input = prompt(label);
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}
