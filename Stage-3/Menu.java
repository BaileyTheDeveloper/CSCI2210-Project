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
     * @return the number entered, or -1 if the answer is not a whole number
     */
    default int promptInt(String label) {
        try {
            return Integer.parseInt(prompt(label));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * @return the number entered, or -1 if the answer is not a number
     */
    default double promptDouble(String label) {
        try {
            return Double.parseDouble(prompt(label));
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}