import java.util.List;
import java.util.Scanner;

/**
 * Lets maintenance staff see and update the work orders assigned to them.
 */
public class MaintenanceMenu implements Menu {
    private final Scanner scanner;
    private final WorkOrderManager workOrderManager;
    private final MaintenanceStaff staff;

    public MaintenanceMenu(Scanner scanner, WorkOrderManager workOrderManager, MaintenanceStaff staff) {
        this.scanner = scanner;
        this.workOrderManager = workOrderManager;
        this.staff = staff;
    }

    @Override
    public Scanner getScanner() {
        return scanner;
    }

    @Override
    public void displayOptions() {
        System.out.println("\nMaintenance Menu:");
        System.out.println("1. View my assigned work orders");
        System.out.println("2. Update a work order's status");
        System.out.println("0. Log out");
        System.out.print("Select an option: ");
    }

    @Override
    public void processOption(int option) {
        switch (option) {
            case 1:
                viewAssigned();
                break;
            case 2:
                updateStatus();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private void viewAssigned() {
        List<WorkOrder> orders = workOrderManager.getWorkOrdersAssignedTo(staff.getUserId());
        if (orders.isEmpty()) {
            System.out.println("No work orders are assigned to you.");
        }
        for (WorkOrder order : orders) {
            System.out.println(workOrderManager.describe(order));
        }
    }

    private void updateStatus() {
        WorkOrder order = workOrderManager.findWorkOrder(prompt("Work order ID"));
        if (order == null || !workOrderManager.getWorkOrdersAssignedTo(staff.getUserId()).contains(order)) {
            System.out.println("That work order is not assigned to you.");
            return;
        }
        System.out.println("1. In progress");
        System.out.println("2. Completed");
        int choice = promptInt("New status");
        if (choice == 1) {
            order.updateStatus("IN_PROGRESS");
        } else if (choice == 2) {
            order.updateStatus("COMPLETED");
        } else {
            System.out.println("Invalid choice.");
            return;
        }
        System.out.println("Work order " + order.getOrderId() + " is now " + order.getStatus() + ".");
    }
}
