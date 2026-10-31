/**
 * Represents a user who owns rental properties.
 * Which properties an owner has is tracked by PropertyManager.
 */
public class Owner extends User {

    public Owner(String userId, String name, String email, String phoneNumber, String password) {
        super(userId, name, email, phoneNumber, password);
    }

    public void viewFinancialStatements() {
        System.out.println("Financial statements are not available yet.");
    }
}
