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
        this.bookmarks = new LinkedList();
    }
    
    public String getID(String username){
        String id="";
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
            Statement stat1 = connessione.createStatement(); 
            String bookid="";
            ResultSet result = stat.executeQuery("SELECT * FROM preferred WHERE UserID =\""+id+"\"");
            ResultSet result1;
            while (result.next()) { 
                bookid=result.getString("BookID");
                result1=stat1.executeQuery("SELECT * FROM bookmarks WHERE BookID = \""+bookid+"\"");
                Bookmark bm = new Bookmark();
                bm.setBookID(result1.getString("BookID"));
                bm.setTitle(result1.getString("title"));
                bm.setUrl(result1.getString("url"));
                bm.setLastEditDate(result1.getString("lasteditdate"));
                bm.setFatherFolder(result1.getString("fatherpath"));
                bm.setTag(result1.getString("tag"));
                bm.setType(result1.getString("type"));
                bm.setIcon(result1.getString("icon"));
                this.bookmarks.add(bm);
            } 
            result.close();
            connessione.close(); 
        } catch ( Exception e ) {
          e.printStackTrace();
        }
    }
    
    private void loadUserDestinations(String id){
        try{
            Class.forName("org.sqlite.JDBC"); 

            Connection connessione = DriverManager.getConnection("jdbc:sqlite:booksync.db"); 
            Statement stat = connessione.createStatement();
            Statement stat1 = connessione.createStatement(); 
            String destid="";
            ResultSet result = stat.executeQuery("SELECT * FROM ownership WHERE UserID =\""+id+"\"");
            ResultSet result1;
            while (result.next()) { 
                destid=result.getString("DestinationID");
                result1=stat1.executeQuery("SELECT * FROM destination WHERE DestinationID = \""+destid+"\"");
                Destination des = new Destination();
                des.setDestinationID(result1.getString("DestinationID"));
                des.setDevice(result1.getString("device"));
                des.setOS(result1.getString("os"));
                des.setBrowser(result1.getString("browser"));
                des.setDropPath(result1.getString("dropboxpath"));
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
    
    public void addUser(String username,String firstname,String lastname,
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
    
    public void addBookmark(String userid,String title,String url,String lasteditdate,
                                String fatherpath,String type){
        try{
            Class.forName("org.sqlite.JDBC"); 
             
            Connection connessione = DriverManager.getConnection("jdbc:sqlite:booksync.db"); 
            Statement stato = connessione.createStatement(); 
 
            stato.executeUpdate("INSERT INTO bookmarks (title,url,lasteditdate,fatherpath,type) VALUES "
                    + "('"+title+"', '"+url+"', '"+lasteditdate+"', '"+fatherpath+"', '"+type+"')"); 
            connessione.close();
            preferred(userid);
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
          e.printStackTrace();
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
    
    public void addDestination(String id,String device,String os,String browser){
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
    
    public void localized(String bookid,String destid){
        try{
            Class.forName("org.sqlite.JDBC"); 
             
            Connection connessione = DriverManager.getConnection("jdbc:sqlite:booksync.db"); 
            Statement stat = connessione.createStatement(); 
            stat.executeUpdate("INSERT INTO localized VALUES "+ "('"+bookid+"', '"+destid+"')");
            connessione.close();
        } catch ( Exception e ) {
          e.printStackTrace();
        } 
    }
    
    public void deleteBookmark(String bookID){
        try{
            Class.forName("org.sqlite.JDBC"); 
             
            Connection connessione = DriverManager.getConnection("jdbc:sqlite:booksync.db");
            Statement stat = connessione.createStatement(); 
            stat.executeUpdate("DELETE FROM bookmarks WHERE BookID =\""+bookID+"\"");
            stat.close();
            connessione.close();
            deletePreferred(bookID);
            deleteLocalized(bookID);
        } catch ( Exception e ) {
          e.printStackTrace();
        }
    }
    
    private void deletePreferred(String bookID){
        try{
            Class.forName("org.sqlite.JDBC"); 
             
            Connection connessione = DriverManager.getConnection("jdbc:sqlite:booksync.db");
            Statement stat = connessione.createStatement(); 
            stat.executeUpdate("DELETE FROM preferred WHERE BookID =\""+bookID+"\"");
            stat.close();
            connessione.close();
        } catch ( Exception e ) {
          e.printStackTrace();
        }
    }
    
    private void deleteLocalized(String bookID){
        try{
            Class.forName("org.sqlite.JDBC"); 
             
            Connection connessione = DriverManager.getConnection("jdbc:sqlite:booksync.db");
            Statement stat = connessione.createStatement(); 
            stat.executeUpdate("DELETE FROM localized WHERE BookID =\""+bookID+"\"");
            stat.close();
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
