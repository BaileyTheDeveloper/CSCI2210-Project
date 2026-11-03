import java.util.ArrayList;
import java.util.List;

/**
 * Stores and searches user accounts.
 */
public class UserManager {
    private final ArrayList<User> users;

    public UserManager() {
        this.users = new ArrayList<>();
    }

    public boolean addUser(User user) {
        if (user == null || findUserById(user.getUserId()) != null
                || findUserByEmail(user.getEmail()) != null) {
            return false;
        }
        users.add(user);
        return true;
    }

    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    public boolean removeUser(String userId) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId().equalsIgnoreCase(userId)) {
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
            if (users.get(i).getUserId().equalsIgnoreCase(userId)) {
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
        if (userId == null || updatedUser == null || !userId.equalsIgnoreCase(updatedUser.getUserId())) {
            System.out.println("Error: The updated user must have the same user ID.");
            return false;
        }
        int userIndex = -1;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId().equalsIgnoreCase(userId)) {
                userIndex = i;
                break;
            }
        }
        if (userIndex == -1) {
            System.out.println("Error: User was not found.");
            return false;
        }
        User emailMatch = findUserByEmail(updatedUser.getEmail());
        if (emailMatch != null && !emailMatch.getUserId().equalsIgnoreCase(userId)) {
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
}
