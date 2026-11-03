import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Stores leases and the rent payments made against them.
 */
public class LeaseManager {
    private final List<Lease> leases = new ArrayList<>();
    private final List<Payment> payments = new ArrayList<>();
    private int nextInvoiceId = 1;

    /**
     * Creates and stores an active one-year lease for a new tenant. The deposit equals one month's rent.
     */
    public Lease createLease(String applicationId, Tenant tenant, Unit unit) {
        Calendar calendar = Calendar.getInstance();
        Date start = calendar.getTime();
        calendar.add(Calendar.YEAR, 1);
        Lease lease = new Lease("LEASE-" + applicationId, start, calendar.getTime(),
                unit.getRentAmount(), unit.getRentAmount(), tenant.getTenantId(), unit.getUnitId());
        lease.createLease();
        leases.add(lease);
        return lease;
    }

    public Lease getLease(String leaseId) {
        for (Lease lease : leases) {
            if (leaseId != null && lease.getLeaseId().equalsIgnoreCase(leaseId)) {
                return lease;
            }
        }
        return null;
    }

    public Lease findLeaseByTenantId(String tenantId) {
        for (Lease lease : leases) {
            if (tenantId != null && tenantId.equalsIgnoreCase(lease.getTenantId())) {
                return lease;
            }
        }
        return null;
    }

    public Payment recordRentPayment(Lease lease, String paymentMethod) {
        int number = nextInvoiceId++;
        Invoice invoice = new Invoice("INV" + number, lease.getLeaseId(), lease.getMonthlyRent(), new java.util.Date());
        Payment payment = new Payment(invoice, paymentMethod, "TXN" + number);
        payments.add(payment);
        return payment;
    }

    public List<Payment> getPayments() {
        return new ArrayList<>(payments);
    }

    public List<Payment> getPaymentsForLease(String leaseId) {
        List<Payment> results = new ArrayList<>();
        for (Payment payment : payments) {
            if (leaseId != null && payment.getLeaseId().equalsIgnoreCase(leaseId)) {
                results.add(payment);
            }
        }
        return results;
    }
}
