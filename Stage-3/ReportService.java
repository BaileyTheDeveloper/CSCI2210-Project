/**
 * Builds financial reports from the payments received on managed properties.
 */
public class ReportService {
    private final PropertyManager propertyManager;
    private final LeaseManager leaseManager;

    public ReportService(PropertyManager propertyManager, LeaseManager leaseManager) {
        this.propertyManager = propertyManager;
        this.leaseManager = leaseManager;
    }

    /**
     * Collects the payments made for properties owned by one owner.
     *
     * @param ownerId the owner's user ID, or null to include every property
     */
    public FinancialReport generateOwnerStatement(String ownerId) {
        FinancialReport report = new FinancialReport();
        for (Payment payment : leaseManager.getPayments()) {
            Lease lease = leaseManager.getLease(payment.getLeaseId());
            Property property = lease == null ? null : propertyManager.findPropertyByUnitId(lease.getUnitId());
            if (property != null && (ownerId == null || ownerId.equalsIgnoreCase(property.getOwnerId()))) {
                report.addInvoice(payment);
            }
        }
        return report;
    }
}
