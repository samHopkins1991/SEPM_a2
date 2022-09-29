import java.util.ArrayList;

public class User {

    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private ArrayList<Ticket> tickets;

    public User (String name, String email, String phoneNumber, String password){
        this.name=name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        //Initialise ticket array.
        this.tickets = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Ticket tickets) {
        this.tickets.add(tickets);
    }

}
