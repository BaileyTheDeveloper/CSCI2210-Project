import java.util.Date;

public class WorkOrder {
    protected String orderId;
    protected String unitId;
    protected String description;
    protected String status; // e.g., PENDING, IN_PROGRESS, COMPLETED
    protected Date dateCreated;

    public WorkOrder(String orderId, String unitId, String description) {
        this.orderId = orderId;
        this.unitId = unitId;
        this.description = description;
        this.status = "PENDING";
        this.dateCreated = new Date();
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    // Getters and Setters
    public String getOrderId() { return orderId; }
    public String getUnitId() { return unitId; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public Date getDateCreated() { return dateCreated; }
}
