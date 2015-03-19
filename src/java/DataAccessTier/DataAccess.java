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
            String bookid="";
            ResultSet result = stat.executeQuery("SELECT * FROM preferred WHERE ID = "+id);
            ResultSet result1;
            while (result.next()) { 
                bookid=result.getString("BookID");
                result1=stat.executeQuery("SELECT * FROM bookmarks WHERE ID = "+bookid);
                Bookmark bm = new Bookmark();
                bm.setBookID(result1.getString("BookID"));
                bm.setTitle(result1.getString("title"));
                bm.setUrl(result1.getString("url"));
                bm.setLastEditDate(result1.getString("lasteditdate"));
                bm.setFatherFolder(result1.getString("fatherfolder"));
                bm.setTag(result1.getString("tag"));
                bm.setType(result1.getString("type"));
                bm.setIcon(result1.getString("icon"));
                
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
            String destid="";
            ResultSet result = stat.executeQuery("SELECT * FROM ownership WHERE ID = "+id);
            ResultSet result1;
            while (result.next()) { 
                destid=result.getString("DestinationID");
                result1=stat.executeQuery("SELECT * FROM destination WHERE ID = "+destid);
                Destination des = new Destination();
                des.setDestinationID(result.getString("DestinationID"));
                des.setDevice(result.getString("device"));
                des.setOS(result.getString("os"));
                des.setBrowser(result.getString("browser"));
                des.setDropPath(result.getString("dropboxpath"));

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
                                String fatherpath,String type){
        try{
            Class.forName("org.sqlite.JDBC"); 
             
            Connection connessione = DriverManager.getConnection("jdbc:sqlite:booksync.db"); 
            Statement stato = connessione.createStatement(); 
 
            stato.executeUpdate("INSERT INTO bookmarks (ID,title,url,lasteditdate,fatherpath,type) VALUES "
                    + "('"+title+"', '"+url+"', '"+lasteditdate+"', '"+fatherpath+"', '"+type+"')"); 
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
