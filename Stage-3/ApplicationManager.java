import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ApplicationManager {
    private Map<String, Application> applications;

    public ApplicationManager() {
        this.applications = new HashMap<>();
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

    public Tenant convertApplicantToTenant(String applicantId, String unitId, Lease leaseDetails) {
        return new Tenant();
    }

    public void forwardToAdmin(String applicationId, String adminId) {
        if (applications.containsKey(applicationId)) {
            Application app = applications.get(applicationId);
            app.setReviewedByAdminID(adminId);
            app.updateStatus("Forwarded to Admin");
        }
    }
}