package Code;

import java.io.Serial;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Goal implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String goalId;
    private String description;
    private Date startDate;
    private Date endDate;
    private String status; // Pending, Approved, Completed

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public Goal(String goalId, String description, Date startDate, Date endDate) {
        if (goalId == null || goalId.isEmpty()) {
            throw new IllegalArgumentException("Goal ID cannot be empty.");
        }
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start and end dates cannot be null.");
        }
        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }

        this.goalId = goalId;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = "Pending"; // Default status
    }



    public String getGoalId() {
        return goalId;
    }

    public String getDescription() {
        return description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (!status.equals("Pending") && !status.equals("Approved") && !status.equals("Completed")) {
            throw new IllegalArgumentException("Invalid status. Must be Pending, Approved, or Completed.");
        }
        this.status = status;
    }

    @Override
    public String toString() {
        return "Goal ID: " + goalId +
                " | Description: " + description +
                " | Start Date: " + sdf.format(startDate) +
                " | End Date: " + sdf.format(endDate) +
                " | Status: " + status;
    }
}
