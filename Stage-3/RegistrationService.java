/**
 * Creates new user accounts with generated IDs and adds them to the user manager.
 */
public class RegistrationService {
    private final UserManager userManager;
    private int nextUserId = 1;
    private int nextApplicantId = 1;

    public RegistrationService(UserManager userManager) {
        this.userManager = userManager;
    }

    public boolean registerApplicant(String name, String email, String phoneNumber, String password) {
        if (hasBlank(name, email, phoneNumber, password) || userManager.findUserByEmail(email) != null) {
            return false;
        }
        String applicantId = "APP" + nextApplicantId;
        Applicant applicant = new Applicant(nextFreeUserId(), name, email, phoneNumber, password, applicantId);
        if (!userManager.addUser(applicant)) {
            return false;
        }
        nextApplicantId++;
        return true;
    }

    public User registerOwner(String name, String email, String phoneNumber, String password) {
        if (hasBlank(name, email, phoneNumber, password) || userManager.findUserByEmail(email) != null) {
            return null;
        }
        User owner = new Owner(nextFreeUserId(), name, email, phoneNumber, password);
        return userManager.addUser(owner) ? owner : null;
    }

    public User registerMaintenanceStaff(String name, String email, String phoneNumber,
                                         String password, String specialty) {
        if (hasBlank(name, email, phoneNumber, password) || userManager.findUserByEmail(email) != null) {
            return null;
        }
        User staff = new MaintenanceStaff(nextFreeUserId(), name, email, phoneNumber, password, specialty);
        return userManager.addUser(staff) ? staff : null;
    }

    private boolean hasBlank(String... fields) {
        for (String field : fields) {
            if (field == null || field.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private String nextFreeUserId() {
        String userId = "USER" + nextUserId;
        while (userManager.findUserById(userId) != null) {
            nextUserId++;
            userId = "USER" + nextUserId;
        }
        nextUserId++;
        return userId;
    }
}
