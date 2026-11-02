import java.util.HashMap;
import java.util.Map;

public class Inspection extends WorkOrder {
    private Map<String, Boolean> checklist; // e.g., {"Plumbing OK": true, "Smoke Detector OK": false}
    private String inspectorNotes;

    public Inspection(String inspectionId, String unitId, String description) {
        super(inspectionId, unitId, description);
        this.checklist = new HashMap<>();
    }

    public void addChecklistItem(String item, boolean passed) {
        checklist.put(item, passed);
    }

    public void setInspectorNotes(String notes) {
        this.inspectorNotes = notes;
    }

    public Map<String, Boolean> getChecklist() { return checklist; }
    public String getInspectorNotes() { return inspectorNotes; }
}
