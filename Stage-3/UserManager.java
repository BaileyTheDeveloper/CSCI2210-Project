import java.util.ArrayList;
import java.util.List;

/**
 * Stores and searches user accounts.
 */
public class UserManager {
    private final ArrayList<User> users;
    private int nextUserId;
    private int nextApplicantId;

    public UserManager() {
        this.users = new ArrayList<>();
        this.nextUserId = 1;
        this.nextApplicantId = 1;
    }

    public boolean addUser(User user) {
        if (user == null || findUserById(user.getUserId()) != null
                || findUserByEmail(user.getEmail()) != null) {
            return false;
        }
        users.add(user);
        return true;
    }

    public boolean registerApplicant(String name, String email, String phoneNumber, String password) {
        if (findUserByEmail(email) != null) {
            return false;
        }

        String userId = "USER" + nextUserId;
        while (findUserById(userId) != null) {
            nextUserId++;
            userId = "USER" + nextUserId;
        }

        String applicantId = "APP" + nextApplicantId;
        Applicant applicant = new Applicant(userId, name, email, phoneNumber, password, applicantId);
        if (!addUser(applicant)) {
            return false;
        }

        nextUserId++;
        nextApplicantId++;
        return true;
    }

    public boolean removeUser(String userId) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId().equals(userId)) {
                users.remove(i);
                return true;
            }
        }
        return false;
    }

    public User findUserById(String userId) {
        if (userId == null) {
            return null;
        }
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId().equals(userId)) {
                return users.get(i);
            }
        }
        return null;
    }

    public User findUserByEmail(String email) {
        if (email == null) {
            return null;
        }
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }

    public boolean editUser(String userId, User updatedUser) {
        if (userId == null || updatedUser == null || !userId.equals(updatedUser.getUserId())) {
            System.out.println("Error: The updated user must have the same user ID.");
            return false;
        }
        int userIndex = -1;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId().equals(userId)) {
                userIndex = i;
                break;
            }
        }
        if (userIndex == -1) {
            System.out.println("Error: User was not found.");
            return false;
        }
        User emailMatch = findUserByEmail(updatedUser.getEmail());
        if (emailMatch != null && !emailMatch.getUserId().equals(userId)) {
            System.out.println("Error: Another user already has that email address.");
            return false;
        }
        users.set(userIndex, updatedUser);
        return true;
    }

    public List<User> searchUsers(String query) {
        List<User> results = new ArrayList<>();
        if (query == null || query.trim().isEmpty()) {
            return results;
        }

        String searchText = query.toLowerCase();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getUserId().toLowerCase().contains(searchText)
                    || user.getName().toLowerCase().contains(searchText)
                    || user.getEmail().toLowerCase().contains(searchText)
                    || user.getPhoneNumber().toLowerCase().contains(searchText)) {
                results.add(user);
            }
        }
        return results;
    }

    public User authenticate(String email, String password) {
        User user = findUserByEmail(email);
        if (user != null && user.isActive() && user.validatePassword(password)) {
            return user;
        }
        return null;
    }
}
