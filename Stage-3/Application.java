import java.util.Date;

public class Application {
    private String applicationID;
    private Date submissionDate;
    private String status;
    private String adminComments;
    private Date reviewDate;
    private String reviewedByAdminID;

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

    public Lease generateLease() {
        return new Lease(
            "LEASE-" + applicationID,
            null,
            null,
            0.0,
            0.0,
            "TBD",
            "TBD"
        );
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