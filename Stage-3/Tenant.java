package com.property.management; // Adjust or remove the package declaration as needed for your project

import java.util.Date;

/**
 * Represents a Tenant in the property management system.
 * Inherits core identity and authentication details from the User class.
 */
public class Tenant extends User {
    // Attributes specific to a Tenant
    private String tenantId;
    private String leaseStatus;
    private String emergencyContact;
    private Unit currentUnit;

    /**
     * Full constructor initializing both parent User attributes and Tenant-specific attributes.
     */
    public Tenant(String userId, String name, String email, String phoneNumber, String passwordHash, String tenantId, Unit currentUnit) {
        // Call the parent User constructor
        super(userId, name, email, phoneNumber, passwordHash);
        
        this.tenantId = tenantId;
        this.currentUnit = currentUnit;
        this.leaseStatus = "Active"; // Defaulting lease status upon creation
        this.emergencyContact = "Not Specified";
    }

    /**
     * Processes a rent payment for the tenant.
     * @param amount The amount to pay.
     * @return A new Payment transaction record.
     */
    public Payment payRent(Double amount) {
        System.out.println("Processing rent payment of $" + amount + " for Tenant: " + getName());
        // In a real application, you would hook this into a payment gateway API.
        return new Payment(this.tenantId, amount, new Date());
    }

    /**
     * Submits a maintenance work order request.
     * @param description Details of the issue.
     * @return A tracking WorkOrder object.
     */
    public WorkOrder submitMaintenanceRequest(String description) {
        System.out.println("Submitting maintenance request: '" + description + "' for Unit: " + 
                (currentUnit != null ? currentUnit.getUnitNumber() : "Unknown"));
        return new WorkOrder(this.tenantId, description, "Pending");
    }

    /**
     * Retrieves the current lease details for the tenant.
     * @return A Lease agreement details object.
     */
    public Lease viewLeaseAgreement() {
        System.out.println("Fetching lease agreement for Tenant ID: " + tenantId);
        return new Lease(this.tenantId, leaseStatus);
    }

    /**
     * Renews the tenant's current lease agreement.
     */
    public void renewLease() {
        this.leaseStatus = "Renewed";
        System.out.println("Lease successfully renewed for Tenant: " + getName());
    }

    // Getters and Setters

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getLeaseStatus() {
        return leaseStatus;
    }

    public void setLeaseStatus(String leaseStatus) {
        this.leaseStatus = leaseStatus;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public Unit getCurrentUnit() {
        return currentUnit;
    }

    public void setCurrentUnit(Unit currentUnit) {
        this.currentUnit = currentUnit;
    }
}
