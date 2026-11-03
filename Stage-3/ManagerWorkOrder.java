public class ManagerWorkOrder extends WorkOrder {
    private double approvedBudget;
    private String assignedContractorId;
    private boolean isManagerApproved;

    public ManagerWorkOrder(String orderId, String unitId, String description, double approvedBudget) {
        super(orderId, unitId, description);
        this.approvedBudget = approvedBudget;
        this.isManagerApproved = false;
    }

    public boolean approveOrder(String managerId) {
        if (isCompleted()) {
            return false;
        }
        this.isManagerApproved = true;
        this.updateStatus("APPROVED");
        return true;
    }

    public boolean assignContractor(String contractorId) {
        if (isCompleted()) {
            return false;
        }
        this.assignedContractorId = contractorId;
        this.updateStatus("ASSIGNED");
        return true;
    }

    private boolean isCompleted() {
        return "COMPLETED".equals(getStatus());
    }

    public double getApprovedBudget() { return approvedBudget; }
    public String getAssignedContractorId() { return assignedContractorId; }
    public boolean isApproved() { return isManagerApproved; }
}
