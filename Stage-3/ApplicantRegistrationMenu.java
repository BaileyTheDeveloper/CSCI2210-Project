import java.util.Scanner;

/**
 * Collects information and registers a new applicant account.
 */
public class ApplicantRegistrationMenu {
    private final Scanner scanner;
    private final UserManager userManager;

    public ApplicantRegistrationMenu(Scanner scanner, UserManager userManager) {
        this.scanner = scanner;
        this.userManager = userManager;
    }

    public void showRegistration() {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (userManager.registerApplicant(name, email, phoneNumber, password)) {
            System.out.println("Applicant registered successfully.");
        } else {
            System.out.println("An account with that email already exists.");
        }
    }
}
