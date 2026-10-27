/**
 * Represents a user who performs maintenance work.
 * Which work orders are assigned to this person is tracked by PropertyManager.
 */
public class MaintenanceStaff extends User {
    private final String specialty;

    public MaintenanceStaff(String userId, String name, String email, String phoneNumber,
                            String password, String specialty) {
        super(userId, name, email, phoneNumber, password);
        this.specialty = specialty;
    }

    public String getSpecialty() {
        return specialty;
    }
}
