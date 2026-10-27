import java.util.ArrayList;
import java.util.List;

/**
 * Represents a person applying to rent a unit.
 */
public class Applicant extends User {
    private final String applicantID;
    private String status;
    private final List<String> applicationHistory;

    public Applicant(String userId, String name, String email, String phoneNumber,
                     String password, String applicantID) {
        super(userId, name, email, phoneNumber, password);
        this.applicantID = applicantID;
        this.status = "New";
        this.applicationHistory = new ArrayList<>();
    }

    public String getApplicantID() {
        return applicantID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getApplicationHistory() {
        return new ArrayList<>(applicationHistory);
    }

    public Application apply(Unit unit) {
        throw new UnsupportedOperationException("Application creation will be added with the Application class.");
    }
}
