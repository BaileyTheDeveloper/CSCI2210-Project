import java.util.ArrayList;
import java.util.List;

/**
 * Creates, validates, and terminates user sessions.
 */
public class SessionManager {
    private final List<UserSession> activeSessions;
    private int nextSessionId;

    public SessionManager() {
        this.activeSessions = new ArrayList<>();
        this.nextSessionId = 1;
    }

    public UserSession createSession(String userId) {
        if (userId == null) {
            System.out.println("Error: User ID cannot be null.");
            return null;
        }
        String sessionId = "SESSION" + nextSessionId;
        nextSessionId++;
        UserSession session = new UserSession(sessionId, userId);
        activeSessions.add(session);
        return session;
    }

    public UserSession getSession(String sessionId) {
        if (sessionId == null) {
            return null;
        }
        for (int i = 0; i < activeSessions.size(); i++) {
            if (activeSessions.get(i).getSessionId().equals(sessionId)) {
                return activeSessions.get(i);
            }
        }
        return null;
    }

    public boolean terminateSession(String sessionId) {
        for (int i = 0; i < activeSessions.size(); i++) {
            if (activeSessions.get(i).getSessionId().equals(sessionId)) {
                UserSession session = activeSessions.remove(i);
                session.invalidate();
                return true;
            }
        }
        return false;
    }

    public boolean validateSession(String sessionId) {
        UserSession session = getSession(sessionId);
        if (session == null || !session.isActive()) {
            return false;
        }
        session.touch();
        return true;
    }
}
