/**
 * Stores the shared identity and account information for application users.
 */
public class User {
    private final String userId;
    private final String name;
    private final String email;
    private final String phoneNumber;
    private final String password;
    private boolean isActive;

    public User(String userId, String name, String email, String phoneNumber, String password) {
        if (userId == null || name == null || email == null || phoneNumber == null || password == null) {
            System.out.println("Error: User information cannot be null.");
            this.userId = "";
            this.name = "";
            this.email = "";
            this.phoneNumber = "";
            this.password = "";
            this.isActive = false;
            return;
        }
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.isActive = true;
    }

    protected User(User user) {
        if (user == null) {
            System.out.println("Error: User cannot be copied because it is null.");
            this.userId = "";
            this.name = "";
            this.email = "";
            this.phoneNumber = "";
            this.password = "";
            this.isActive = false;
            return;
        }
        this.userId = user.userId;
        this.name = user.name;
        this.email = user.email;
        this.phoneNumber = user.phoneNumber;
        this.password = user.password;
        this.isActive = user.isActive;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }
}
