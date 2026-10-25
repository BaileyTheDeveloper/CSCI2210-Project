import java.util.HashMap;
import java.util.Map;

public class Property {
    private String propertyId;
    private String address;
    private String dwellingType;
    private String ownerId;
    private Map<String, Unit> units;

    public Property(String propertyId, String address, String dwellingType, String ownerId) {
        this.propertyId = propertyId;
        this.address = address;
        this.dwellingType = dwellingType;
        this.ownerId = ownerId;
        this.units = new HashMap<>();
    }

    public void addUnit(Unit unit) {
        units.put(unit.getUnitId(), unit);
    }

    public boolean removeUnit(String unitId) {
        if (units.containsKey(unitId)) {
            units.remove(unitId);
            return true;
        }
        return false;
    }

    public Unit getUnit(String unitId) {
        return units.get(unitId);
    }

    public int getTotalUnits() {
        return units.size();
    }

    public int getVacantUnits() {
        int count = 0;
        for (Unit unit : units.values()) {
            if (unit.isVacant()) {
                count++;
            }
        }
        return count;
    }

    public int getOccupiedUnits() {
        int count = 0;
        for (Unit unit : units.values()) {
            if (!unit.isVacant()) {
                count++;
            }
        }
        return count;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}