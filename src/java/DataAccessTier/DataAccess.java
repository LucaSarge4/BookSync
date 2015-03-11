package DataAccessTier;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.LinkedList;

public class DataAccess {
    
    private LinkedList <User> users;
    private LinkedList <Bookmark> bookmarks;
    private LinkedList <Destination> destinations;
    
    public DataAccess(){
    }
    
    public void loadUserData(String id){
        
    }
    
    public void aggiungiUser(String username,String firstname,String lastname,
                                    String password,String email,String country,String regdate){
        try{
            Class.forName("org.sqlite.JDBC"); 
             
            Connection connessione = DriverManager.getConnection("jdbc:sqlite:booksync.db"); 
            Statement stato = connessione.createStatement(); 
 
            stato.executeUpdate("INSERT INTO users (username,firstname,lastname,password,email,country,regdate) VALUES "
                    + "('"+username+"', '"+firstname+"', '"+lastname+"', '"+password+"', '"+email+"', '"+country+"', '"+regdate+"')"); 
             
            connessione.close();
        } catch ( Exception e ) {
          e.printStackTrace();
        }
    }
    
    public void aggiungiBookmark(String id,String title,String url,String lasteditdate,
                                String fatherpath,String destination,String type){
        try{
            Class.forName("org.sqlite.JDBC"); 
             
            Connection connessione = DriverManager.getConnection("jdbc:sqlite:booksync.db"); 
            Statement stato = connessione.createStatement(); 
 
            stato.executeUpdate("INSERT INTO bookmarks (ID,title,url,lasteditdate,fatherpath,destination,type) VALUES "
                    + "('"+id+"', '"+title+"', '"+url+"', '"+lasteditdate+"', '"+fatherpath+"', '"+destination+"', '"+type+"')"); 
             
            connessione.close();
        } catch ( Exception e ) {
          e.printStackTrace();
        } 
    }
    
    public void aggiungiDes(String id,String device,String os,String browser){
        try{
            Class.forName("org.sqlite.JDBC"); 
             
            Connection connessione = DriverManager.getConnection("jdbc:sqlite:booksync.db"); 
            Statement stato = connessione.createStatement(); 
 
            stato.executeUpdate("INSERT INTO destination (ID,device,os,browser) VALUES "
                    + "('"+id+"', '"+device+"', '"+os+"', '"+browser+"')"); 
             
            connessione.close();
        } catch ( Exception e ) {
          e.printStackTrace();
        } 
    }  
}
