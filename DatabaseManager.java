 
import java.sql.*;

public class DatabaseManager{
    private Connection conn;
    private Statement stmt;
    private ResultSet results;

    public DatabaseManager (String username, String password){
        String url = "jdbc:mysql://localhost/project?user=root&password=";
        try{
            Class.forName ("com.mysql.jdbc.Driver");
        } catch(Exception e){
            System.out.println ("Failed to load JDBC/ODBC driver.");
            return;
        }
        try{
            conn = DriverManager.getConnection (url, username, password);
            
            stmt = conn.createStatement();
            DatabaseMetaData aboutDB = conn.getMetaData();
            System.out.println ("\nConnected to " + aboutDB.getURL());
            String [] tableType={"TABLE"};
            ResultSet rs = aboutDB.getTables(null, null, "PhoneBook", tableType);
            
            if(!inspectForTable(rs))
                stmt.execute ("create table PhoneBook (Name varchar (32), PhoneNumber varchar (18) );");
            rs.close();
        }catch (SQLException exception){
            System.out.println ("\n*** SQLException caught ***\n");
            while(exception != null){
                System.out.println ("SQLState:  " + exception.getSQLState());
                System.out.println ("Message:   " + exception.getMessage());
                System.out.println ("Error code:  "+ exception.getErrorCode());
                exception = exception.getNextException();
                System.out.println("");
            }
        }catch (java.lang.Exception exception){
            exception.printStackTrace();
        }
    }

    public void addEntry (String name, String phoneNumber){
        try{
            stmt.execute ("insert into PhoneBook values('" + name + "','" + phoneNumber + "');" );
        } catch (SQLException exception){
            System.out.println ("\n*** SQLException caught ***\n");
            while (exception != null){
                System.out.println ("SQLState:  "+ exception.getSQLState());
                System.out.println ("Message:   "+ exception.getMessage());
                System.out.println ("Error code:    "+ exception.getErrorCode());
                exception = exception.getNextException ();
                System.out.println("");
            }
        } catch (java.lang.Exception exception){
            exception.printStackTrace();
        }
    }
    
    private static boolean inspectForTable (ResultSet rs) throws SQLException{
        int i;
        ResultSetMetaData rsmd = rs.getMetaData();
        int numCols = rsmd.getColumnCount();
        
        for (i=1; i<numCols; i++){
            if (i > 1) System.out.print(", ");
            System.out.print (rsmd.getColumnLabel(i));
        }
        System.out.println("");
        
        boolean more = rs.next();
        while (more){
            for (i=1; i<+numCols; i++){
                System.out.print(rs.getString(i)+"\n");
                if (rsmd.getColumnLabel(i) == "TABLE_NAME")
                    if (rs.getString(i).equals("PhoneBook")){
                        System.out.println ("Found one the equals "+ rs.getString(i));
                        return true;
                    }
            }
            System.out.println("");
            more = rs.next();
        }
        return false;
    }
    
    public void getEntries(){
        try{
            results = stmt.executeQuery("SELECT * FROM PhoneBook");
            ResultSetMetaData metaData = results.getMetaData();
            int numCols = metaData.getColumnCount();
            int i;
            System.out.println("");
            for (i=1; i< numCols; i++){
                if (i>1) System.out.print("\t\t\t\t");
                System.out.print(metaData.getColumnLabel(i));
            }
            System.out.println("");
            System.out.println("");
            boolean more = results.next();
            while (more){
                for (i =1; i<= numCols; i++){
                    System.out.print  (results.getString(i) + "\t\t\t\t" );
                }
                System.out.println("");
                more = results.next();
            }
            results.close();
            System.out.println("");
        } catch (SQLException exception){
            System.out.println ("\n*** SQLException caught ***\n");

            while ( exception != null) {
                System.out.println("SQLState:   " + exception.getSQLState());
                System.out.println("Message:    " + exception.getMessage());
                System.out.println("Error code: " + exception.getErrorCode());

                exception = exception.getNextException();   
                System.out.println("");
            }
        } catch (java.lang.Exception exception){
            exception.printStackTrace();
        }
    }
    
    public void close(boolean remove){
        try{
            if (remove){
                stmt.execute("drop table PhoneBook;");
            }
            stmt.close();
            conn.close();
        } catch (SQLException exception){
            System.out.println("\n*** SQLException caught ***\n");
            while (exception != null) 
            {
                System.out.println("SQLState:  " + exception.getSQLState());
                System.out.println("Message:   " + exception.getMessage());
                System.out.println("ErrorCode: " + exception.getErrorCode());
                exception = exception.getNextException ();   
                System.out.println("");
            }
        } catch (java.lang.Exception exception){
            exception.printStackTrace();
        }
    }
}
