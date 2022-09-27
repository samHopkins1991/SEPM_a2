//this class will be the main driver, running login and menu work
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    //Test current user logged.
    private User currentUser;
    private final Scanner sc;

    // Users can be of type User or Type Technician, this Array list Stores Both Polymorphically
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
        System.out.println("1 - Existing user login");
        System.out.println("2 - Create new user");
        System.out.println("5 - Exit the program");

        String choice = sc.nextLine();

        switch (choice){
            case "1":
                loginScreen();
                break;
            case "2":
                newUserScreen();
                break;
            case "5":
                System.out.println("Shutting down!!!!");
                break;
            default:
                System.out.println("Please enter a valid choice integer only");
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

        while (passwordMatch == false){
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
        // boolean keeps track of if it is user vs tech signing in
        boolean loginSuccess = false;

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
                loginSuccess = true;
                //Test current user logged.
                currentUser = u;
                printMenu(u instanceof Technician); // determines if the user is a technician dynamically
                System.out.println("login success");
            }
        }

        if(!loginSuccess){
            System.out.println("Email and password do not match");
            initialScreen();
        }
    }

    // prints menu depending on boolean
    // if true print technician menu
    // if false print user menu
    public void printMenu(boolean isTechnician){
        // options for users:
        // submit ticket
        // view my tickets

        // options for technicians:
        // view assigned tickets
        //      -> change status
        //      -> change severity
        // view all closed and archived tickets

        // these arrays create the menu options for the user
        String userOptions[] = {"Submit Ticket", "View My Tickets", "Log out"};
        String techOptions[] = {"View Assigned Tickets", "View Closed and Archived Tickets", "Log out"};

        System.out.println("*****************");
        //Prints the current user logged.
        System.out.println("Welcome " + currentUser.getName());
        if (isTechnician == false){
            System.out.println("User Menu");
            for (int i =0; i < userOptions.length; i++){
                System.out.printf("%d. %s%n",i +1, userOptions[i]);
            }
        } else {
            System.out.println("Technician Menu");
            for (int i =0; i < techOptions.length; i++){
                System.out.printf("%d. %s%n",i+ 1, techOptions[i]);
            }
        }
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