public class User {
    private final String userId;
    private String name;
    private String email;
    private String phoneNumber;
    private String passwordHash;
    private boolean isActive;

    // Constructor initializing all required fields and setting isActive to true by default
    public User(String userId, String name, String email, String phoneNumber, String passwordHash) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.passwordHash = passwordHash;
        this.isActive = true; 
    }

    // Getter for final field userId (no setter to protect identity integrity)
    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Standard setter for updating credentials safely
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    // Methods to manage status that subclasses might need to check
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    // Method to validate password matching logic
    public boolean validatePassword(String password) {
        // Placeholder check: update this with your actual hashing framework logic (e.g., BCrypt)
        return this.passwordHash != null && this.passwordHash.equals(password);
    }
}
