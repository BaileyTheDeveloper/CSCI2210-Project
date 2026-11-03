import java.util.List;
import java.util.Scanner;

/**
 * Lets an owner see their properties, income and maintenance activity.
 */
public class OwnerMenu implements Menu {
    private final Scanner scanner;
    private final PropertyManager propertyManager;
    private final WorkOrderManager workOrderManager;
    private final ReportService reportService;
    private final Owner owner;

    public OwnerMenu(Scanner scanner, PropertyManager propertyManager,
                     WorkOrderManager workOrderManager, ReportService reportService, Owner owner) {
        this.scanner = scanner;
        this.propertyManager = propertyManager;
        this.workOrderManager = workOrderManager;
        this.reportService = reportService;
        this.owner = owner;
    }

    @Override
    public Scanner getScanner() {
        return scanner;
    }

    @Override
    public void displayOptions() {
        System.out.println("\nOwner Menu:");
        System.out.println("1. View my properties");
        System.out.println("2. View financial statement");
        System.out.println("3. View maintenance on my properties");
        System.out.println("0. Log out");
        System.out.print("Select an option: ");
    }

    @Override
    public void processOption(int option) {
        switch (option) {
            case 1:
                viewProperties();
                break;
            case 2:
                System.out.println(reportService.generateOwnerStatement(owner.getUserId()));
                break;
            case 3:
                viewMaintenance();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private void viewProperties() {
        List<Property> properties = propertyManager.getPropertiesForOwner(owner.getUserId());
        if (properties.isEmpty()) {
            System.out.println("You do not own any managed properties.");
        }
        for (Property property : properties) {
            System.out.printf("%s | %s | %s | %d units (%d vacant)%n", property.getPropertyId(),
                    property.getAddress(), property.getDwellingType(), property.getTotalUnits(),
                    property.getVacantUnits());
        }
    }

    private void viewMaintenance() {
        boolean found = false;
        for (WorkOrder order : workOrderManager.getWorkOrders()) {
            Property property = propertyManager.findPropertyByUnitId(order.getUnitId());
            if (property != null && owner.getUserId().equalsIgnoreCase(property.getOwnerId())) {
                System.out.println(workOrderManager.describe(order));
                found = true;
            }
        }
        if (!found) {
            System.out.println("No maintenance work orders for your properties.");
        }
    }
}
