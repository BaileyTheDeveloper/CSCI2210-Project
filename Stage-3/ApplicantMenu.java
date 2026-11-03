import java.util.List;
import java.util.Scanner;

/**
 * Lets an applicant browse vacant units and apply for one.
 */
public class ApplicantMenu implements Menu {
    private final Scanner scanner;
    private final PropertyManager propertyManager;
    private final ApplicationManager applicationManager;
    private final Applicant applicant;

    public ApplicantMenu(Scanner scanner, PropertyManager propertyManager,
                         ApplicationManager applicationManager, Applicant applicant) {
        this.scanner = scanner;
        this.propertyManager = propertyManager;
        this.applicationManager = applicationManager;
        this.applicant = applicant;
    }

    @Override
    public Scanner getScanner() {
        return scanner;
    }

    @Override
    public void displayOptions() {
        System.out.println("\nApplicant Menu:");
        System.out.println("1. Browse vacant units");
        System.out.println("2. Apply for a unit");
        System.out.println("3. View my applications");
        System.out.println("0. Log out");
        System.out.print("Select an option: ");
    }

    @Override
    public void processOption(int option) {
        switch (option) {
            case 1:
                browseUnits();
                break;
            case 2:
                apply();
                break;
            case 3:
                viewApplications();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private void browseUnits() {
        List<Unit> units = propertyManager.getVacantUnits();
        if (units.isEmpty()) {
            System.out.println("No vacant units are available.");
        }
        for (Unit unit : units) {
            System.out.println(unit);
        }
    }

    private void apply() {
        String unitId = prompt("Unit ID to apply for");
        Unit unit = propertyManager.findUnit(unitId);
        if (unit == null || !unit.isVacant()) {
            System.out.println("That unit does not exist or is not vacant.");
            return;
        }
        String applicationId = "APP-" + applicant.getUserId() + "-" + unit.getUnitId();
        if (applicationManager.getApplication(applicationId) != null) {
            System.out.println("You have already applied for that unit.");
            return;
        }
        Application application = applicationManager.submitApplication(applicant.getUserId(), unit.getUnitId());
        application.submitApplication();
        applicant.setStatus("Applied");
        System.out.println("Application submitted: " + application.getApplicationID());
    }

    private void viewApplications() {
        List<Application> applications = applicationManager.getApplicationsForApplicant(applicant.getUserId());
        if (applications.isEmpty()) {
            System.out.println("You have not submitted any applications.");
        }
        for (Application application : applications) {
            System.out.println("  " + application.getDetails());
        }
    }
}
