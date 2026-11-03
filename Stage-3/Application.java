import java.util.Date;

public class Application {
    private String applicationID;
    private Date submissionDate;
    private String status;
    private String adminComments;
    private Date reviewDate;
    private String reviewedByAdminID;
    private String applicantId;
    private String unitId;

    public Application(String applicationID, Date submissionDate, String status) {
        this.applicationID = applicationID;
        this.submissionDate = submissionDate;
        this.status = status != null ? status : "Pending";
    }

    public void submitApplication() {
        this.status = "Submitted";
    }

    public void updateStatus(String status) {
        this.status = status;
    }

    public String getDetails() {
        return "Application ID: " + applicationID + " | Status: " + status + " | Submitted: " + submissionDate;
    }

    public String getApplicationID() {
        return applicationID;
    }

    public String getStatus() {
        return status;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setApplicantAndUnit(String applicantId, String unitId) {
        this.applicantId = applicantId;
        this.unitId = unitId;
    }

    public void setAdminComments(String adminComments) {
        this.adminComments = adminComments;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public void setReviewedByAdminID(String reviewedByAdminID) {
        this.reviewedByAdminID = reviewedByAdminID;
    }
}