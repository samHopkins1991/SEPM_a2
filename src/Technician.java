public class Technician extends User{
    private Ticket[] assignedTickets;
    private int level;

    public Technician(String name, String email, String phoneNumber, String password, int level) {
        super(name, email, phoneNumber, password);
        this.level = level;
    }

    public Ticket[] getAssignedTickets() {
        return assignedTickets;
    }

    public void setAssignedTickets(Ticket[] assignedTickets) {
        this.assignedTickets = assignedTickets;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
