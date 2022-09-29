import java.util.ArrayList;

public class Technician extends User{
    //private Ticket[] assignedTickets;
    private ArrayList<Ticket> assignedTickets;
    private int level;

    public Technician(String name, String email, String phoneNumber, String password, int level) {
        super(name, email, phoneNumber, password);
        this.level = level;
        this.assignedTickets = new ArrayList<>();
    }

    public ArrayList<Ticket> getAssignedTickets() {
        return assignedTickets;
    }

    public void setAssignedTickets(Ticket assignedTicket) {
        this.assignedTickets.add(assignedTicket);
    }

    public int getNumberOfAssignedTickets(){
        return assignedTickets.size();
    }

//    private int getTicketIndex(String ticketNumber){
//        return assignedTickets.indexOf(ticketNumber);
//    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}
