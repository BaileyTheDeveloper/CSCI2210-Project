import java.util.Scanner;

/**
 * Shows the start-up menu: log in, register as an applicant, log out and exit.
 * After a login it opens the menu that matches the user's role.
 */
public class MainMenu implements Menu {
    private final Scanner scanner;
    private final UserManager userManager;
    private final SessionManager sessionManager;
    private final RegistrationService registrationService;
    private final PropertyManager propertyManager;
    private final ApplicationManager applicationManager;
    private final ApplicationApprovalService approvalService;
    private final LeaseManager leaseManager;
    private final WorkOrderManager workOrderManager;
    private final ReportService reportService;
    private final LoginHandler loginHandler;
    private final LogoutHandler logoutHandler;
    private boolean running;
    private UserSession currentSession;

    public MainMenu(Scanner scanner) {
        this.scanner = scanner;
        this.userManager = new UserManager();
        this.sessionManager = new SessionManager();
        this.registrationService = new RegistrationService(userManager);
        this.propertyManager = new PropertyManager();
        this.applicationManager = new ApplicationManager(userManager);
        this.leaseManager = new LeaseManager();
        this.workOrderManager = new WorkOrderManager(userManager);
        this.approvalService = new ApplicationApprovalService(
                applicationManager, userManager, propertyManager, leaseManager);
        this.reportService = new ReportService(propertyManager, leaseManager);
        this.loginHandler = new LoginHandler(userManager, sessionManager);
        this.logoutHandler = new LogoutHandler(sessionManager);
        this.running = true;

        new SampleData(userManager, registrationService, propertyManager, applicationManager,
                approvalService, leaseManager, workOrderManager).load();
    }

    @Override
    public Scanner getScanner() {
        return scanner;
    }

    @Override
    public void showMenu() {
        while (running) {
            displayOptions();
            if (!scanner.hasNextLine()) {
                running = false;
            } else {
                String line = scanner.nextLine();
                try {
                    processOption(Integer.parseInt(line));
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a number.");
                }
            }
        }
    }

    @Override
    public void displayOptions() {
        System.out.println("Main Menu:");
        System.out.println("1. Login");
        System.out.println("2. Register Applicant");
        System.out.println("3. Logout");
        System.out.println("4. Exit");
        System.out.print("Select an option: ");
    }

    @Override
    public void processOption(int option) {
        switch (option) {
            case 1:
                login();
                break;
            case 2:
                new ApplicantRegistrationMenu(scanner, registrationService).showRegistration();
                break;
            case 3:
                logout();
                break;
            case 4:
                if (currentSession != null) {
                    logout();
                }
                System.out.println("Goodbye!");
                running = false;
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private void login() {
        if (currentSession != null && sessionManager.validateSession(currentSession.getSessionId())) {
            System.out.println("A user is already logged in.");
            return;
        }

        currentSession = new LoginMenu(scanner, loginHandler).showLogin();
        if (currentSession == null) {
            System.out.println("Invalid email or password.");
            return;
        }

        User user = userManager.findUserById(currentSession.getUserId());
        System.out.println("Welcome, " + user.getName() + ".");
        openRoleMenu(user);
        logout();
    }

    private void openRoleMenu(User user) {
        Menu menu;
        if (user instanceof AdminUser) {
            AdminUser admin = (AdminUser) user;
            menu = new AdminMenu(scanner,
                    new PropertyAdminMenu(scanner, propertyManager, userManager),
                    new StaffAccountMenu(scanner, registrationService),
                    new ApplicationReviewMenu(scanner, applicationManager, approvalService, admin),
                    new WorkOrderAdminMenu(scanner, workOrderManager, userManager, admin),
                    reportService);
        } else if (user instanceof Owner) {
            menu = new OwnerMenu(scanner, propertyManager, workOrderManager,
                    reportService, (Owner) user);
        } else if (user instanceof MaintenanceStaff) {
            menu = new MaintenanceMenu(scanner, workOrderManager, (MaintenanceStaff) user);
        } else if (user instanceof Tenant) {
            menu = new TenantMenu(scanner, leaseManager, workOrderManager, (Tenant) user);
        } else if (user instanceof Applicant) {
            menu = new ApplicantMenu(scanner, propertyManager, applicationManager, (Applicant) user);
        } else {
            return;
        }
        menu.showMenu();
    }

    private void logout() {
        if (currentSession == null || !logoutHandler.executeLogout(currentSession.getSessionId())) {
            System.out.println("There is no active session to log out.");
        } else {
            System.out.println("You have been logged out.");
        }
        currentSession = null;
    }
}
