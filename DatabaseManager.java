import java.sql.*;

public class DatabaseManager{
    private Connection conn;
    private Statement stmt;
    private ResultSet rset;

    public DatabaseManager (String username, String password){
        try{
            Class.forName ("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            System.out.println ("Failed to load JDBC/ODBC driver.");
            return;
        }
    }

}