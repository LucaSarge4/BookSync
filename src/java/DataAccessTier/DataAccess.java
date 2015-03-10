package DataAccessTier;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

public class DataAccess {
    
    private LinkedList <User> users;
    private LinkedList <Bookmark> bookmarks;
    private LinkedList <Destination> destinations;
    
    public DataAccess(){
        try{
            loadFromDB();
       }catch(Exception e ){
            e.printStackTrace();
        }
    }
    
    private void loadFromDB(){
        try{
            Class.forName("org.sqlite.JDBC"); 
             
            Connection connessione = DriverManager.getConnection("jdbc:sqlite:booksync.db"); 
            Statement stat = connessione.createStatement(); 
 
            ResultSet result = stat.executeQuery("SELECT * FROM users");
            User add;
            while (result.next()) { 
              add= new User();
              add.getID();
              this.students.add(add); 
            } 
            result.close(); 
            connessione.close(); 
        } catch ( Exception e ) {
          e.printStackTrace();
        }     
    }
}
