import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores managed properties and their units, and records which owner has each property.
 */
public class PropertyManager {
    private final Map<String, Property> managedProperties;
    private int nextPropertyId = 1;
    private int nextUnitId = 1;

    public PropertyManager() {
        this.managedProperties = new LinkedHashMap<>();
    }

    public String addProperty(String address, String dwellingType) {
        String propertyId = "PROP" + nextPropertyId++;
        managedProperties.put(propertyId, new Property(propertyId, address, dwellingType, null));
        return propertyId;
    }

    public Unit addUnit(String propertyId, String unitNumber, double rent, int bedrooms, int bathrooms) {
        Property property = findProperty(propertyId);
        if (property == null) {
            return null;
        }
        Unit unit = new Unit("UNIT" + nextUnitId++, unitNumber, rent, bedrooms, bathrooms, propertyId);
        property.addUnit(unit);
        return unit;
    }

    public void assignPropertyToOwner(String propertyId, String ownerId) {
        Property property = findProperty(propertyId);
        if (property != null) {
            property.setOwnerId(ownerId);
        }
    }

    public List<Property> getProperties() {
        return new ArrayList<>(managedProperties.values());
    }

    public List<Property> getPropertiesForOwner(String ownerId) {
        List<Property> results = new ArrayList<>();
        if (ownerId == null) {
            return results;
        }
        for (Property property : managedProperties.values()) {
            if (ownerId.equalsIgnoreCase(property.getOwnerId())) {
                results.add(property);
            }
        }
        return results;
    }

    public Property findProperty(String propertyId) {
        if (propertyId == null) {
            return null;
        }
        for (Property property : managedProperties.values()) {
            if (property.getPropertyId().equalsIgnoreCase(propertyId)) {
                return property;
            }
        }
        return null;
    }

    public Property findPropertyByUnitId(String unitId) {
        for (Property property : managedProperties.values()) {
            if (property.getUnit(unitId) != null) {
                return property;
            }
        }
        return null;
    }

    public Unit findUnit(String unitId) {
        Property property = findPropertyByUnitId(unitId);
        return property == null ? null : property.getUnit(unitId);
    }

    public List<Unit> getVacantUnits() {
        List<Unit> results = new ArrayList<>();
        for (Property property : managedProperties.values()) {
            for (Unit unit : property.getUnits()) {
                if (unit.isVacant()) {
                    results.add(unit);
                }
            }
        }
        return results;
    }
}
