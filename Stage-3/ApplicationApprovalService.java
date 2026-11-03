/**
 * Carries out an admin's decision on an application, including the tenant and lease changes
 * that follow an approval.
 */
public class ApplicationApprovalService {
    private final ApplicationManager applicationManager;
    private final UserManager userManager;
    private final PropertyManager propertyManager;
    private final LeaseManager leaseManager;

    public ApplicationApprovalService(ApplicationManager applicationManager, UserManager userManager,
                                      PropertyManager propertyManager, LeaseManager leaseManager) {
        this.applicationManager = applicationManager;
        this.userManager = userManager;
        this.propertyManager = propertyManager;
        this.leaseManager = leaseManager;
    }

    /**
     * Approves an application, turns the applicant into a tenant and creates their lease.
     *
     * @return the new lease, or null if the application, applicant or vacant unit is not valid
     */
    public Lease approve(String applicationId, String adminId) {
        Application application = applicationManager.getApplication(applicationId);
        if (!applicationManager.isAwaitingReview(application)) {
            return null;
        }
        User applicant = userManager.findUserById(application.getApplicantId());
        Unit unit = propertyManager.findUnit(application.getUnitId());
        if (!(applicant instanceof Applicant) || unit == null || !unit.isVacant()) {
            return null;
        }

        Tenant tenant = applicationManager.convertApplicantToTenant((Applicant) applicant, unit);
        if (tenant == null) {
            return null;
        }
        applicationManager.reviewApplication(applicationId, true);
        application.setReviewedByAdminID(adminId);

        Lease lease = leaseManager.createLease(applicationId, tenant, unit);
        unit.markOccupied();
        tenant.setLeaseStatus("Active");
        return lease;
    }

    /**
     * Denies an application that is still waiting for review and marks the applicant as denied.
     *
     * @return false if the application does not exist or has already been reviewed
     */
    public boolean deny(String applicationId, String adminId) {
        Application application = applicationManager.getApplication(applicationId);
        if (!applicationManager.isAwaitingReview(application)) {
            return false;
        }
        applicationManager.reviewApplication(applicationId, false);
        application.setReviewedByAdminID(adminId);
        User applicant = userManager.findUserById(application.getApplicantId());
        if (applicant instanceof Applicant) {
            ((Applicant) applicant).setStatus("Denied");
        }
        return true;
    }
}
