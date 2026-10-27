import java.util.Scanner;

public class LoginMenu {

    private final Scanner scanner;
    private final LoginHandler loginHandler;

    public LoginMenu(Scanner scanner, LoginHandler loginHandler) {
        this.scanner = scanner;
        this.loginHandler = loginHandler;
    }

    public UserSession showLogin() {

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        return loginHandler.executeLogin(email, password);
    }
}