import java.util.Date;

/**
 * Tracks the active state and times for one logged-in user.
 */
public class UserSession {
    private final String sessionId;
    private final String userId;
    private final Date loginTime;
    private Date lastActiveTime;
    private boolean isActive;

    public UserSession(String sessionId, String userId) {
        if (sessionId == null || userId == null) {
            System.out.println("Error: Session and user IDs cannot be null.");
            this.sessionId = "";
            this.userId = "";
            this.loginTime = new Date();
            this.lastActiveTime = new Date();
            this.isActive = false;
            return;
        }
        this.sessionId = sessionId;
        this.userId = userId;
        this.loginTime = new Date();
        this.lastActiveTime = new Date();
        this.isActive = true;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public Date getLoginTime() {
        return new Date(loginTime.getTime());
    }

    public Date getLastActiveTime() {
        return new Date(lastActiveTime.getTime());
    }

    public boolean isActive() {
        return isActive;
    }

    public void touch() {
        if (isActive) {
            lastActiveTime = new Date();
        }
    }

    public void invalidate() {
        isActive = false;
    }
}
