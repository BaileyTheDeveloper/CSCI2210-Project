import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores work orders and finds them by unit or by assigned staff member.
 */
public class WorkOrderManager {
    private final Map<String, WorkOrder> workOrders = new LinkedHashMap<>();
    private final UserManager userManager;
    private int nextWorkOrderId = 1;

    public WorkOrderManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public String describe(WorkOrder order) {
        String contractorId = order instanceof ManagerWorkOrder
                ? ((ManagerWorkOrder) order).getAssignedContractorId() : null;
        User contractor = contractorId == null ? null : userManager.findUserById(contractorId);
        String contractorName = contractorId == null ? "nobody"
                : contractor == null ? "Unknown person" : contractor.getName();
        
        String typeLabel = order instanceof Inspection ? "[Inspection] " : "";
        return String.format("  %s%s | Unit %s | %s | %s | Assigned to: %s", typeLabel, order.getOrderId(),
                order.getUnitId(), order.getStatus(), order.getDescription(), contractorName);
    }

    public WorkOrder createWorkOrder(String unitId, String description) {
        WorkOrder order = new ManagerWorkOrder("WO" + nextWorkOrderId++, unitId, description, 0.0);
        workOrders.put(order.getOrderId(), order);
        return order;
    }

    public WorkOrder createInspection(String unitId, String description, double budget) {
        Inspection order = new Inspection("INSP" + nextWorkOrderId++, unitId, description, budget);
        workOrders.put(order.getOrderId(), order);
        return order;
    }

    public WorkOrder findWorkOrder(String orderId) {
        if (orderId == null) {
            return null;
        }
        for (WorkOrder order : workOrders.values()) {
            if (order.getOrderId().equalsIgnoreCase(orderId)) {
                return order;
            }
        }
        return null;
    }

    public List<WorkOrder> getWorkOrders() {
        return new ArrayList<>(workOrders.values());
    }

    public List<WorkOrder> getWorkOrdersForUnit(String unitId) {
        List<WorkOrder> results = new ArrayList<>();
        for (WorkOrder order : workOrders.values()) {
            if (unitId != null && order.getUnitId().equalsIgnoreCase(unitId)) {
                results.add(order);
            }
        }
        return results;
    }

    public List<WorkOrder> getWorkOrdersAssignedTo(String staffId) {
        List<WorkOrder> results = new ArrayList<>();
        for (WorkOrder order : workOrders.values()) {
            if (order instanceof ManagerWorkOrder
                    && staffId != null
                    && staffId.equalsIgnoreCase(((ManagerWorkOrder) order).getAssignedContractorId())) {
                results.add(order);
            }
        }
        return results;
    }
}
