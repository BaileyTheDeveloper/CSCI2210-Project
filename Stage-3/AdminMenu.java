import java.util.Scanner;

/**
 * The admin's main menu. It opens a separate submenu for each area of admin work.
 */
public class AdminMenu implements Menu {
    private final Scanner scanner;
    private final Menu propertyMenu;
    private final Menu accountMenu;
    private final Menu applicationMenu;
    private final Menu workOrderMenu;
    private final ReportService reportService;

    public AdminMenu(Scanner scanner, Menu propertyMenu, Menu accountMenu, Menu applicationMenu,
                     Menu workOrderMenu, ReportService reportService) {
        this.scanner = scanner;
        this.propertyMenu = propertyMenu;
        this.accountMenu = accountMenu;
        this.applicationMenu = applicationMenu;
        this.workOrderMenu = workOrderMenu;
        this.reportService = reportService;
    }

    @Override
    public Scanner getScanner() {
        return scanner;
    }

    @Override
    public void displayOptions() {
        System.out.println("\nAdmin Menu:");
        System.out.println("1. Manage properties & units");
        System.out.println("2. Manage user accounts");
        System.out.println("3. Review applications");
        System.out.println("4. Manage work orders");
        System.out.println("5. View financial report");
        System.out.println("0. Log out");
        System.out.print("Select an option: ");
    }

    @Override
    public void processOption(int option) {
        switch (option) {
            case 1:
                propertyMenu.showMenu();
                break;
            case 2:
                accountMenu.showMenu();
                break;
            case 3:
                applicationMenu.showMenu();
                break;
            case 4:
                workOrderMenu.showMenu();
                break;
            case 5:
                System.out.println(reportService.generateOwnerStatement(null));
                break;
            default:
                System.out.println("Invalid option.");
        }
    }
}
