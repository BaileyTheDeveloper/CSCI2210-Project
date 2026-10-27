import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user who performs maintenance work.
 */
public class MaintenanceStaff extends User {
    private final String specialty;
    private final List<String> assignedOrders;

    public MaintenanceStaff(String userId, String name, String email, String phoneNumber,
                            String password, String specialty) {
        super(userId, name, email, phoneNumber, password);
        this.specialty = specialty;
        this.assignedOrders = new ArrayList<>();
    }

    public String getSpecialty() {
        return specialty;
    }

    public List<String> getAssignedOrders() {
        return new ArrayList<>(assignedOrders);
    }

    public void addAssignedOrder(String workOrderId) {
        if (workOrderId == null) {
            System.out.println("Error: Work order ID cannot be null.");
            return;
        }
        assignedOrders.add(workOrderId);
    }
}
