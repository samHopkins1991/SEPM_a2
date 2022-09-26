<<<<<<< HEAD
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
=======
//this class will be the main driver, running login and menu work
import java.util.Scanner;

public class Main {
    private final Scanner sc;

    // uses User.getClass() to return what user it is.
    // will have different menu's if user vs technician
    private User u;

    public Main(){
        createTechnicians();
        this.sc = new Scanner(System.in);
        initialScreen();

    }

    // prints initial screen
    public void initialScreen(){

        System.out.println("What would you like to do?");
        System.out.println("1 - Existing user login");
        System.out.println("2 - Create new user");

        String choice = sc.nextLine();

        switch (choice){
            case "1":
                loginScreen();
                break;
            case "2":
                newUserScreen();
                break;
            default:
                System.out.println("Please enter only an integer");
                initialScreen();


        }



    }

    // screen for creating new users
    public void newUserScreen(){
        System.out.println("*****************");
        System.out.println("New User");
        System.out.println("*****************");
        System.out.println();

    }

    // login screen for existing users
    public void loginScreen(){
        String email;
        System.out.println("*****************");
        System.out.println("LOGIN");
        System.out.println("*****************");
        System.out.println();
        System.out.println("Please enter your email");
        email = this.sc.nextLine();

    }
    public void printMenu(User u){

        // if user.getclass == User{
        // print user menu
        // if user.getclass() == Technician {
        // print technician menu
    }

    // hardcoded as per assignment spec
    // creates technicians at beginning of application
    public void createTechnicians(){
        new Technician("Harry Styles", "harrystyles@gmail.com", "04123456789", "password123", 1);
        new Technician ("Niall Horan", "niallhoran@gmail.com", "04123456789", "password123", 1);
        new Technician("Louis Tomlinson", "louistomlinson@gmail.com", "04123456789", "password123", 2);
        new Technician("Zayn Malik", "zaynmalik@gmail.com", "04123456789", "password123", 2);

    }




    public static void main(String[] args) {
        new Main();
>>>>>>> 0f7953f72766973f6d7edfeaf57559fab7e62495
    }
}