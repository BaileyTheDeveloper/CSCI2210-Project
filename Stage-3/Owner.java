import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user who owns rental properties.
 */
public class Owner extends User {
    private final List<Property> ownedProperties;

    public Owner(String userId, String name, String email, String phoneNumber, String password) {
        super(userId, name, email, phoneNumber, password);
        this.ownedProperties = new ArrayList<>();
    }

    public void viewFinancialStatements() {
        System.out.println("Financial statements are not available yet.");
    }

    public List<Property> getPropertiesOwned() {
        return new ArrayList<>(ownedProperties);
    }

    public void addProperty(Property property) {
        if (property == null) {
            System.out.println("Error: Property cannot be null.");
            return;
        }
        ownedProperties.add(property);
    }
}
