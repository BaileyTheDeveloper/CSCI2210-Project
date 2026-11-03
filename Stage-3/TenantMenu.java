import java.util.List;
import java.util.Scanner;

/**
 * Lets a tenant view their lease, pay rent and request maintenance.
 */
public class TenantMenu implements Menu {
    private final Scanner scanner;
    private final LeaseManager leaseManager;
    private final WorkOrderManager workOrderManager;
    private final Tenant tenant;

    public TenantMenu(Scanner scanner, LeaseManager leaseManager,
                      WorkOrderManager workOrderManager, Tenant tenant) {
        this.scanner = scanner;
        this.leaseManager = leaseManager;
        this.workOrderManager = workOrderManager;
        this.tenant = tenant;
    }

    @Override
    public Scanner getScanner() {
        return scanner;
    }

    @Override
    public void displayOptions() {
        System.out.println("\nTenant Menu:");
        System.out.println("1. View my lease");
        System.out.println("2. Pay rent");
        System.out.println("3. Request maintenance");
        System.out.println("4. View maintenance requests for my unit");
        System.out.println("0. Log out");
        System.out.print("Select an option: ");
    }

    @Override
    public void processOption(int option) {
        switch (option) {
            case 1:
                viewLease();
                break;
            case 2:
                payRent();
                break;
            case 3:
                requestMaintenance();
                break;
            case 4:
                viewRequests();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private Lease findLease() {
        Lease lease = leaseManager.findLeaseByTenantId(tenant.getTenantId());
        if (lease == null) {
            System.out.println("You do not have a lease.");
        }
        return lease;
    }

    private void viewLease() {
        Lease lease = findLease();
        if (lease == null) {
            return;
        }
        System.out.printf("Lease %s | Unit %s | Rent $%.2f | Status: %s%n", lease.getLeaseId(),
                lease.getUnitId(), lease.getMonthlyRent(), tenant.getLeaseStatus());
        for (Payment payment : leaseManager.getPaymentsForLease(lease.getLeaseId())) {
            System.out.printf("  Paid $%.2f on %s (%s)%n", payment.getAmountDue(),
                    payment.getPaymentDate(), payment.getTransactionReference());
        }
    }

    private void payRent() {
        Lease lease = findLease();
        if (lease == null) {
            return;
        }
        String method = prompt("Payment method (CREDIT_CARD, BANK_TRANSFER, CHECK)");
        if (method.isEmpty()) {
            System.out.println("Payment cancelled.");
            return;
        }
        Payment payment = leaseManager.recordRentPayment(lease, method);
        System.out.printf("Paid $%.2f. Reference: %s%n", payment.getAmountDue(), payment.getTransactionReference());
    }

    private void requestMaintenance() {
        if (tenant.getCurrentUnit() == null) {
            System.out.println("You are not assigned to a unit.");
            return;
        }
        String description = prompt("Describe the problem");
        if (description.isEmpty()) {
            System.out.println("A description is required.");
            return;
        }
        WorkOrder order = workOrderManager.createWorkOrder(tenant.getCurrentUnit().getUnitId(), description);
        System.out.println("Maintenance request created: " + order.getOrderId());
    }

    private void viewRequests() {
        if (tenant.getCurrentUnit() == null) {
            System.out.println("You are not assigned to a unit.");
            return;
        }
        List<WorkOrder> orders = workOrderManager.getWorkOrdersForUnit(tenant.getCurrentUnit().getUnitId());
        if (orders.isEmpty()) {
            System.out.println("No maintenance requests for your unit.");
        }
        for (WorkOrder order : orders) {
            System.out.println(workOrderManager.describe(order));
        }
    }
}
