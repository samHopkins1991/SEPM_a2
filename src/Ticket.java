import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.Deque;

public class Ticket {

    private  String ticketNumber;
    public static int autoInt = 1000;
    private Status status;
    private Severity severity;
    private String description;
    private LocalDate creationDate;
    private LocalDate closedDate;
    private User openBy;
    private User closedBy;

    public Ticket(Severity severity) {
        //create an open ticket by default on creation.

        ticketNumber = "TN-" + autoInt++;
        this.status = Status.OPEN;
        this.severity = severity;
        this.description = "";
    }

    public Ticket(Severity severity, String description) {
        //create an open ticket by default on creation.
        this.ticketNumber = "TN-" + autoInt++;
        this.status = Status.OPEN;
        this.severity = severity;
        this.description = description;
        //Create local date.
        this.creationDate = LocalDate.now();
    }

    public Ticket(Severity severity, String description, User openBy) {
        //create an open ticket by default on creation.
        this.ticketNumber = "TN-" + autoInt++;
        this.status = Status.OPEN;
        this.severity = severity;
        this.description = description;
        this.openBy = openBy;
        //Create local date.
        this.creationDate = LocalDate.now();
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

    public  String getTicketNumber() {
        return this.ticketNumber;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getClosedDate() {
        return closedDate;
    }
    //set the closed date.
    public void setClosedDate() {
        this.closedDate = LocalDate.now();
    }

    public User getOpenBy() {
        return openBy;
    }

    public void setOpenBy(User openBy) {
        this.openBy = openBy;
    }

    public User getClosedBy() {
        return closedBy;
    }

    public void setClosedBy(User closedBy) {
        this.closedBy = closedBy;
    }
}

