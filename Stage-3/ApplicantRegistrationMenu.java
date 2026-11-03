import java.util.Scanner;

/**
 * Collects information and registers a new applicant account.
 */
public class ApplicantRegistrationMenu {
    private final Scanner scanner;
    private final RegistrationService registrationService;

    public ApplicantRegistrationMenu(Scanner scanner, RegistrationService registrationService) {
        this.scanner = scanner;
        this.registrationService = registrationService;
    }

    public void showRegistration() {
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Phone number: ");
        String phoneNumber = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        if (name.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || password.isEmpty()) {
            System.out.println("All fields are required.");
            return;
        }

        if (registrationService.registerApplicant(name, email, phoneNumber, password)) {
            System.out.println("Applicant registered successfully.");
        } else {
            System.out.println("An account with that email already exists.");
        }
    }
}
