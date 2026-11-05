import java.util.List;
import java.util.Scanner;

/**
 * Lets an admin add, update, list, and remove properties and units, as well as assign properties to owners.
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
        System.out.println("2. Update property address/type");
        System.out.println("3. Delete property");
        System.out.println("4. Add unit to a property");
        System.out.println("5. Update unit rent");
        System.out.println("6. Delete unit from a property");
        System.out.println("7. List properties and units");
        System.out.println("8. Assign property to an owner");
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
                updateProperty();
                break;
            case 3:
                deleteProperty();
                break;
            case 4:
                addUnit();
                break;
            case 5:
                updateUnitRent();
                break;
            case 6:
                deleteUnit();
                break;
            case 7:
                listProperties();
                break;
            case 8:
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

    private void updateProperty() {
        String propertyId = prompt("Property ID to update");
        Property property = propertyManager.findProperty(propertyId);
        if (property == null) {
            System.out.println("Property not found.");
            return;
        }
        String newAddress = prompt("New Address (leave blank to keep current: " + property.getAddress() + ")");
        if (!newAddress.isEmpty()) {
            property.setAddress(newAddress);
        }
        System.out.println("Property updated successfully.");
    }

    private void deleteProperty() {
        String propertyId = prompt("Property ID to delete");
        Property property = propertyManager.findProperty(propertyId);
        if (property == null) {
            System.out.println("Property not found.");
            return;
        }
        propertyManager.getProperties().remove(property);
        System.out.println("Property " + propertyId + " deleted.");
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

    private void updateUnitRent() {
        String unitId = prompt("Unit ID to update");
        Unit unit = propertyManager.findUnit(unitId);
        if (unit == null) {
            System.out.println("Unit not found.");
            return;
        }
        double newRent = promptDouble("New Monthly Rent");
        if (newRent < 0) {
            System.out.println("Invalid rent amount.");
            return;
        }
        unit.setRentAmount(newRent);
        System.out.println("Rent updated successfully for Unit " + unitId + ".");
    }

    private void deleteUnit() {
        String unitId = prompt("Unit ID to delete");
        Property property = propertyManager.findPropertyByUnitId(unitId);
        if (property == null) {
            System.out.println("Unit or parent property not found.");
            return;
        }
        if (property.removeUnit(unitId)) {
            System.out.println("Unit " + unitId + " removed from property " + property.getPropertyId() + ".");
        } else {
            System.out.println("Failed to remove unit.");
        }
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
