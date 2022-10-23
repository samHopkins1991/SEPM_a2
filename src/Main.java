import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.TimerTask;
import java.util.Timer;

/**
 * Application driver class - runs the application and bootstraps requirements
 * This class houses methods for menus and user logins
 */
public class Main {

    private final int MIN_PASSWORD_LENGTH = 20;
    private final int TIME_IN_SECONDS = 60;// The amount of time set before the archived tickets becomes unavailable.
    private Scanner sc;

    private final ArrayList<User> users = new ArrayList<>(); // Users can be of type User or Type Technician
    private User currentUser; // stores the currently logged-in user null otherwise

    private final ArrayList<Ticket> openTickets = new ArrayList<>();        // All currently Open Tickets
    private final ArrayList<Ticket> archivePool = new ArrayList<>();        // All tickets that are currently closed
    private final ArrayList<Ticket> archivedTickets = new ArrayList<>();    // All tickets that are archived -> view only

    public Main() {
        initialise();
        mainMenu();
        System.out.println("Shutting down!!!");
    }

    /**
     * Bootstraps the application setting up required parameters and users
     */
    private void initialise() {
        this.sc = new Scanner(System.in);
        createUsers();
        createTechnicians();
        initialiseTickets();
    }

    /**
     * prints the main menu - loads specific menus if a user is logged in
     */
    public void mainMenu() {

        System.out.println("What would you like to do?");
        int choice = 0;

        while (true) {
            if (currentUser != null) {
                printMenu(); // if a user is logged in do not show the home screen
            } else {
                choice = getMenuChoice(new String[]{"Existing User Login", "Create New User", "Exit Program"});
                switch (choice) {
                    case 1:
                        loginMenu();
                        break;
                    case 2:
                        newUserScreen();
                        break;
                    case 3:
                        return; // exits loop then exits program
                    default:
                        System.out.println("Please enter a valid choice integer only");
                        break;
                }
            }
        }
    }

    /**
     * asks the user to enter all their current details
     * if they match a user then the password reset action will occur,
     */
    private void resetForgottenPassword() {
        System.out.print("Please enter your email: ");
        String email = sc.nextLine();

        System.out.print("Please enter your name: ");
        String name = sc.nextLine();

        System.out.print("Please enter your phone number: ");
        String phone = sc.nextLine();

        for (User user : users) {
            if (user.getEmail().equals(email)) {
                if (!user.getName().equals(name)) {
                    return;
                }
                if (!user.getPhoneNumber().equals(phone)) {
                    return;
                }
                resetUserPasswordDialog(user); // user matches show reset new password dialog
                return; // return to ensure last code is not run.
            }
        }

        System.out.println("No user with that email exists, perhaps you should create one");
    }

    /**
     * prompts the user to enter a new valid password that meets the strength requirements
     *
     * @param user this should be the user who will have the password changed
     */
    private void resetUserPasswordDialog(User user) {
        boolean successful = false;
        while (!successful) {
            System.out.println("Please enter a password which is at least 20 alphanumeric characters.");
            String p1 = getValidPassword();
            System.out.print("Confirm New Password: ");
            String p2 = sc.nextLine();

            if (p1.equals(p2)) {
                System.out.println("Password Changed Successfully.");
                user.setPassword(p1);
                successful = true;
            } else {
                System.out.println("Passwords entered do not match, please try again.");
            }
        }
    }

    /**
     * Continually asks for a valid password when one is given will return the entered password to calling function
     *
     * @return a valid password
     */
    private String getValidPassword() {
        while (true) {
            System.out.print("Enter a valid Password: ");
            String p1 = sc.nextLine();
            if (isPasswordValid(p1)) {
                return p1;
            }
        }
    }


    /**
     * Verifies if a password is valid
     *
     * @param p1 the password to be validated
     * @return true if valid, false otherwise
     */
    private boolean isPasswordValid(String p1) {
        int capChars = 0, lowChars = 0, digits = 0; // initialization
        char ch;

        //size check, currently returning to menu for user to try subission again
        int sizeCheck = p1.length();
        if (sizeCheck < MIN_PASSWORD_LENGTH) {
            System.out.println("\n The password does not meet the length requirements.");
            return false;
        } else { //check to see if the password contains one or more of the required characters.
            for (int i = 0; i < p1.length(); i++) {
                ch = p1.charAt(i);
                if (Character.isUpperCase(ch))
                    capChars += 1;
                else if (Character.isLowerCase(ch))
                    lowChars += 1;
                else if (Character.isDigit(ch))
                    digits += 1;

            }
        }


        // returns success or failure, failure returns to initial screen to resubmit the request
        if (capChars >= 1 && lowChars >= 1 && digits >= 1) {
            return true;
        } else {
            System.out.println("\nThe Password is weak, please try again.\n ");
            return false;
        }

    }

