import java.util.List;
import java.util.Scanner;

/**
 * Lets an admin create, view, edit, and delete user accounts (CRUD).
 */
public class UserManagementMenu implements Menu {
    private final Scanner scanner;
    private final RegistrationService registrationService;
    private final UserManager userManager;

    public UserManagementMenu(Scanner scanner, RegistrationService registrationService, UserManager userManager) {
        this.scanner = scanner;
        this.registrationService = registrationService;
        this.userManager = userManager;
    }

    @Override
    public Scanner getScanner() {
        return scanner;
    }

    @Override
    public void displayOptions() {
        System.out.println("\nUser Account Management:");
        System.out.println("1. Create Owner account");
        System.out.println("2. Create Maintenance Staff account");
        System.out.println("3. List all accounts");
        System.out.println("4. Edit user information");
        System.out.println("5. Delete user account");
        System.out.println("0. Back");
        System.out.print("Select an option: ");
    }

    @Override
    public void processOption(int option) {
        switch (option) {
            case 1:
                createAccount(1);
                break;
            case 2:
                createAccount(2);
                break;
            case 3:
                listAccounts();
                break;
            case 4:
                editUser();
                break;
            case 5:
                deleteUser();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private void createAccount(int option) {
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

    private void listAccounts() {
        List<User> users = userManager.getUsers();
        if (users.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }
        for (User u : users) {
            String role = u.getClass().getSimpleName();
            System.out.printf("  ID: %s | Role: %-16s | Name: %-15s | Email: %s%n",
                    u.getUserId(), role, u.getName(), u.getEmail());
        }
    }

    private void editUser() {
        String userId = prompt("User ID to edit");
        User existing = userManager.findUserById(userId);
        if (existing == null) {
            System.out.println("User not found.");
            return;
        }

        String name = prompt("New Name (leave blank to keep: " + existing.getName() + ")");
        String email = prompt("New Email (leave blank to keep: " + existing.getEmail() + ")");
        String phone = prompt("New Phone (leave blank to keep: " + existing.getPhoneNumber() + ")");
        String password = prompt("New Password (leave blank to keep current)");

        name = name.isEmpty() ? existing.getName() : name;
        email = email.isEmpty() ? existing.getEmail() : email;
        phone = phone.isEmpty() ? existing.getPhoneNumber() : phone;
        String pass = password.isEmpty() ? "keep" : password; 

        // Preserve subclass type when updating
        User updatedUser;
        if (existing instanceof AdminUser) {
            AdminUser admin = (AdminUser) existing;
            updatedUser = new AdminUser(userId, name, email, phone, pass.equals("keep") ? admin.getEmail() : pass, admin.getEmployeeId());
        } else if (existing instanceof Owner) {
            updatedUser = new Owner(userId, name, email, phone, pass);
        } else if (existing instanceof MaintenanceStaff) {
            MaintenanceStaff staff = (MaintenanceStaff) existing;
            updatedUser = new MaintenanceStaff(userId, name, email, phone, pass, staff.getSpecialty());
        } else if (existing instanceof Applicant) {
            Applicant app = (Applicant) existing;
            updatedUser = new Applicant(userId, name, email, phone, pass, app.getApplicantID());
        } else if (existing instanceof Tenant) {
            Tenant t = (Tenant) existing;
            updatedUser = new Tenant(userId, name, email, phone, pass, t.getTenantId(), t.getCurrentUnit());
        } else {
            updatedUser = new User(userId, name, email, phone, pass);
        }

        if (userManager.editUser(userId, updatedUser)) {
            System.out.println("User updated successfully.");
        } else {
            System.out.println("Failed to update user.");
        }
    }

    private void deleteUser() {
        String userId = prompt("User ID to delete");
        if (userManager.removeUser(userId)) {
            System.out.println("User " + userId + " removed successfully.");
        } else {
            System.out.println("User not found.");
        }
    }
}