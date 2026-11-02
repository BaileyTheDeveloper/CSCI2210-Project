import java.util.Date;

public class Payment extends Invoice {
    private String paymentMethod; // e.g., CREDIT_CARD, BANK_TRANSFER, CHECK
    private Date paymentDate;
    private String transactionReference;

    public Payment(Invoice invoice, String paymentMethod, String transactionReference) {
        super(invoice.getInvoiceId(), invoice.getLeaseId(), invoice.getAmountDue(), invoice.getDueDate());
        this.paymentMethod = paymentMethod;
        this.transactionReference = transactionReference;
        this.paymentDate = new Date();
        this.markAsPaid(); // A payment implies completion of the invoice terms
    }

    public String getPaymentMethod() { return paymentMethod; }
    public Date getPaymentDate() { return paymentDate; }
    public String getTransactionReference() { return transactionReference; }
}
