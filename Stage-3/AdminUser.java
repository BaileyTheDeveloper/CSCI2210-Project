/**
 * Represents a user with administrative responsibilities.
 */
public class AdminUser extends User {
    private final String employeeId;

    public AdminUser(String userId, String name, String email, String phoneNumber,
                     String password, String employeeId) {
        super(userId, name, email, phoneNumber, password);
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }
}
