package DataAccessTier;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

public class DataAccess implements DataAccessInterface{
    
    private LinkedList <User> users;
    private LinkedList <Bookmark> bookmarks;
    private LinkedList <Destination> destinations;
    
    public DataAccess(){
        this.bookmarks = new LinkedList<Bookmark>();
        this.destinations = new LinkedList<Destination>();
    }
    
    public String getID(String username){
        String id="This user is not valid";
        try{
            Class.forName("org.sqlite.JDBC");
            Connection connessione = DriverManager.getConnection("jdbc:sqlite:booksync.db"); 
            Statement stat = connessione.createStatement();
            ResultSet result = stat.executeQuery("SELECT * FROM users WHERE username = \""+username+"\"");
            id=result.getString("ID");
            result.close();
            connessione.close(); 
        } catch ( Exception e ) {
          e.printStackTrace();
        }
        return id;
    }
    
    private void loadUserBookmarks(String id){
        try{
            Class.forName("org.sqlite.JDBC"); 
             
            Connection connessione = DriverManager.getConnection("jdbc:sqlite:booksync.db"); 
            Statement stat = connessione.createStatement(); 
 
            ResultSet result = stat.executeQuery("SELECT * FROM bookmarks WHERE ID = "+id);
            Bookmark bm = new Bookmark();
            while (result.next()) { 
                bm.setID(id);
                bm.setTitle(result.getString("title"));
                bm.setUrl(result.getString("url"));
                bm.setLastEditDate(result.getString("lasteditdate"));
                bm.setFatherFolder(result.getString("fatherfolder"));
                bm.setTag(result.getString("tag"));
                bm.setDestination(result.getString("destination"));
                bm.setType(result.getString("type"));
                bm.setIcon(result.getString("icon"));
                
              this.bookmarks.add(bm); 
            } 
            result.close();connessione.close(); 
        } catch ( Exception e ) {
          e.printStackTrace();
        }
    }
    
    private void loadUserDestinations(String id){
        try{
            Class.forName("org.sqlite.JDBC"); 

            Connection connessione = DriverManager.getConnection("jdbc:sqlite:booksync.db"); 
            Statement stat = connessione.createStatement();
            ResultSet result = stat.executeQuery("SELECT * FROM destinations WHERE ID = "+id);
            Destination des = new Destination();
            while (result.next()) { 
                des.setID(id);
                des.setDevice(result.getString("device"));
                des.setOS(result.getString("os"));
                des.setBrowser(result.getString("browser"));

              this.destinations.add(des); 
            } 
            result.close(); 
            connessione.close(); 
        } catch ( Exception e ) {
          e.printStackTrace();
        }
    }
    
    public LinkedList getBookmarks(String id){
        loadUserBookmarks(id);
        return this.bookmarks;
    }
    
    public LinkedList getDestinations(String id){
        loadUserDestinations(id);
        return this.destinations;
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
                    + "('"+title+"', '"+url+"', '"+lasteditdate+"', '"+fatherpath+"', '"+destination+"', '"+type+"')"); 
            connessione.close();
            preferred(id);
        } catch ( Exception e ) {
          e.printStackTrace();
        } 
    }
    
    private void preferred(String id){
        String bookid="";
        try{
            Class.forName("org.sqlite.JDBC"); 
             
            Connection connessione = DriverManager.getConnection("jdbc:sqlite:booksync.db"); 
            Statement stat = connessione.createStatement(); 
            ResultSet result = stat.executeQuery( "SELECT * FROM bookmarks;" );
            while ( result.next() ) {
                   bookid=result.getString("BookID");
            }
            result.close();
            stat.close();
            connessione.close();
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
        try{
            Class.forName("org.sqlite.JDBC"); 
             
            Connection connessione = DriverManager.getConnection("jdbc:sqlite:booksync.db"); 
            Statement stat = connessione.createStatement(); 
            stat.executeUpdate("INSERT INTO preferred VALUES "+ "('"+id+"', '"+bookid+"')");
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
 
            stato.executeUpdate("INSERT INTO destination (device,os,browser) VALUES "
                    + "('"+device+"', '"+os+"', '"+browser+"')"); 
             
            connessione.close();
            ownership(id);
        } catch ( Exception e ) {
          e.printStackTrace();
        } 
    } 
    
    private void ownership(String id){
        String destid="";
        try{
            Class.forName("org.sqlite.JDBC"); 
             
            Connection connessione = DriverManager.getConnection("jdbc:sqlite:booksync.db"); 
            Statement stat = connessione.createStatement(); 
            ResultSet result = stat.executeQuery( "SELECT * FROM destination;" );
            while ( result.next() ) {
                   destid=result.getString("DestinationID");
            }
            result.close();
            stat.close();
            connessione.close();
        } catch ( Exception e ) {
          e.printStackTrace();
        }
        try{
            Class.forName("org.sqlite.JDBC"); 
             
            Connection connessione = DriverManager.getConnection("jdbc:sqlite:booksync.db"); 
            Statement stat = connessione.createStatement(); 
            stat.executeUpdate("INSERT INTO ownership VALUES "+ "('"+id+"', '"+destid+"')");
            connessione.close();
        } catch ( Exception e ) {
          e.printStackTrace();
        } 
    }
    
    public boolean login(String id,String password){
        boolean response = false;
        try{
            Class.forName("org.sqlite.JDBC"); 

            Connection connessione = DriverManager.getConnection("jdbc:sqlite:booksync.db"); 
            Statement stat = connessione.createStatement();
            ResultSet result = stat.executeQuery("SELECT * FROM users WHERE ID = "+id);
            if(result.getString("password").equals(password))
                response=true;
            result.close(); 
            connessione.close(); 
        } catch ( Exception e ) {
          e.printStackTrace();
        }
        return response;
    }
}
