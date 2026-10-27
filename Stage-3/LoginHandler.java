/**
 * Checks login credentials and creates a session for a valid user.
 */
public class LoginHandler {
    private final UserManager userManager;
    private final SessionManager sessionManager;

    public LoginHandler(UserManager userManager, SessionManager sessionManager) {
        this.userManager = userManager;
        this.sessionManager = sessionManager;
    }

    /**
     * Creates a session when the email and password belong to an active user.
     *
     * @param email the user's email address
     * @param password the user's plain-text password
     * @return the new session, or null if the credentials are invalid
     */
    public UserSession executeLogin(String email, String password) {
        User user = userManager.authenticate(email, password);
        if (user == null) {
            return null;
        }
        return sessionManager.createSession(user.getUserId());
    }

    /**
     * Checks whether an active user has supplied the correct password.
     *
     * @param email the user's email address
     * @param password the user's plain-text password
     * @return true if the credentials are valid
     */
    public boolean validateCredentials(String email, String password) {
        return userManager.authenticate(email, password) != null;
    }
}
