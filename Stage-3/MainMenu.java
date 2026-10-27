import java.util.Scanner;

public class MainMenu implements Menu {
    private final Scanner scanner;
    private final UserManager userManager;
    private final SessionManager sessionManager;
    private final LoginHandler loginHandler;
    private final LogoutHandler logoutHandler;
    private boolean running;
    private UserSession currentSession;

    public MainMenu(Scanner scanner) {
        this.scanner = scanner;
        this.userManager = new UserManager();
        this.sessionManager = new SessionManager();
        this.loginHandler = new LoginHandler(userManager, sessionManager);
        this.logoutHandler = new LogoutHandler(sessionManager);
        this.running = true;
    }

    @Override 
    public void showMenu() {
        while (running) {
            displayOptions();
            if (!scanner.hasNextLine()) {
                running = false;
            } else {
                String input = scanner.nextLine();
                try {
                    processOption(Integer.parseInt(input));
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a number.");
                }
            }
        }
    }

    @Override
    public void displayOptions(){
        System.out.println("Main Menu:");
        System.out.println("1. Login");
        System.out.println("2. Register Applicant");
        System.out.println("3. Logout");
        System.out.println("4. Exit");
        System.out.print("Select an option: ");
    }

    @Override
    public void processOption(int option) {
        switch(option){
            case 1:
                login();
                break;
            case 2:
                registerApplicant();
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

        LoginMenu loginMenu = new LoginMenu(scanner, loginHandler);
        currentSession = loginMenu.showLogin();
        if (currentSession == null) {
            System.out.println("Invalid email or password.");
        } else {
            User user = userManager.findUserById(currentSession.getUserId());
            System.out.println("Welcome, " + user.getName() + ".");
        }
    }

    private void registerApplicant() {
        ApplicantRegistrationMenu registrationMenu =
                new ApplicantRegistrationMenu(scanner, userManager);
        registrationMenu.showRegistration();
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