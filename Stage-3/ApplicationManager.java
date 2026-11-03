import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
        Application existing = getApplication(appId);
        if (existing != null) {
            return existing;
        }
        Application app = new Application(appId, new Date(), "Pending");
        app.setApplicantAndUnit(applicantId, unitId);
        applications.put(appId, app);
        return app;
    }

    public Application getApplication(String applicationId) {
        if (applicationId == null) {
            return null;
        }
        for (Application application : applications.values()) {
            if (application.getApplicationID().equalsIgnoreCase(applicationId)) {
                return application;
            }
        }
        return null;
    }

    public List<Application> getApplications() {
        return new ArrayList<>(applications.values());
    }

    public List<Application> getApplicationsAwaitingReview() {
        List<Application> results = new ArrayList<>();
        for (Application app : applications.values()) {
            if (isAwaitingReview(app)) {
                results.add(app);
            }
        }
        return results;
    }

    public boolean isAwaitingReview(Application app) {
        return app != null && ("Submitted".equals(app.getStatus()) || "Pending".equals(app.getStatus()));
    }

    public List<Application> getApplicationsForApplicant(String applicantId) {
        List<Application> results = new ArrayList<>();
        for (Application app : applications.values()) {
            if (applicantId != null && applicantId.equalsIgnoreCase(app.getApplicantId())) {
                results.add(app);
            }
        }
        return results;
    }

    public void reviewApplication(String applicationId, boolean approved) {
        Application app = getApplication(applicationId);
        if (app != null) {
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
        Application app = getApplication(applicationId);
        if (app != null) {
            app.setReviewedByAdminID(adminId);
            app.updateStatus("Forwarded to Admin");
        }
    }
}