import java.util.ArrayList;
import java.util.List;

/**
 * Summarizes the invoices and payments for one owner.
 */
public class FinancialReport {
    private final List<Invoice> invoices;

    public FinancialReport() {
        this.invoices = new ArrayList<>();
    }

    public void addInvoice(Invoice invoice) {
        if (invoice != null) {
            invoices.add(invoice);
        }
    }

    public double getTotalDue() {
        double total = 0;
        for (Invoice invoice : invoices) {
            if (!invoice.isPaid()) {
                total += invoice.getAmountDue();
            }
        }
        return total;
    }

    public double getTotalPaid() {
        double total = 0;
        for (Invoice invoice : invoices) {
            if (invoice.isPaid()) {
                total += invoice.getAmountDue();
            }
        }
        return total;
    }

    @Override
    public String toString() {
        return String.format("Total paid: $%.2f | Total due: $%.2f", getTotalPaid(), getTotalDue());
    }
}
