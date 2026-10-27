/**
 * Ends a user's active session.
 */
public class LogoutHandler {
    private final SessionManager sessionManager;

    public LogoutHandler(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public boolean executeLogout(String sessionId) {
        return sessionManager.terminateSession(sessionId);
    }
}
