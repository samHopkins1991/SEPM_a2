//this class will be the main driver, running login and menu work
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    //Test current user logged.
    private User currentUser;
    private final Scanner sc;
    
    
    //Req'd variables for passwordCheck function
    private final int PASSWORD_LENGTH = 20;

    // Users can be of type User or Type Technician, this ArrayList stores both
    // The Initial Values are hardcoded as per specification
    private ArrayList<User> users = new ArrayList<>();

    public Main(){
        createTechnicians();
        createUsers();
        this.sc = new Scanner(System.in);
        mainMenu();
        System.out.println("Shutting down!!!");
    }

    // prints initial screen
    // login or create user
    public void mainMenu(){

        System.out.println("What would you like to do?");
        int choice = 0;

        while(choice != 4){
            choice = getMenuChoice(new String[] {"Existing User Login", "Create New User", "Forgot My Password", "Exit Program"});
            switch (choice) {
                case 1:
                    loginScreen();
                    break;
                case 2:
                    newUserScreen();
                    break;
                case 3:
                    resetForgottenPassword();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Please enter a valid choice integer only");
                    break;
            }
        }


    }

    private void resetForgottenPassword() {
        // asks the user to enter all their current details and if they match a user then the password reset action will occur,

        System.out.print("Please enter your email: ");
        String email = sc.nextLine();

        System.out.print("Please enter your name: ");
        String name = sc.nextLine();

        System.out.print("Please enter your phone number: ");
        String phone = sc.nextLine();

        for (User user: users) {
            if(user.getEmail().equals(email)) {
                if(!user.getName().equals(name)){ return; }
                if(!user.getPhoneNumber().equals(phone)){ return; }
                // user matches show reset new password dialog
                resetUserPasswordDialog(user);
                return; // return to ensure last code is not run.
            }
        }

        // didn't return. printing that no user was found
        System.out.println("No user with that email exists, perhaps you should create one");
    }

    private void resetUserPasswordDialog(User user) {
        boolean successful = false;
        while(!successful){
            System.out.println("Please enter a password which is at least 20 alphanumeric characters.");
            String p1 = getValidPassword();
            System.out.print("Confirm New Password: ");
            String p2 = sc.nextLine();

            if(p1.equals(p2)){
                //added validation on success for the user to know it worked. Return to initial screen if no match.
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
     * @return a valid password
     */
    private String getValidPassword() {
        while(true){
            System.out.print("Enter a valid Password: ");
            String p1 = sc.nextLine();
            if(isPasswordValid(p1)){
               return p1;
            }
        }
    }


    //function to validate strength of password
    private boolean isPasswordValid(String p1) {
        int capChars = 0, lowChars = 0, digits = 0; // initialization
        char ch;

        //size check, currently returning to menu for user to try subission again
        int sizeCheck = p1.length();
        if(sizeCheck < PASSWORD_LENGTH) {
            System.out.println("\nThe password does not meet the length requirements.");
            return false;
        }
        else { //check to see if the password contains one or more of the required characters.
            for (int i = 0; i < PASSWORD_LENGTH; i++)
            {
                ch = p1.charAt(i);
                if(Character.isUpperCase(ch))
                    capChars = 1;
                else if(Character.isLowerCase(ch))
                    lowChars = 1;
                else if(Character.isDigit(ch))
                    digits = 1;
                
            }
        }
        //returns success or failure, failure returns to initial screen to resubmit the request
        if(capChars==1 && lowChars==1 && digits==1) {
            return true;
        }
        else {
            System.out.println("\nThe Password is weak, please try again.\n ");
            return false;
        }
        
    }


    // screen for creating new users
    // adds new users to users arraylist
    public void newUserScreen(){
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

        while (!passwordMatch){
            System.out.print("Please confirm your password: ");
            if (password.equals(this.sc.nextLine())){
                passwordMatch = true;
                User u = new User(name, email, phoneNumber, password);
                users.add(u);
                System.out.printf("User %s, was added! %n", u.getName());
            }
            else{
                System.out.println("Passwords do not match!");
            }
        }
    }

    // login screen for existing users
    public void loginScreen() {
        String email;
        String password;
        System.out.println("*****************");
        System.out.println("LOGIN");
        System.out.println("*****************\n");
        System.out.print("please enter your email: ");
        email = sc.nextLine();
        System.out.print("Please enter your password: ");
        password = sc.nextLine();

        // loops through users in search of match.
        for (User u : users) {
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                currentUser = u;
                System.out.println("login success");
                printUserWelcome();
                printMenu(); // determines if the user is a technician dynamically
                return;
            }
        }

        // only reached if unsuccessful in login attempt
        System.out.println("You could not be logged in");
    }

    private void printLogoutOption(){
        System.out.println("-1: Logout");
    }

    private void printUserWelcome(){
        System.out.println("*****************");
        System.out.println("Welcome " + currentUser.getName());
    }

    /**
     * prints menu based on users type
     * if the user is a technician they will be shown the technicians menu
     * otherwise a user will see the users menu
     */

    //Submit ticket (Ticket creation)
    private Ticket createTicket(){

        String severity = "";
        System.out.println("Please enter severity level :");
        int choice = 0;

        while(choice < 1 || choice > 4){
            choice = getMenuChoice(new String[] {"LOW", "MEDIUM", "HIGH", "Cancel Ticket"});
            switch (choice) {
                case 1:
                    severity="LOW";
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
        do{
            System.out.print("Please enter description : ");
            description = sc.nextLine();
            if (description.equalsIgnoreCase("")){
                System.out.println("Please add a description for the ticket");
            }
        }while (description.equals(""));

        //create and return Ticket
        return new Ticket(Severity.valueOf(severity), description);
    }

    // assigns ticket to tech with least ammount of current tickets
    // if there is a tie, it is random.
    public void assignTicket(Ticket ticket){
        // tracks which level tech needs to be for ticket
        int techLvl;
        // array of technicians at ticket level
        ArrayList<Technician> tch = new ArrayList<>();
        ArrayList<Technician> techsWithLowestTickets = new ArrayList<>();

        // if ticket is high - goes to level 2
        if (ticket.getSeverity() == Severity.HIGH){
            techLvl = 2;
        } else {
            techLvl = 1;
        }
        // adds technicians to tch array
        for (User u: users) {
            if (u instanceof Technician && ((Technician) u).getLevel() == techLvl) {
                tch.add((Technician) u);
            }
        }
        // loops through array of techs - searching for tech with the lowest tickets
        for (int i = 0; i < tch.size(); i ++){
            // if beginning of array populate with first technician
            if (i == 0) {
                techsWithLowestTickets.add(tch.get(i));
            }
            // if equal no. of tickets, add to techs-with-the-lowest-ticks array
            if (tch.get(i).getNumberOfAssignedTickets() == techsWithLowestTickets.get(0).getNumberOfAssignedTickets()){
                techsWithLowestTickets.add(tch.get(i));

                // if tech[i].numtickets is less than the first entry of techswithlowestticks (entire array will be the same)
                // then empty array and fill with current tech
            } else if (tch.get(i).getNumberOfAssignedTickets() < techsWithLowestTickets.get(0).getNumberOfAssignedTickets()) {
                techsWithLowestTickets.clear();
                techsWithLowestTickets.add(tch.get(i));
            }
        }

        // if only 1 tech in array - assign ticket to them
        if (techsWithLowestTickets.size() == 1) {
            techsWithLowestTickets.get(0).setAssignedTickets(ticket);

            // if more than one pick random
        } else {
            Random rand = new Random();
            int numTechs = techsWithLowestTickets.size();
            int randomTechIndex = rand.nextInt(numTechs);

            techsWithLowestTickets.get(randomTechIndex).setAssignedTickets(ticket);
        }
    }

    public void printMenu(){
        if (currentUser instanceof Technician){
            technicianMenu();
        } else {
            userMenu();
        }
    }

    private void userMenu() {
        // options for users:
        // submit ticket
        // view my tickets
        System.out.println("User Menu");
        int choice = 0;

        while(true){ // returns if a valid choice is given after running the appropriate functions
            printLogoutOption(); // -1
            choice = getMenuChoice(new String[] {"Submit Ticket", "View My Tickets"});
            switch (choice) {
                case -1:
                    logoutUser();
                    return;
                case 1:
                    System.out.println("submit ticket");
                    Ticket ticket = createTicket();
                    assignTicket(ticket);

                    //Only assign ticket if not "null" in case the user select "cancel ticket"
                    if (ticket != null){
                        //assign ticket to current user
                        currentUser.setTickets(ticket);
                        //Call your assign ticket method here

//                        System.out.println("Ticket Num : " + ticket.getTicketNumber());
//                        System.out.println("Ticket Severity : " + ticket.getSeverity());
//                        System.out.println("Ticket Status : " + ticket.getStatus());
//                        System.out.println("Ticket description : " + ticket.getDescription());


                    }



                    break;
                case 2:
                    System.out.println("view my tickets");
                    viewUserTickets();

                    break;
                //ToDo take this out for final product
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

    //testing function for ticket assignment.. have left in for when we
    // change severity and reassign tickets
    public void testTickets(){
        for (User u: users){
            if (u instanceof Technician){
                System.out.printf("Tech: %s %nLevel: %d%nTicket: %s%n",((Technician) u).getName(), (((Technician) u).getLevel()), ((Technician) u).getAssignedTickets().toString());
            }
        }
    }

    //Display user created ticket that the status is open.
    private void viewUserTickets() {
        System.out.println("Current user tickets ");
        //Get current user tickets
        ArrayList<Ticket> currentUserTickets = currentUser.getTickets();
        //Filter tickets that status are open
        for (Ticket ticket:currentUserTickets) {
            if (ticket.getStatus().equals(Status.OPEN)){
                System.out.println("**************************");
                System.out.println("Ticket Num : " + ticket.getTicketNumber());
                System.out.println("Ticket Severity : " + ticket.getSeverity());
                System.out.println("Ticket Status : " + ticket.getStatus());
                System.out.println("Ticket description : " + ticket.getDescription());
                System.out.println("**************************");

            }
        }


    }

    private int getMenuChoice(String[] menuOptions) {
        for (int i = 0; i < menuOptions.length; i++){
            System.out.printf("%d. %s%n",i +1, menuOptions[i]);
        }
        if (sc.hasNextInt()){
            return Integer.parseInt(sc.nextLine()); // needs to be parsed this way to avoid from errors;
        }
        //To consume the carry return of hasNextInt
        sc.nextLine();
       return 0;
    }

    private void technicianMenu() {
        // options for technicians:
        // view assigned tickets
        //      -> change status
        //      -> change severity
        // view all closed and archived tickets
        System.out.println("Technician Menu");
        printLogoutOption(); // -1
        int choice = getMenuChoice(new String[] {"View Assigned Tickets", "View Closed and Archived Tickets"});

        while(true){
            switch (choice) {
                case -1:
                    logoutUser();
                    return;
                case 1:
                    System.out.println("View Assigned Tickets");
                    return;
                case 2:
                    System.out.println("View Closed and Archived Tickets");
                    return;
                default:
                    System.out.println("Please enter a valid choice integer only");
            }
        }

    }

    private void logoutUser() {
        System.out.println("Logging out!!!");
        currentUser = null; //clear current user var
    }

    // hardcoded as per assignment spec
    // creates technicians at beginning of application
    public void createTechnicians(){
        Technician a = new Technician("Harry Styles", "harrystyles@gmail.com", "04123456789", "password123", 1);
        Technician b = new Technician ("Niall Horan", "niallhoran@gmail.com", "04123456789", "password123", 1);
        Technician c = new Technician("Louis Tomlinson", "louistomlinson@gmail.com", "04123456789", "password123", 2);
        Technician d = new Technician("Zayn Malik", "zaynmalik@gmail.com", "04123456789", "password123", 2);
        Technician e = new Technician ("Liam Payne", "liampayne@gmail.com", "04123456789", "password123", 1);
        // adds technicians to arraylist
        users.add(a);
        users.add(b);
        users.add(c);
        users.add(d);
        users.add(e);

    }
    // hardcoding users for quick login for testing
    public void createUsers(){
        User a = new User("Sam", "Sam","04123456789", "Sam");
        User b = new User("Harley", "Harley","04123456789", "Harley");
        User c = new User("Alan", "Alan","04123456789", "Alan");
        User d = new User("Raf", "Raf","04123456789", "Raf");
        User e = new User("Josh", "Josh","04123456789", "Josh");

        // adds users to array list
        users.add(a);
        users.add(b);
        users.add(c);
        users.add(d);
        users.add(e);
    }

    public static void main(String[] args) {
        new Main();
    }
}
