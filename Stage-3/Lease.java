import java.util.Date;

public class Lease {
    private String leaseID;
    private String tenantID;
    private String unitID;
    private Date startDate;
    private Date endDate;
    private double monthlyRent;
    private double securityDeposit;
    private String status;

    public Lease(String leaseId, Date startDate, Date endDate, double monthlyRent, double securityDeposit, String tenantId, String unitId) {
        this.leaseID = leaseId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.monthlyRent = monthlyRent;
        this.securityDeposit = securityDeposit;
        this.tenantID = tenantId;
        this.unitID = unitId;
        this.status = "Draft";
    }

    public void createLease() {
        this.status = "Active";
    }

    public void terminateLease() {
        this.status = "Terminated";
    }

    public String getLeaseId() {
        return leaseID;
    }

    public String getTenantId() {
        return tenantID;
    }

    public String getUnitId() {
        return unitID;
    }

    public double getMonthlyRent() {
        return monthlyRent;
    }
}