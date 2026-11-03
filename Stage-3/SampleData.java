/**
 * Fills the application with starting accounts, properties, units, a lease and a work order
 * so every menu has something to show.
 */
public class SampleData {
    private final UserManager userManager;
    private final RegistrationService registrationService;
    private final PropertyManager propertyManager;
    private final ApplicationManager applicationManager;
    private final ApplicationApprovalService approvalService;
    private final LeaseManager leaseManager;
    private final WorkOrderManager workOrderManager;

    public SampleData(UserManager userManager, RegistrationService registrationService,
                      PropertyManager propertyManager, ApplicationManager applicationManager,
                      ApplicationApprovalService approvalService, LeaseManager leaseManager,
                      WorkOrderManager workOrderManager) {
        this.userManager = userManager;
        this.registrationService = registrationService;
        this.propertyManager = propertyManager;
        this.applicationManager = applicationManager;
        this.approvalService = approvalService;
        this.leaseManager = leaseManager;
        this.workOrderManager = workOrderManager;
    }

    public void load() {
        userManager.addUser(new AdminUser("ADMIN1", "John Doe", "johndoe@realestate.com",
                "555-0100", "admin123", "EMP1"));
        User owner = registrationService.registerOwner("Jane Doe", "janedoe@realestate.com",
                "555-0101", "owner123");
        User staff = registrationService.registerMaintenanceStaff("John Smith",
                "johnsmith@realestate.com", "555-0102", "maint123", "Plumbing");

        String oakStreet = propertyManager.addProperty("12 Oak Street", "Apartment");
        String elmAvenue = propertyManager.addProperty("48 Elm Avenue", "House");
        propertyManager.assignPropertyToOwner(oakStreet, owner.getUserId());
        propertyManager.assignPropertyToOwner(elmAvenue, owner.getUserId());

        Unit unit101 = propertyManager.addUnit(oakStreet, "101", 1200.00, 2, 1);
        Unit unit102 = propertyManager.addUnit(oakStreet, "102", 1350.00, 2, 2);
        propertyManager.addUnit(elmAvenue, "A", 1800.00, 3, 2);

        // The tenant starts as an applicant and is approved through the normal approval steps.
        registrationService.registerApplicant("Jane Smith", "janesmith@realestate.com",
                "555-0103", "tenant123");
        User tenant = userManager.findUserByEmail("janesmith@realestate.com");
        Application tenantApplication = applicationManager.submitApplication(
                tenant.getUserId(), unit101.getUnitId());
        tenantApplication.submitApplication();
        Lease lease = approvalService.approve(tenantApplication.getApplicationID(), "ADMIN1");
        leaseManager.recordRentPayment(lease, "BANK_TRANSFER");

        WorkOrder order = workOrderManager.createWorkOrder(unit101.getUnitId(), "Kitchen sink is leaking");
        ((ManagerWorkOrder) order).assignContractor(staff.getUserId());

        // This applicant has a pending application, so the admin has one to review.
        registrationService.registerApplicant("Andy Applicant", "applicant@realestate.com",
                "555-0104", "applicant123");
        User applicant = userManager.findUserByEmail("applicant@realestate.com");
        applicationManager.submitApplication(applicant.getUserId(), unit102.getUnitId())
                .submitApplication();
    }
}
