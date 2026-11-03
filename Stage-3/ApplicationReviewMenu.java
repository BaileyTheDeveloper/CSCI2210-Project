import java.util.List;
import java.util.Scanner;

/**
 * Lets an admin list waiting applications and approve or deny them.
 */
public class ApplicationReviewMenu implements Menu {
    private final Scanner scanner;
    private final ApplicationManager applicationManager;
    private final ApplicationApprovalService approvalService;
    private final AdminUser admin;

    public ApplicationReviewMenu(Scanner scanner, ApplicationManager applicationManager,
                                 ApplicationApprovalService approvalService, AdminUser admin) {
        this.scanner = scanner;
        this.applicationManager = applicationManager;
        this.approvalService = approvalService;
        this.admin = admin;
    }

    @Override
    public Scanner getScanner() {
        return scanner;
    }

    @Override
    public void displayOptions() {
        System.out.println("\nApplication Review:");
        System.out.println("1. List applications waiting for review");
        System.out.println("2. Approve an application");
        System.out.println("3. Deny an application");
        System.out.println("0. Back");
        System.out.print("Select an option: ");
    }

    @Override
    public void processOption(int option) {
        switch (option) {
            case 1:
                listWaiting();
                break;
            case 2:
                approve();
                break;
            case 3:
                deny();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private void listWaiting() {
        List<Application> waiting = applicationManager.getApplicationsAwaitingReview();
        if (waiting.isEmpty()) {
            System.out.println("No applications are waiting for review.");
        }
        for (Application application : waiting) {
            System.out.println("  " + application.getDetails() + " | Unit: " + application.getUnitId());
        }
    }

    private void approve() {
        Lease lease = approvalService.approve(prompt("Application ID"), admin.getUserId());
        if (lease == null) {
            System.out.println("Cannot approve: the application, applicant or vacant unit was not found, "
                    + "or the application was already reviewed.");
        } else {
            System.out.println("Approved. Lease " + lease.getLeaseId() + " created.");
        }
    }

    private void deny() {
        if (approvalService.deny(prompt("Application ID"), admin.getUserId())) {
            System.out.println("Application denied.");
        } else {
            System.out.println("Application not found or already reviewed.");
        }
    }
}
