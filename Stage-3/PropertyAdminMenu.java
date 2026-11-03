import java.util.List;
import java.util.Scanner;

/**
 * Lets an admin add properties and units and assign properties to owners.
 */
public class PropertyAdminMenu implements Menu {
    private final Scanner scanner;
    private final PropertyManager propertyManager;
    private final UserManager userManager;

    public PropertyAdminMenu(Scanner scanner, PropertyManager propertyManager, UserManager userManager) {
        this.scanner = scanner;
        this.propertyManager = propertyManager;
        this.userManager = userManager;
    }

    @Override
    public Scanner getScanner() {
        return scanner;
    }

    @Override
    public void displayOptions() {
        System.out.println("\nProperty Management:");
        System.out.println("1. Add property");
        System.out.println("2. Add unit to a property");
        System.out.println("3. List properties and units");
        System.out.println("4. Assign property to an owner");
        System.out.println("0. Back");
        System.out.print("Select an option: ");
    }

    @Override
    public void processOption(int option) {
        switch (option) {
            case 1:
                addProperty();
                break;
            case 2:
                addUnit();
                break;
            case 3:
                listProperties();
                break;
            case 4:
                assignOwner();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private void addProperty() {
        String address = prompt("Address");
        String type = prompt("Dwelling type (e.g. Apartment, House)");
        if (address.isEmpty() || type.isEmpty()) {
            System.out.println("Address and dwelling type are required.");
            return;
        }
        System.out.println("Property created: " + propertyManager.addProperty(address, type));
    }

    private void addUnit() {
        String propertyId = prompt("Property ID");
        if (propertyManager.findProperty(propertyId) == null) {
            System.out.println("Property not found.");
            return;
        }
        String unitNumber = prompt("Unit number");
        double rent = promptDouble("Monthly rent");
        int bedrooms = promptInt("Bedrooms");
        int bathrooms = promptInt("Bathrooms");
        if (unitNumber.isEmpty() || rent < 0 || bedrooms < 0 || bathrooms < 0) {
            System.out.println("Invalid unit details.");
            return;
        }
        Unit unit = propertyManager.addUnit(propertyId, unitNumber, rent, bedrooms, bathrooms);
        System.out.println("Unit created: " + unit.getUnitId());
    }

    private void listProperties() {
        List<Property> properties = propertyManager.getProperties();
        if (properties.isEmpty()) {
            System.out.println("No properties have been added.");
        }
        for (Property property : properties) {
            String ownerId = property.getOwnerId();
            User owner = ownerId == null ? null : userManager.findUserById(ownerId);
            String ownerName = ownerId == null ? "none"
                    : owner instanceof Owner ? owner.getName() : "Unknown owner";
            System.out.printf("%s | %s | %s | Owner: %s%n", property.getPropertyId(), property.getAddress(),
                    property.getDwellingType(), ownerName);
            for (Unit unit : property.getUnits()) {
                System.out.println(unit);
            }
        }
    }

    private void assignOwner() {
        String propertyId = prompt("Property ID");
        String ownerId = prompt("Owner user ID");
        if (propertyManager.findProperty(propertyId) == null
                || !(userManager.findUserById(ownerId) instanceof Owner)) {
            System.out.println("Property or owner not found.");
            return;
        }
        propertyManager.assignPropertyToOwner(propertyId, ownerId);
        System.out.println("Property " + propertyId + " assigned to " + ownerId + ".");
    }
}
