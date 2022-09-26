


public class Ticket {

    private String status;
    private String severity;
    private String description;

    public Ticket(String severity) {
        //create an open ticket by default on creation.
        this.status = String.valueOf(Status.OPEN);
        this.severity = severity;
        this.description = "";
    }

    public Ticket(String severity, String description) {
        //create an open ticket by default on creation.
        this.status = String.valueOf(Status.OPEN);
        this.severity = severity;
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

