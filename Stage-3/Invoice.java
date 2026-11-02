import java.util.Date;

public class Invoice {
    protected String invoiceId;
    protected String leaseId;
    protected double amountDue;
    protected Date dueDate;
    protected boolean isPaid;

    public Invoice(String invoiceId, String leaseId, double amountDue, Date dueDate) {
        this.invoiceId = invoiceId;
        this.leaseId = leaseId;
        this.amountDue = amountDue;
        this.dueDate = dueDate;
        this.isPaid = false;
    }

    public void markAsPaid() {
        this.isPaid = true;
    }

    public String getInvoiceId() { return invoiceId; }
    public String getLeaseId() { return leaseId; }
    public double getAmountDue() { return amountDue; }
    public Date getDueDate() { return dueDate; }
    public boolean isPaid() { return isPaid; }
}
