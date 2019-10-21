import java.util.*;

public class UserInterface {

    private DatabaseManager database;                           // the reference to the DatabaseManager object, 
                                                                // handles all requests to access the database
    public UserInterface(DatabaseManager theDatabaseManager) {
	    database = theDatabaseManager;
    }

    public void start() {
        Scanner in = new Scanner (System.in);
        while (true) {                                      // Continue until the user enters a quit command
            System.out.println ("Click in the Console,"  
             + "\n then Enter a command: (choose)" 
             + "\n A (then Enter) to Add a phone book entry, " 
             + "\n D (then Enter) to Display all phone book entries," 
             + "\n K (then Enter) to exit and Keep the entries," 
             + "\n or Q (then Enter) to Quit and remove the entries: " );

            String command = in.nextLine();

            if ( command.charAt(0) == 'A' )
            {
                System.out.println ("Enter name: ");
                String name = in.nextLine();
                System.out.println ("Enter phone number: ");
                String phoneNumber = in.nextLine();
                database.addEntry (name, phoneNumber);  // Add this entry to the database.
            }
            else if (command.charAt(0) == 'D')
            {
                database.getEntries();  // Query the database for the resultSet  
            }
            else if (command.charAt(0) == 'K' )
            {
                System.out.println("Bye");
                database.close(false);               // The user entered the quit command, but does not want to delete info.
                return;
            }
            else if ( command.charAt(0) != 'Q' )
            {
                System.out.println ("Invalid command, please enter either A, D, K, or Q.");
            } 
            else                                     // command is Q
            {
                System.out.println("Bye");
                database.close(true);                // The user entered the quit command, so shutdown the database and return.
                return;
            }
      }
   }
}