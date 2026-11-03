import java.util.List;
import java.util.Scanner;

/**
 * Lets an admin review work orders, approve them and assign maintenance staff.
 */
public class WorkOrderAdminMenu implements Menu {
    private final Scanner scanner;
    private final WorkOrderManager workOrderManager;
    private final UserManager userManager;
    private final AdminUser admin;

    public WorkOrderAdminMenu(Scanner scanner, WorkOrderManager workOrderManager,
                              UserManager userManager, AdminUser admin) {
        this.scanner = scanner;
        this.workOrderManager = workOrderManager;
        this.userManager = userManager;
        this.admin = admin;
    }

    @Override
    public Scanner getScanner() {
        return scanner;
    }

    @Override
    public void displayOptions() {
        System.out.println("\nWork Orders:");
        System.out.println("1. List work orders");
        System.out.println("2. Approve a work order");
        System.out.println("3. Assign maintenance staff");
        System.out.println("0. Back");
        System.out.print("Select an option: ");
    }

    @Override
    public void processOption(int option) {
        switch (option) {
            case 1:
                listWorkOrders();
                break;
            case 2:
                approve();
                break;
            case 3:
                assignStaff();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private void listWorkOrders() {
        List<WorkOrder> orders = workOrderManager.getWorkOrders();
        if (orders.isEmpty()) {
            System.out.println("There are no work orders.");
        }
        for (WorkOrder order : orders) {
            System.out.println(workOrderManager.describe(order));
        }
    }

    private ManagerWorkOrder findOrder() {
        WorkOrder order = workOrderManager.findWorkOrder(prompt("Work order ID"));
        if (!(order instanceof ManagerWorkOrder)) {
            System.out.println("Work order not found.");
            return null;
        }
        return (ManagerWorkOrder) order;
    }

    private void approve() {
        ManagerWorkOrder order = findOrder();
        if (order != null) {
            if (order.approveOrder(admin.getUserId())) {
                System.out.println("Work order approved.");
            } else {
                System.out.println("That work order is already completed.");
            }
        }
    }

    private void assignStaff() {
        ManagerWorkOrder order = findOrder();
        if (order == null) {
            return;
        }
        for (User account : userManager.getUsers()) {
            if (account instanceof MaintenanceStaff) {
                System.out.println("  " + account.getUserId() + " | " + account.getName()
                        + " | " + ((MaintenanceStaff) account).getSpecialty());
            }
        }
        String staffId = prompt("Maintenance staff user ID");
        User staff = userManager.findUserById(staffId);
        if (!(staff instanceof MaintenanceStaff)) {
            System.out.println("Maintenance staff not found.");
            return;
        }
        if (order.assignContractor(staffId)) {
            System.out.println("Work order assigned to " + staff.getName() + ".");
        } else {
            System.out.println("That work order is already completed.");
        }
    }
}
