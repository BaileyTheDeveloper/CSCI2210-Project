import java.util.Scanner;

/**
 * Starts the real estate management command-line application.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MainMenu mainMenu = new MainMenu(scanner);
        mainMenu.showMenu();
        scanner.close();
    }
}
