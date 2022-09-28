//this class will be the main driver, running login and menu work
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    //Test current user logged.
    private User currentUser;
    private final Scanner sc;
    
    
    //Req'd variables for passwordCheck function
    int passwordLength = 20;
    int capChars, lowChars, digits = 0;
    char ch;

    // Users can be of type User or Type Technician, this ArrayList stores both
    // The Initial Values are hardcoded as per specification
    private ArrayList<User> users = new ArrayList<>();

    public Main(){
        createTechnicians();
        createUsers();
        this.sc = new Scanner(System.in);
        initialScreen();

    }

    // prints initial screen
    // login or create user
    public void initialScreen(){

        System.out.println("What would you like to do?");
        int choice = getMenuChoice(new String[] {"Existing User Login", "Create New User", "Forgot My Password", "Exit Program"});

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
                System.out.println("Shutting down!!!");
                break;
            default:
                System.out.println("Please enter a valid choice integer only");
                initialScreen();
                break;
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
                initialScreen();
            }
        }

        // didnt return printing that no user was found
        System.out.println("No user with that email exists, perhaps you should create one");
    }

    private void resetUserPasswordDialog(User user) {
        boolean successful = false;
        while(!successful){
            System.out.println("Please enter a password which is at least 20 alphanumeric characters.\n");
            System.out.print("Enter New Password: ");
            String p1 = sc.nextLine(); // Completed.
            passwordReqCheck(p1); //as below
            System.out.print("Confirm New Password: ");
            String p2 = sc.nextLine();

            if(p1.equals(p2)){
                //added validation on success for the user to know it worked. Return to initial screen if no match.
                System.out.println("Password Changed Successfully.");
                user.setPassword(p1);
                successful = true;
            } else {
                System.out.println("Passwords entered do not match, please try again.");
                initialScreen();
            }
        }
    }
    
    
    //function to validate strength of password
    private void passwordReqCheck(String p1) {
        
        //size check, currently returning to menu for user to try subission again
        int sizeCheck = p1.length();
        if(sizeCheck<passwordLength) {
            System.out.println("\nThe password does not meet the length requirements. Please resubmit your password reset request.\n");
            initialScreen();
        }
        else
        //check to see if the password contains one or more of the required characters.
        {
            for (int i = 0; i < passwordLength; i++)
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
            System.out.println("\nPassword meets requirements.\n");
        }
        else {
            System.out.println("\nThe Password is weak, please try again.\n ");
            initialScreen();
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
        System.out.println("*****************");
        System.out.println();
        System.out.println("Please enter your Name");
        name = this.sc.nextLine();
        System.out.printf("Thankyou, %s. Please enter your email. %n", name);
        email = this.sc.nextLine();
        System.out.println("Please enter your phone number");
        phoneNumber = this.sc.nextLine();

        while (!passwordMatch){
            System.out.println("Please enter a password");
            password = this.sc.nextLine();
            System.out.println("Please re-enter your password");
            if (password.equals(this.sc.nextLine())){
                passwordMatch = true;
                User u = new User(name, email, phoneNumber, password);
                users.add(u);
                initialScreen();
            } else {
                System.out.println("Passwords did not match");
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
        System.out.println("please enter your email");
        email = sc.nextLine();
        System.out.println("Please enter your password");
        password = sc.nextLine();

        // loops through users in search of match.
        for (User u : users) {
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                currentUser = u;
                printMenu(); // determines if the user is a technician dynamically
                System.out.println("login success");
                printUserWelcome();
                return;
            }
        }

        // only reached if unsuccessful in login attempt
        System.out.println("You could not be logged in");
        initialScreen();
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
        printLogoutOption(); // -1
        int choice = getMenuChoice(new String[] {"Submit Ticket", "View My Tickets"});
        switch (choice) {
            case -1:
                logoutUser();
                break;
            case 1:
                System.out.println("submit ticket");
                break;
            case 2:
                System.out.println("view my ticket");
                break;
            default:
                System.out.println("Please enter a valid choice integer only");
                userMenu();
                break;
        }
    }

    private int getMenuChoice(String[] menuOptions) {
        for (int i = 0; i < menuOptions.length; i++){
            System.out.printf("%d. %s%n",i +1, menuOptions[i]);
        }
        int choice = Integer.parseInt(sc.nextLine()); // needs to be parsed this way to avoid from errors
        return choice;
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

        switch (choice) {
            case -1:
                logoutUser();
                break;
            case 1:
                System.out.println("View Assigned Tickets");
                break;
            case 2:
                System.out.println("View Closed and Archived Tickets");
                break;
            default:
                System.out.println("Please enter a valid choice integer only");
                technicianMenu();
                break;
        }
    }

    private void logoutUser() {
        System.out.println("Logging out!!!");
        initialScreen();
        currentUser = null; //clear current user var
    }

    // hardcoded as per assignment spec
    // creates technicians at beginning of application
    public void createTechnicians(){
        Technician a = new Technician("Harry Styles", "harrystyles@gmail.com", "04123456789", "password123", 1);
        Technician b = new Technician ("Niall Horan", "niallhoran@gmail.com", "04123456789", "password123", 1);
        Technician c = new Technician("Louis Tomlinson", "louistomlinson@gmail.com", "04123456789", "password123", 2);
        Technician d = new Technician("Zayn Malik", "zaynmalik@gmail.com", "04123456789", "password123", 2);

        // adds technicians to arraylist
        users.add(a);
        users.add(b);
        users.add(c);
        users.add(d);

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
