public class ManagerWorkOrder extends WorkOrder {
    private double approvedBudget;
    private String assignedContractorId;
    private boolean isManagerApproved;

    public ManagerWorkOrder(String orderId, String unitId, String description, double approvedBudget) {
        super(orderId, unitId, description);
        this.approvedBudget = approvedBudget;
        this.isManagerApproved = false;
    }

    public void approveOrder(String managerId) {
        this.isManagerApproved = true;
        this.updateStatus("APPROVED");
    }

    public void assignContractor(String contractorId) {
        this.assignedContractorId = contractorId;
        this.updateStatus("ASSIGNED");
    }

    public double getApprovedBudget() { return approvedBudget; }
    public String getAssignedContractorId() { return assignedContractorId; }
    public boolean isApproved() { return isManagerApproved; }
}
