public class Owner extends User {
    private String ownerId;
    private List<Property> ownedProperties;

    // Constructor matching the exact UML parameter list
    public Owner(String userId, String name, String email, String phoneNumber, String passwordHash, String ownerId) {
        // Pass base User credentials to the superclass constructor
        super(userId, name, email, phoneNumber, passwordHash);
        this.ownerId = ownerId;
        // Initialize the collection to avoid NullPointerExceptions when adding properties
        this.ownedProperties = new ArrayList<>();
    }

    // Displays mock financial statement data for properties
    public void viewFinancialStatements() {
        System.out.println("--- Financial Statements for Owner: " + getName() + " (ID: " + ownerId + ") ---");
        if (ownedProperties.isEmpty()) {
            System.out.println("No properties found. No statements available.");
        } else {
            System.out.println("Generating financial statements for " + ownedProperties.size() + " property/properties...");
            // Example processing placeholder
            for (Property p : ownedProperties) {
                System.out.println("[Statement Summary] " + p + " - Status: Healthy");
            }
        }
    }

    // Returns the list of properties owned
    public List<Property> getPropertiesOwned() {
        return this.ownedProperties;
    }

    // Adds a new property to the owner's portfolio
    public void addProperty(Property property) {
        if (property != null) {
            this.ownedProperties.add(property);
            System.out.println("Successfully added " + property + " to Owner " + getName());
        }
    }

    // Getter for ownerId
    public String getOwnerId() {
        return ownerId;
    }
}