    /**
     * shows a form for creating a new user and then adds the new user to memory
     */
    public void newUserScreen() {
        String email;
        String name;
        String phoneNumber;
        String password;
        boolean passwordMatch = false;

        System.out.println("*****************");
        System.out.println("New User");
        System.out.println("*****************\n");
        System.out.print("Please enter your Name: ");
        name = this.sc.nextLine();
        System.out.printf("Thankyou, %s. Please enter your email: ", name);
        email = this.sc.nextLine();
        System.out.print("Please enter your phone number: ");
        phoneNumber = this.sc.nextLine();

        // gets a validated password
        password = getValidPassword();

        while (!passwordMatch) {
            System.out.print("Please confirm your password: ");
            if (password.equals(this.sc.nextLine())) {
                passwordMatch = true;
                User u = new User(name, email, phoneNumber, password);
                users.add(u);
                System.out.printf("User %s, was added! %n", u.getName());
            } else {
                System.out.println("Passwords do not match!");
            }
        }
    }


    /**
     * login menu shows options to continue to the login form
     * or the forgot password form
     */
    private void loginMenu() {
        int choice = 0;
        while (choice != 3) {
            if (currentUser != null) {
                printMenu(); // if a user is logged in do not show the home screen
            } else {
                choice = getMenuChoice(new String[]{"Continue to login form", "I forgot my password", "Back to Main"});
                switch (choice) {
                    case 1:
                        showLoginForm();
                        break;
                    case 2:
                        resetForgottenPassword();
                        break;
                    case 3:
                        return; // goes back
                    default:
                        System.out.println("Please enter a valid choice integer only");
                        break;
                }
            }
        }
    }


    /**
     * Login form for existing users
     */
    public void showLoginForm() {
        String email;
        String password;
        System.out.println("*****************");
        System.out.println("LOGIN");
        System.out.println("*****************\n");
        System.out.print("please enter your email: ");
        email = sc.nextLine();
        System.out.print("Please enter your password: ");
        password = sc.nextLine();

        for (User u : users) { // loops through users in search of match.
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                currentUser = u;
                System.out.println("login success");
                printUserWelcome();
                printMenu(); // determines if the user is a technician dynamically
                return;
            }
        }

