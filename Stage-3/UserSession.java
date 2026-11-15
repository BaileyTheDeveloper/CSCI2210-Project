/**
 * Tracks the active state for one logged-in user.
 */
public class UserSession {
    private final String sessionId;
    private final String userId;
    private boolean isActive;

    public UserSession(String sessionId, String userId) {
        if (sessionId == null || userId == null) {
            System.out.println("Error: Session and user IDs cannot be null.");
            this.sessionId = "";
            this.userId = "";
            this.isActive = false;
            return;
        }
        this.sessionId = sessionId;
        this.userId = userId;
        this.isActive = true;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void invalidate() {
        isActive = false;
    }
}
