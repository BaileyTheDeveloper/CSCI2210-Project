public class Unit {
    private String unitId;
    private String unitNumber;
    private String propertyId;
    private double monthlyRent;
    private boolean vacancyStatus; // true = Vacant, false = Occupied
    private int bedrooms;
    private int bathrooms;

    public Unit(String unitId, String unitNumber, double monthlyRent, int bedrooms, int bathrooms, String propertyId) {
        this.unitId = unitId;
        this.unitNumber = unitNumber;
        this.propertyId = propertyId;
        this.monthlyRent = monthlyRent;
        this.vacancyStatus = true;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
    }

    public String getUnitId() {
        return unitId;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    public int getBathrooms() {
        return bathrooms;
    }

    public double getRentAmount() {
        return monthlyRent;
    }

    public void setRentAmount(double rent) {
        this.monthlyRent = rent;
    }

    public void markOccupied() {
        this.vacancyStatus = false;
    }

    public void markVacant() {
        this.vacancyStatus = true;
    }

    public boolean isVacant() {
        return vacancyStatus;
    }

    @Override
    public String toString() {
        return String.format("  %s | Unit %s | %d bed / %d bath | $%.2f | %s", getUnitId(),
                getUnitNumber(), getBedrooms(), getBathrooms(), getRentAmount(),
                isVacant() ? "Vacant" : "Occupied");
    }
}