        System.out.println("You could not be logged in"); // only reached if unsuccessful in login attempt
    }

    /**
     * prints the logout option - to be used in menus where the user is logged-in
     */
    private void printLogoutOption() {
        System.out.println("-1: Logout");
    }

    /**
     * prints a welcome for the currently logged-in user
     */
    private void printUserWelcome() {
        System.out.println("\n*****************");
        System.out.println("Welcome " + currentUser.getName());
    }

    /**
     * Provides a form to create a new ticket
     * @return A valid ticket or null if the creation was cancelled
     */
    private Ticket createTicketDialog() {

        String severity = "";
        System.out.println("Please enter severity level :");
        int choice = 0;

        while (choice < 1 || choice > 4) {
            choice = getMenuChoice(new String[]{"LOW", "MEDIUM", "HIGH", "Cancel Ticket"});
            switch (choice) {
                case 1:
                    severity = "LOW";
                    break;
                case 2:
                    severity = "MEDIUM";
                    break;
                case 3:
                    severity = "HIGH";
                    break;
                case 4:
                    return null;
                default:
                    System.out.println("Please enter a valid choice integer only");
                    break;
            }
        }
        String description = "";
        do {
            System.out.print("Please enter description : ");
            description = sc.nextLine();
            if (description.equalsIgnoreCase("")) {
                System.out.println("Please add a description for the ticket");
            }
        } while (description.equals(""));

        // create and return Ticket
        return createTicket(Severity.valueOf(severity), description, this.currentUser);
    }

    /**
     * Creates and returns the created ticket,
     * Call Create Ticket Dialog instead!
     * @param severity Ticket Severity Level
     * @param description Ticket description
     * @param user User who created the ticket
     * @return Valid Ticket
     */
    private Ticket createTicket(Severity severity, String description, User user){
        Ticket t = new Ticket(severity, description, this.currentUser);
        this.openTickets.add(t);
        return t;
    }

    /**
     * assigns ticket to tech with least ammount of current tickets
     * if there is a tie, it is random.
     *
     * @param ticket the ticket that will be assigned to a technician
     * @return the provided ticket
     */
    public Ticket assignTicket(Ticket ticket) {
        // tracks which level tech needs to be for ticket
        int techLvl;
        // array of technicians at ticket level
        ArrayList<Technician> tch = new ArrayList<>();
        ArrayList<Technician> techsWithLowestTickets = new ArrayList<>();

        // if ticket is high - goes to level 2
        if (ticket.getSeverity() == Severity.HIGH) {
            techLvl = 2;
        } else {
            techLvl = 1;
        }
        // adds technicians to tch array
        for (User u : users) {
            if (u instanceof Technician && ((Technician) u).getLevel() == techLvl) {
                tch.add((Technician) u);
            }
        }
        // loops through array of techs - searching for tech with the lowest tickets
        for (int i = 0; i < tch.size(); i++) {
            // if beginning of array populate with first technician
            if (i == 0) {
                techsWithLowestTickets.add(tch.get(i));
            }
            // if equal no. of tickets, add to techs-with-the-lowest-ticks array
            if (tch.get(i).getNumberOfAssignedTickets() == techsWithLowestTickets.get(0).getNumberOfAssignedTickets()) {
                techsWithLowestTickets.add(tch.get(i));

                // if tech[i].numtickets is less than the first entry of techswithlowestticks (entire array will be the same)
                // then empty array and fill with current tech
            } else if (tch.get(i).getNumberOfAssignedTickets() < techsWithLowestTickets.get(0).getNumberOfAssignedTickets()) {
                techsWithLowestTickets.clear();
                techsWithLowestTickets.add(tch.get(i));
            }
        }

        try{
            Technician t = null;

            if (techsWithLowestTickets.size() == 1) { // if only 1 tech in array - assign ticket to them
                t = techsWithLowestTickets.get(0);
            } else { // if more than one pick random
                Random rand = new Random();
                int numTechs = techsWithLowestTickets.size();
                int randomTechIndex = rand.nextInt(numTechs);
                t = techsWithLowestTickets.get(randomTechIndex);
            }

            t.setAssignedTickets(ticket);   // may throw the error
            ticket.setAssignee(t);          // won't be reached if error thrown
        } catch (NullPointerException e){
            System.out.println("Cannot assign ticket to a technician as no technicians exist!");
        }



        return ticket;
    }

    /**
     * prints menu based on users type
     * if the user is a technician they will be shown the technicians menu
     * otherwise a user will see the users menu
     */
    public void printMenu() {
        // if the user is the system owner display this message
        if (currentUser.isSystemOwner()) System.out.println("You are the System Owner");

        if (currentUser instanceof Technician) {
            technicianMenu();
        } else {
            userMenu();
        }
    }

    /**
     * displays the user menu
     */
    private void userMenu() {
        // options for users:
        // submit ticket
        // view my tickets
        if(currentUser.isSystemOwner()){ displaySystemOwnerMenu(); }
        else { displayGeneralUserMenu(); }
    }



    private void displayGeneralUserMenu() {
        System.out.println("User Menu");
        int choice = 0;

        while (true) { // returns if a valid choice is given after running the appropriate functions
            printLogoutOption(); // -1
            choice = getMenuChoice(new String[]{"Submit Ticket", "View My Tickets"});
            switch (choice) {
                case -1:
                    logoutUser();
                    return;
                case 1:
                    System.out.println("submit ticket");
                    Ticket ticket = createTicketDialog();

                    if (ticket != null) { // Only assign ticket if not "null" in case the user select "cancel ticket"
                        currentUser.AddTicket(ticket); // assign ticket to current user
                        assignTicket(ticket); // assign ticket to a technician
                    }

                    break;
                case 2:
                    System.out.println("view my tickets");
                    viewUserTickets();
                    break;
                // ToDo take this out for final product
                // hidden function for showing ticket assignments -
                // have left in for when we reassign tickets
                case 3:
                    testTickets();
                    break;
                default:
                    System.out.println("Please enter a valid choice integer only");
            }
        }
    }

    private void displaySystemOwnerMenu() {
        System.out.println("Sytem Owner Menu");
        int choice = 0;

        while (true) { // returns if a valid choice is given after running the appropriate functions
            printLogoutOption(); // -1
            choice = getMenuChoice(new String[]{"Submit Ticket", "View My Tickets", "Generate Ticket Report"});
            switch (choice) {
                case -1:
                    logoutUser();
                    return;
                case 1:
                    System.out.println("submit ticket");
                    Ticket ticket = createTicketDialog();

                    if (ticket != null) { // Only assign ticket if not "null" in case the user select "cancel ticket"
                        currentUser.AddTicket(ticket); // assign ticket to current user
                        assignTicket(ticket); // assign ticket to a technician
                    }

                    break;
                case 2:
                    System.out.println("view my tickets");
                    viewUserTickets();
                    break;
                case 3:
                    showTicketReportDialog();
                    break;
                default:
                    System.out.println("Please enter a valid choice integer only");
            }
        }
    }


    /**
     * testing function for ticket assignment.. have left in for when we
     * change severity and reassign tickets
     * todo remove this function
     */
    public void testTickets() {
        for (User u : users) {
            if (u instanceof Technician) {
                System.out.printf("Tech: %s %nLevel: %d%nTicket: %s%n", ((Technician) u).getName(), (((Technician) u).getLevel()), ((Technician) u).getAssignedTickets().toString());
            }
        }
    }

    /**
     * Display user created ticket that the status is open.
     */
    private void viewUserTickets() {
        System.out.println("Current user tickets ");
        //Get current user tickets
        ArrayList<Ticket> currentUserTickets = currentUser.getTickets();
        //Filter tickets that status are open
        for (Ticket ticket : currentUserTickets) {
            if (ticket.getStatus().equals(Status.OPEN)) {
                System.out.println("**************************");
                System.out.println("Ticket Num : " + ticket.getTicketNumber());
                System.out.println("Ticket Severity : " + ticket.getSeverity());
                System.out.println("Ticket Status : " + ticket.getStatus());
                System.out.println("Ticket description : " + ticket.getDescription());
                System.out.println("**************************");

            }
        }
    }

    /**
     * prints a list for the user to read and takes an input
     *
     * @param menuOptions the options to be turned into a menu list
     * @return 0 if the option was invalid or an integer
     */
    private int getMenuChoice(String[] menuOptions) {
        for (int i = 0; i < menuOptions.length; i++) {
            System.out.printf("%d. %s%n", i + 1, menuOptions[i]);
        }

        try {
            return Integer.parseInt(sc.nextLine()); // needs to be parsed this way to avoid from errors;
        } catch (NumberFormatException e) {
            // log the error here
            return 0;
        }
    }

    /**
     * Displays the technicians menu
     */
    private void technicianMenu() {
        // options for technicians:
        // view assigned tickets
        //      -> change status
        //      -> change severity
        // view all closed and archived tickets
        System.out.println("Technician Menu");
        printLogoutOption(); // -1
        int choice = getMenuChoice(new String[]{"View Assigned Tickets", "View Closed Tickets", "View Archived Tickets"});

        switch (choice) {
            case -1:
                logoutUser();
                break;
            case 1:
                System.out.println("View Assigned Tickets");
                //show all open ticket assigned to technician
                //viewTechOpenTickets();
                techViewTicketsMenu();
                break;
            case 2:
                System.out.println("View Closed Tickets");
                viewClosedTickets();
                break;
            case 3:
                System.out.println("View Archived Tickets");
                viewArchivedTickets();
                break;
            default:
                System.out.println("Please enter a valid choice integer only");
        }
    }

    private void viewArchivedTickets() {
        for (Ticket ticket: archivedTickets) {
            System.out.println("**************************");
            System.out.println("Ticket Num : " + ticket.getTicketNumber());
            System.out.println("Ticket Severity : " + ticket.getSeverity());
            System.out.println("Ticket Status : " + ticket.getStatus());
            System.out.println("Ticket description : " + ticket.getDescription());
            System.out.println("**************************");
        }
    }

    //show all closed ticket archived for 24H
    private void viewClosedTickets() {

        if (archivePool.size() > 0) {
            System.out.println("Archived closed tickets ");
            //start counter for choice.
            int i = 0;
            System.out.println("Select a Ticket to view?");
            for (Ticket ticket : archivePool) {
                    System.out.printf("%d. %s%n", i + 1, ticket.getTicketNumber());
                    i++;
            }

            try {
                System.out.println(archivePool.size() + 1 + " Go Back");
                int choice = Integer.parseInt(sc.nextLine()) - 1;
                //Test if the selected choice is to go back.
                if (archivePool.size() == choice) {
                    technicianMenu();
                } else {
                    techViewClosedTickets(choice);
                }

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid number");
                viewClosedTickets();
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Please enter a valid number");
                viewClosedTickets();
            }
        } else {
            System.out.println("There are currently no closed tickets to view!");
        }

    }

    public void techViewClosedTickets(int index){
        Ticket ticket = archivePool.get(index);

        System.out.println("**************************");
        System.out.println("Ticket Num : " + ticket.getTicketNumber());
        System.out.println("Ticket Severity : " + ticket.getSeverity());
        System.out.println("Ticket Status : " + ticket.getStatus());
        System.out.println("Ticket description : " + ticket.getDescription());
        System.out.println("**************************");

        int choice = getMenuChoice(new String[]{"Change Status", "Exit"});

        switch (choice) {
            case 1:
                changeStatusOfArchived(ticket);
                break;
            case 2:
                technicianMenu();
                break;
        }
    }

    private void changeStatusOfArchived(Ticket ticket) {
        ArrayList <Status> values = new ArrayList<>();

        System.out.printf("Current status is: %s%n", ticket.getStatus().toString());
        for (Status s: Status.values()){
            if (ticket.getStatus() != s){
                values.add(s);
            }
        }
        System.out.println("Select a new status to set?");
        System.out.printf("1. %s%n", values.get(0).toString());
        System.out.printf("2. %s%n", values.get(1).toString());
        try {
            int choice = Integer.parseInt(sc.nextLine());
            ticket.setStatus(values.get(choice -1));
            //Re-assign ticket.
            assignTicket(ticket);
            //remove ticket from archived array that was closed.
            archivePool.remove(ticket);

        } catch (NumberFormatException e){
            System.out.println("Please enter a valid choice");
            changeStatusOfArchived(ticket);  // todo remove recursion
        }


    }

    public void techViewTicketsMenu() {

        ArrayList<Ticket> currentTechTickets = ((Technician) currentUser).getAssignedTickets();
//        //Filter tickets that status are open
        if (currentTechTickets.size() > 0) {
            //start counter for choice.
            int i = 0;
            System.out.println("Which Ticket would you like to view?");
            for (Ticket ticket : currentTechTickets) {
                if (ticket.getStatus().equals(Status.OPEN)) {
                    System.out.printf("%d. %s%n", i + 1, ((Technician) currentUser).getAssignedTickets().get(i).getTicketNumber());
                    i++;
                }
            }

            try {
                System.out.println(currentTechTickets.size() + 1 + " Go Back");
                int choice = Integer.parseInt(sc.nextLine()) - 1;
                //Test if the selected choice is to go back.
                if (currentTechTickets.size() == choice) {
                    technicianMenu();
                } else {
                    techViewTicket(choice);
                }

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid number");
                techViewTicketsMenu();
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Please enter a valid number");
                techViewTicketsMenu();
            }
        } else {
            System.out.println("You have not open ticket assigned");
        }


//        for (int i = 0; i < ((Technician) currentUser).getAssignedTickets().size(); i ++){
//            System.out.printf("%d. %s%n", i+1,((Technician) currentUser).getAssignedTickets().get(i).getTicketNumber() );
//        }


    }

    public void techViewTicket(int index) {
        Ticket ticket = ((Technician) currentUser).getAssignedTickets().get(index);

        System.out.println("**************************");
        System.out.println("Ticket Num : " + ticket.getTicketNumber());
        System.out.println("Ticket Severity : " + ticket.getSeverity());
        System.out.println("Ticket Status : " + ticket.getStatus());
        System.out.println("Ticket description : " + ticket.getDescription());
        System.out.println("**************************");

        int choice = getMenuChoice(new String[]{"Change Status", "Change Severity", "Exit"});

        switch (choice) {
            case 1:

                changeStatus(ticket);
                break;
            case 2:
                changeSeverity(ticket);
                break;

            case 3:
                technicianMenu();
                break;
        }


    }


    //To change ticket status
    private void changeStatus(Ticket ticket) {

        ArrayList <Status> values = new ArrayList<>();

        System.out.printf("Current status is: %s%n", ticket.getStatus().toString());
        for (Status s: Status.values()){
            if (ticket.getStatus() != s){
                values.add(s);
            }
        }
        System.out.println("Select a new status to set?");
        System.out.printf("1. %s%n", values.get(0).toString());
        System.out.printf("2. %s%n", values.get(1).toString());
        try {
            int choice = Integer.parseInt(sc.nextLine());
            ticket.setStatus(values.get(choice -1));
            // remove ticket from technician array that was closed.
            ((Technician) currentUser).removeAssignedTicket(ticket);

        } catch
        (NumberFormatException e){
            System.out.println("Please enter a valid choice");
            changeStatus(ticket);
        }

        if (ticket.getStatus() != Status.OPEN){
            ArchiveTimer(ticket);                   // prepare to add ticket to archived tickets
            ticket.setClosedBy(this.currentUser);   // set closing user and closing date
            openTickets.remove(ticket);             // remove closed ticket from open tickets
        }
    }

    //To archive after 24 hours. Testing 60 seconds.
    //Timer variable.
    Timer timer;
    private void ArchiveTimer(Ticket ticketToArchive) {
        timer = new Timer();
        timer.schedule(new ArchiveTickets(ticketToArchive), TIME_IN_SECONDS * 1000);
    }

    //Inner class to add a ticket to closed array.
    class ArchiveTickets extends TimerTask {
        Ticket tempTicket;
        ArchiveTickets(Ticket ticketToArchive){
            System.out.println("Ticket Archived");
            archivePool.add(ticketToArchive);
            this.tempTicket = ticketToArchive;
        }
        public void run() {
            // Remove ticket after 24Hrs
            archivePool.remove(this.tempTicket);

            // if the ticket is reopened do not add it to the archived tickets
            if(this.tempTicket.getStatus() != Status.OPEN){
                archivedTickets.add(this.tempTicket);
            } else {
                openTickets.add(this.tempTicket);
                this.tempTicket.setClosedDate(null);    // remove closed Date
                this.tempTicket.setClosedBy(null);      // remove closing User
                reassignTicket(this.tempTicket);
            }

            // To terminate the thread when the array is empty.
            if (archivePool.size() == 0){
                timer.cancel(); // Terminate the timer thread
            }
        }
    }

    public void changeSeverity(Ticket ticket){
        Severity origSeverity = ticket.getSeverity();
        ArrayList <Severity> values = new ArrayList<>();

        System.out.printf("Current severity is: %s%n", ticket.getSeverity().toString());
        for (Severity s: Severity.values()){
            if (ticket.getSeverity() != s){
                values.add(s);
            }
        }
        System.out.println("What would you like to change the severity to?");
        System.out.printf("1. %s%n", values.get(0).toString());
        System.out.printf("2. %s%n", values.get(1).toString());
        try {
            int choice = Integer.parseInt(sc.nextLine());
            ticket.setSeverity(values.get(choice -1));
        } catch
        (NumberFormatException e){
            System.out.println("Please enter a valid choice");
            changeSeverity(ticket);
        }

        if ((origSeverity == Severity.LOW || origSeverity == Severity.MEDIUM) && ticket.getSeverity() == Severity.HIGH){
            reassignTicket(ticket);
        } else if (origSeverity == Severity.HIGH && ticket.getSeverity() != Severity.HIGH){
            reassignTicket(ticket);
        }

    }

    public void reassignTicket(Ticket ticket){
        assignTicket(ticket);
        ((Technician) currentUser).removeAssignedTicket(ticket);
    }

    /**
     * logs out the current user if one is logged in
     */
    private void logoutUser() {
        System.out.println("Logging out!!!");
        currentUser = null; // clear current user var
    }

    /**
     * creates and adds technicians to the users array
     */
    public void createTechnicians(){
        users.add(new Technician("Harry Styles", "harrystyles@gmail.com", "04123456789", "password123", 1));
        users.add(new Technician("Niall Horan", "niallhoran@gmail.com", "04123456789", "password123", 1));
        users.add(new Technician("Louis Tomlinson", "louistomlinson@gmail.com", "04123456789", "password123", 2));
        users.add(new Technician("Zayn Malik", "zaynmalik@gmail.com", "04123456789", "password123", 2));
        users.add(new Technician("Liam Payne", "liampayne@gmail.com", "04123456789", "password123", 1));
    }

    /**
     * creates and adds users for testing purposes
     * todo remove in production
     */
    public void createUsers(){
        users.add(new User("Test User", "x", "0400000000", "x", true)); // test user creates all test tickets
        users.add(new User("Sam", "Sam","04123456789", "Sam"));
        users.add(new User("Harley", "Harley","04123456789", "Harley"));
        users.add(new User("Alan", "Alan","04123456789", "Alan"));
        users.add(new User("Raf", "Raf","04123456789", "Raf"));
        users.add(new User("Josh", "Josh","04123456789", "Josh"));
    }

    public void initialiseTickets(){

        // this is a test function and assumes a valid user exists

        // set the user
        this.currentUser = users.get(0);

        this.openTickets.add(currentUser.AddTicket(assignTicket(new Ticket(Severity.LOW, "testLow1", this.currentUser))));
        this.openTickets.add(currentUser.AddTicket(assignTicket(new Ticket(Severity.MEDIUM, "testMedium1", this.currentUser))));
        this.openTickets.add(currentUser.AddTicket(assignTicket(new Ticket(Severity.HIGH, "TestHigh1", this.currentUser))));
        this.openTickets.add(currentUser.AddTicket(assignTicket(new Ticket(Severity.LOW, "testLow2", this.currentUser))));
        this.openTickets.add(currentUser.AddTicket(assignTicket(new Ticket(Severity.MEDIUM, "testMedium2", this.currentUser))));
        this.openTickets.add(currentUser.AddTicket(assignTicket(new Ticket(Severity.HIGH, "TestHigh2", this.currentUser))));
        this.openTickets.add(currentUser.AddTicket(assignTicket(new Ticket(Severity.LOW, "testLow3", this.currentUser))));
        this.openTickets.add(currentUser.AddTicket(assignTicket(new Ticket(Severity.MEDIUM, "testMedium3", this.currentUser))));
        this.openTickets.add(currentUser.AddTicket(assignTicket(new Ticket(Severity.HIGH, "TestHigh3", this.currentUser))));
        this.openTickets.add(currentUser.AddTicket(assignTicket(new Ticket(Severity.LOW, "testLow4", this.currentUser))));
        this.openTickets.add(currentUser.AddTicket(assignTicket(new Ticket(Severity.MEDIUM, "testMedium4", this.currentUser))));
        this.openTickets.add(currentUser.AddTicket(assignTicket(new Ticket(Severity.HIGH, "TestHigh4", this.currentUser))));

        // unset user
        this.currentUser = null;
    }

    private void showTicketReportDialog() {

        System.out.println("Enter from Date inclusive:");

        // get starting inclusive date
        System.out.print("Please Enter a Year: ");
        int fromYear = getValidYear();
        System.out.print("Please Enter a Month: ");
        int fromMonth = getValidMonth();
        System.out.print("Please Enter a Day: ");
        int fromDayOfMonth = getValidDay(fromYear, fromMonth);

        System.out.println("Enter to Date inclusive:");

        // get ending inclusive date
        System.out.print("Please Enter a Year: ");
        int toYear = getValidYear();
        System.out.print("Please Enter a Month: ");
        int toMonth = getValidMonth();
        System.out.print("Please Enter a Day: ");
        int toDayOfMonth = getValidDay(toYear, toMonth);

        printTicketReport(LocalDate.of(fromYear, fromMonth, fromDayOfMonth), LocalDate.of(toYear, toMonth, toDayOfMonth));
    }

    public int getValidYear(){
        int choice = -1;

        while (true){
            try {
                choice = Integer.parseInt(sc.nextLine()); // needs to be parsed this way to avoid from errors;
                if(choice <= 0 || choice > LocalDate.now().getYear()) {
                    throw new NumberFormatException("Year must not be greater then " + LocalDate.now().getYear() + " inclusive: ");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.print(e.getMessage());
            }
        }

        return choice;
    }

    public int getValidMonth(){
        int choice = -1;

        while (true){
            try {
                choice = Integer.parseInt(sc.nextLine()); // needs to be parsed this way to avoid from errors;
                if(choice <= 0 || choice > 12) {
                    throw new NumberFormatException("Month must be between 1 and 12 inclusive: ");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.print(e.getMessage());
            }
        }

        return choice;
    }

    public int getValidDay(int year, int month){
        int choice = -1;

        YearMonth date = YearMonth.of(year,month);
        int lastDayValue = date.getMonth().length(date.isLeapYear());

        while (true){
            try {
                choice = Integer.parseInt(sc.nextLine()); // needs to be parsed this way to avoid from errors;
                if(choice <= 0 || choice > lastDayValue) {
                    throw new NumberFormatException("Day of Month must be between 1 and " + lastDayValue + " inclusive: ");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.print(e.getMessage());
            }
        }

        return choice;
    }




    private void printTicketReport(LocalDate dateOpen, LocalDate dateClosed){

        // temp array list used to show tickets when collected for report
        ArrayList<Ticket> summarizedTickets = new ArrayList<>();

        int ticketsCount        = 0;    // how many tickets are in the selected period
        int ticketsOpenCount    = 0;    // how many tickets are open
        int ticketsCUCount      = 0;    // how many tickets are closed and unresolved
        int ticketsCRCount      = 0;    // how many tickets are closed and resolved
        int ticketsARCount      = 0;    // how many tickets are archived

        for (Ticket ticket: this.openTickets){
            if(ticket.getCreationDate().compareTo(dateOpen) >= 0){
                if(ticket.getClosedDate() == null || ticket.getClosedDate().compareTo(dateClosed) <= 0){
                    ticketsCount++;
                    summarizedTickets.add(ticket);
                }
            }
        }

        for (Ticket ticket: this.archivePool){
            if(ticket.getCreationDate().compareTo(dateOpen) >= 0){
                if(ticket.getClosedDate() == null || ticket.getClosedDate().compareTo(dateClosed) <= 0){
                    if(ticket.getStatus() == Status.CLOSE_R)    ticketsCRCount++;
                    if(ticket.getStatus() == Status.CLOSE_UR)   ticketsCUCount++;
                    ticketsCount++;
                    summarizedTickets.add(ticket);
                }
            }
        }

        for (Ticket ticket: this.archivedTickets){
            if(ticket.getCreationDate().compareTo(dateOpen) >= 0){
                if(ticket.getClosedDate() == null || ticket.getClosedDate().compareTo(dateClosed) <= 0){
                    ticketsCount++;
                    ticketsARCount++;
                    summarizedTickets.add(ticket);
                }
            }
        }

        // display ticket table
        // for each ticket in the report show --
        // who opened it
        // date opened
        // the severity of the ticket
        // is the ticket closed
        // who attended to it -- if ticket is reassigned it shows only the last technician or the tech who closed it
        // time taken to close ticket
        System.out.println("================================================================================================");
        System.out.printf("%-20s | %-10s | %-10s | %-7s | %-20s | %-11s |\n", "Opened By", "Date Opened", "Severity", "Closed?", "Assigned to", "Days opened");

        for (Ticket ticket: summarizedTickets){

            String openedby             = ticket.getOpenBy().getName();
            String dateOpened           = ticket.getCreationDate().toString();
            String ticketSeverity       = ticket.getSeverity().toString();
            String isTicketClosed       = String.valueOf((ticket.getStatus() != Status.OPEN));

            StringBuilder assignedTo    = new StringBuilder("");
            if (ticket.getClosedBy() == null){
                assignedTo.append(ticket.getAssignee() == null ? "Not Assigned" : ticket.getAssignee().getName());
            } else {
                assignedTo.append(ticket.getClosedBy().getName());
            }

            StringBuilder daysOpened    = new StringBuilder("");
            if(ticket.getClosedDate() == null){
                daysOpened.append(ticket.getCreationDate().compareTo(LocalDate.now()));
            } else {
                daysOpened.append(ticket.getCreationDate().compareTo(ticket.getClosedDate()));
            }

            System.out.printf("%-20s | %-11s | %-10s | %-7s | %-20s | %-11s |\n", openedby, dateOpened, ticketSeverity, isTicketClosed, assignedTo, daysOpened);
        }
        System.out.println("================================================================================================");

        // display ticket summary

        System.out.println();
        System.out.println("========================");
        System.out.println("|        SUMMARY       |");
        System.out.println("========================");
        System.out.println("Total Tickets: " + ticketsCount);
        System.out.println("# Tickets opened: " + ticketsOpenCount);
        System.out.println("# Tickets resolved: " + ticketsCRCount);
        System.out.println("# Tickets unresolved: " + ticketsCUCount);
        System.out.println("# Tickets archived: " + ticketsARCount);
        System.out.println("======================== \n");

    }

    /**
     * runs the application
     * @param args an array or program arguments
     */
    public static void main(String[] args) {
        new Main();
    }
}