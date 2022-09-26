


public class Ticket {

    private Status status;
    private Severity severity;
    private String description;

    public Ticket(Severity severity) {
        //create an open ticket by default on creation.
        this.status = Status.OPEN;
        this.severity = severity;
        this.description = "";
    }

    public Ticket(Severity severity, String description) {
        //create an open ticket by default on creation.
        this.status = Status.OPEN;
        this.severity = severity;
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

