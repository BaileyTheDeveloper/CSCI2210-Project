import java.util.Scanner;

/**
 * Lets an admin create owner and maintenance staff accounts.
 */
public class StaffAccountMenu implements Menu {
    private final Scanner scanner;
    private final RegistrationService registrationService;

    public StaffAccountMenu(Scanner scanner, RegistrationService registrationService) {
        this.scanner = scanner;
        this.registrationService = registrationService;
    }

    @Override
    public Scanner getScanner() {
        return scanner;
    }

    @Override
    public void displayOptions() {
        System.out.println("\nCreate Account:");
        System.out.println("1. Owner");
        System.out.println("2. Maintenance staff");
        System.out.println("0. Back");
        System.out.print("Select an option: ");
    }

    @Override
    public void processOption(int option) {
        if (option != 1 && option != 2) {
            System.out.println("Invalid option.");
            return;
        }
        String name = prompt("Name");
        String email = prompt("Email");
        String phone = prompt("Phone number");
        String password = prompt("Password");
        String specialty = option == 2 ? prompt("Specialty") : "";
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            System.out.println("All fields are required.");
            return;
        }

        User created = option == 1
                ? registrationService.registerOwner(name, email, phone, password)
                : registrationService.registerMaintenanceStaff(name, email, phone, password, specialty);
        if (created == null) {
            System.out.println("An account with that email already exists.");
        } else {
            System.out.println("Account created with user ID " + created.getUserId() + ".");
        }
    }
}
