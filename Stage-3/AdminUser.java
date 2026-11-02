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

    public void manageProperties() {
        System.out.println("Property management is handled by the property classes.");
    }

    public void generateFinancialReports() {
        System.out.println("Financial reports are not available yet.");
    }

    public void assignMaintenance() {
        System.out.println("Maintenance assignment is handled by the work order classes.");
    }
}
