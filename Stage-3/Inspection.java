import java.util.HashMap;
import java.util.Map;

/**
 * Represents an inspection work order equipped with a checklist and inspector notes.
 */
public class Inspection extends ManagerWorkOrder {
    private final Map<String, Boolean> checklist;
    private String inspectorNotes;

    public Inspection(String inspectionId, String unitId, String description, double approvedBudget) {
        super(inspectionId, unitId, description, approvedBudget);
        this.checklist = new HashMap<>();
    }

    public void addChecklistItem(String item, boolean passed) {
        checklist.put(item, passed);
    }

    public void setInspectorNotes(String notes) {
        this.inspectorNotes = notes;
    }

    public Map<String, Boolean> getChecklist() { 
        return checklist; 
    }
    
    public String getInspectorNotes() { 
        return inspectorNotes; 
    }
}
