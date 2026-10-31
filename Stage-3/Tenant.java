/**
 * Represents a user who currently rents a unit.
 */
public class Tenant extends User {
    private final String tenantId;
    private String leaseStatus;
    private Unit currentUnit;

    public Tenant(String userId, String name, String email, String phoneNumber,
                  String password, String tenantId, Unit currentUnit) {
        super(userId, name, email, phoneNumber, password);
        this.tenantId = tenantId;
        this.currentUnit = currentUnit;
        this.leaseStatus = "None";
    }

    public Tenant(Applicant applicant, Unit currentUnit) {
        super(applicant);
        if (applicant == null) {
            System.out.println("Error: Applicant cannot be null.");
            this.tenantId = "";
            this.currentUnit = null;
            this.leaseStatus = "None";
        } else {
            this.tenantId = applicant.getApplicantID();
            this.currentUnit = currentUnit;
            this.leaseStatus = "None";
        }
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getLeaseStatus() {
        return leaseStatus;
    }

    public void setLeaseStatus(String leaseStatus) {
        this.leaseStatus = leaseStatus;
    }

    public Unit getCurrentUnit() {
        return currentUnit;
    }

    public void setCurrentUnit(Unit currentUnit) {
        this.currentUnit = currentUnit;
    }

    public void payRent(double amount) {
        throw new UnsupportedOperationException("Rent payment will be added with the payment classes.");
    }
}
