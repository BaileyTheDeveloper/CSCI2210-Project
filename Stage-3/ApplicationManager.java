import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ApplicationManager {
    private final Map<String, Application> applications;
    private final UserManager userManager;

    public ApplicationManager(UserManager userManager) {
        this.applications = new HashMap<>();
        this.userManager = userManager;
    }

    public Application submitApplication(String applicantId, String unitId) {
        String appId = "APP-" + applicantId + "-" + unitId;
        Application app = new Application(appId, new Date(), "Pending");
        applications.put(appId, app);
        return app;
    }

    public void reviewApplication(String applicationId, boolean approved) {
        if (applications.containsKey(applicationId)) {
            Application app = applications.get(applicationId);
            app.updateStatus(approved ? "Approved" : "Denied");
            app.setReviewDate(new Date());
        }
    }

    public Tenant convertApplicantToTenant(Applicant applicant, Unit unit) {
        if (applicant == null || unit == null) {
            System.out.println("Error: Applicant and unit are required to create a tenant.");
            return null;
        }
        if (userManager == null) {
            System.out.println("Error: User manager is not available.");
            return null;
        }
        User registeredUser = userManager.findUserById(applicant.getUserId());
        if (!(registeredUser instanceof Applicant)) {
            System.out.println("Error: Applicant account was not found.");
            return null;
        }

        Applicant registeredApplicant = (Applicant) registeredUser;
        Tenant convertedTenant = new Tenant(registeredApplicant, unit);
        if (!userManager.editUser(registeredApplicant.getUserId(), convertedTenant)) {
            return null;
        }
        return convertedTenant;
    }

    public void forwardToAdmin(String applicationId, String adminId) {
        if (applications.containsKey(applicationId)) {
            Application app = applications.get(applicationId);
            app.setReviewedByAdminID(adminId);
            app.updateStatus("Forwarded to Admin");
        }
    }
}