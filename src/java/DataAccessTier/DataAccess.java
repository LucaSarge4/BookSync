package DataAccessTier;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

public class DataAccess implements DataAccessInterface{
    
    private LinkedList <User> users;
    private LinkedList <Bookmark> bookmarks;
    private LinkedList<Bookmark> destBookmarks;
    private LinkedList<Bookmark> unDestBookmarks;
    private LinkedList <Destination> destinations;
    
    public DataAccess(){
        this.users = new LinkedList();
        this.bookmarks = new LinkedList();
        this.destinations = new LinkedList();
        this.destBookmarks = new LinkedList();
        this.unDestBookmarks = new LinkedList();
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
    
    private void loadUsers(){
        try{
            Class.forName("org.sqlite.JDBC"); 
             
            Connection connessione = DriverManager.getConnection("jdbc:sqlite:booksync.db"); 
            Statement stat = connessione.createStatement();
            ResultSet result = stat.executeQuery("SELECT * FROM users");
            while (result.next()) { 
                User u = new User();
                u.setID(result.getString("ID"));
                u.setUserName(result.getString("username"));
                u.setFirstName(result.getString("firstname"));
                u.setLastName(result.getString("lastname"));
                u.setPassword("noPsw");
                u.setEmail(result.getString("email"));
                u.setCountry(result.getString("country"));
                u.setDate(result.getString("regdate"));
                this.users.add(u);
            } 
            result.close();
            connessione.close(); 
        } catch ( Exception e ) {
          e.printStackTrace();
        }
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
                bm.setFatherFolder(result1.getString("fatherfolder"));
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
    
    private void loadDestinationBookmarks(String destinationID){
        try{
            Class.forName("org.sqlite.JDBC"); 

            Connection connessione = DriverManager.getConnection("jdbc:sqlite:booksync.db"); 
            Statement stat = connessione.createStatement();
            Statement stat1 = connessione.createStatement(); 
            String bookid="";
            ResultSet result = stat.executeQuery("SELECT * FROM localized WHERE DestinationID =\""+destinationID+"\"");
            ResultSet result1;
            while (result.next()) { 
                bookid=result.getString("BookID");
                result1=stat1.executeQuery("SELECT * FROM bookmarks WHERE BookID = \""+bookid+"\"");
                Bookmark bm = new Bookmark();
                bm.setBookID(result1.getString("BookID"));
                bm.setTitle(result1.getString("title"));
                bm.setUrl(result1.getString("url"));
                bm.setLastEditDate(result1.getString("lasteditdate"));
                bm.setFatherFolder(result1.getString("fatherfolder"));
                bm.setTag(result1.getString("tag"));
                bm.setType(result1.getString("type"));
                bm.setIcon(result1.getString("icon"));
                this.destBookmarks.add(bm);
            } 
            result.close(); 
            connessione.close(); 
        } catch ( Exception e ) {
          e.printStackTrace();
        }
    }
    
    public LinkedList getUsers(){
        loadUsers();
        return this.users;
    }
    
    public LinkedList getBookmarks(String id){
        loadUserBookmarks(id);
        return this.bookmarks;
    }
    
    public LinkedList getDestinations(String id){
        loadUserDestinations(id);
        return this.destinations;
    }
    
    public LinkedList getDestinationBookmarks(String destID){
        loadDestinationBookmarks(destID);
        return this.destBookmarks;
    }
    
    private boolean checkBookmarks(Bookmark bm){
        for(int i=0;i<this.destBookmarks.size();i++){
            if(this.destBookmarks.get(i).getBookID().equals(bm.getBookID()))
                return false;
        }
        return true;
    }
    
    public LinkedList getUnselectedDestinationBookmarks(String userID,String destID){
        loadUserBookmarks(userID);
        loadDestinationBookmarks(destID);
        for(int i=0;i<this.bookmarks.size();i++){
            if(checkBookmarks(this.bookmarks.get(i)))
                this.unDestBookmarks.add(this.bookmarks.get(i));
        }
        return this.unDestBookmarks;
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
                                String fatherfolder,String type,String description,String tag){
        try{
            Class.forName("org.sqlite.JDBC"); 
             
            Connection connessione = DriverManager.getConnection("jdbc:sqlite:booksync.db"); 
            Statement stato = connessione.createStatement(); 
 
            stato.executeUpdate("INSERT INTO bookmarks (title,url,lasteditdate,fatherfolder,type,description,tag) VALUES "
                    + "('"+title+"', '"+url+"', '"+lasteditdate+"', '"+fatherfolder+"', '"+type+"','"+description+"','"+tag+"')"); 
            
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
    
    public void editBookmarkTitle(String bookid,String title){
        try{
            Class.forName("org.sqlite.JDBC"); 
             
            Connection connessione = DriverManager.getConnection("jdbc:sqlite:booksync.db"); 
            Statement stato = connessione.createStatement(); 
 
            stato.executeUpdate("UPDATE bookmarks set title =\""+title+"\" WHERE BookID = \""+bookid+"\""); 
            connessione.close();
        } catch ( Exception e ) {
          e.printStackTrace();
        } 
    }
    
    public void editBookmarkUrl(String bookid,String url){
        try{
            Class.forName("org.sqlite.JDBC"); 
             
            Connection connessione = DriverManager.getConnection("jdbc:sqlite:booksync.db"); 
            Statement stato = connessione.createStatement(); 
 
            stato.executeUpdate("UPDATE bookmarks set url =\""+url+"\" WHERE BookID = \""+bookid+"\""); 
            connessione.close();
        } catch ( Exception e ) {
          e.printStackTrace();
        } 
    }
    
    public void editBookmarkLastEditDate(String bookid,String date){
        try{
            Class.forName("org.sqlite.JDBC"); 
             
            Connection connessione = DriverManager.getConnection("jdbc:sqlite:booksync.db"); 
            Statement stato = connessione.createStatement(); 
 
            stato.executeUpdate("UPDATE bookmarks set lasteditdate =\""+date+"\" WHERE BookID = \""+bookid+"\""); 
            connessione.close();
        } catch ( Exception e ) {
          e.printStackTrace();
        } 
    }
    
    public void editBookmarkFatherFolder(String bookid,String fatherFolder){
        try{
            Class.forName("org.sqlite.JDBC"); 
             
            Connection connessione = DriverManager.getConnection("jdbc:sqlite:booksync.db"); 
            Statement stato = connessione.createStatement(); 
 
            stato.executeUpdate("UPDATE bookmarks set fatherfolder =\""+fatherFolder+"\" WHERE BookID = \""+bookid+"\""); 
            connessione.close();
        } catch ( Exception e ) {
          e.printStackTrace();
        } 
    }
    
    public void editBookmarkTag(String bookid,String tag){
        try{
            Class.forName("org.sqlite.JDBC"); 
             
            Connection connessione = DriverManager.getConnection("jdbc:sqlite:booksync.db"); 
            Statement stato = connessione.createStatement(); 
 
            stato.executeUpdate("UPDATE bookmarks set tag =\""+tag+"\" WHERE BookID = \""+bookid+"\""); 
            connessione.close();
        } catch ( Exception e ) {
          e.printStackTrace();
        } 
    }
    
    public void editBookmarkDescription(String bookid,String desc){
        try{
            Class.forName("org.sqlite.JDBC"); 
             
            Connection connessione = DriverManager.getConnection("jdbc:sqlite:booksync.db"); 
            Statement stato = connessione.createStatement(); 
 
            stato.executeUpdate("UPDATE bookmarks set description =\""+desc+"\" WHERE BookID = \""+bookid+"\""); 
            connessione.close();
        } catch ( Exception e ) {
          e.printStackTrace();
        } 
    }
    
    public void editBookmarkIcon(String bookid,String icon){
        try{
            Class.forName("org.sqlite.JDBC"); 
             
            Connection connessione = DriverManager.getConnection("jdbc:sqlite:booksync.db"); 
            Statement stato = connessione.createStatement(); 
 
            stato.executeUpdate("UPDATE bookmarks set icon =\""+icon+"\" WHERE BookID = \""+bookid+"\""); 
            connessione.close();
        } catch ( Exception e ) {
          e.printStackTrace();
        } 
    }
    
    public void addDestination(String userid,String device,String os,String browser){
        try{
            Class.forName("org.sqlite.JDBC"); 
             
            Connection connessione = DriverManager.getConnection("jdbc:sqlite:booksync.db"); 
            Statement stato = connessione.createStatement(); 
 
            stato.executeUpdate("INSERT INTO destination (device,os,browser) VALUES "
                    + "('"+device+"', '"+os+"', '"+browser+"')"); 
             
            connessione.close();
            ownership(userid);
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
    
    public void deleteLocalized(String bookID,String destID){
        try{
            Class.forName("org.sqlite.JDBC"); 
             
            Connection connessione = DriverManager.getConnection("jdbc:sqlite:booksync.db");
            Statement stat = connessione.createStatement(); 
            stat.executeUpdate("DELETE FROM localized WHERE BookID =\""+bookID+"\" AND DestinationID=\""+destID+"\"");
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
    
    public void editDestinationName(String destID,String name){
        try{
            Class.forName("org.sqlite.JDBC"); 
             
            Connection connessione = DriverManager.getConnection("jdbc:sqlite:booksync.db"); 
            Statement stato = connessione.createStatement(); 
 
            stato.executeUpdate("UPDATE destination set device =\""+name+"\" WHERE DestinationID = \""+destID+"\""); 
            connessione.close();
        } catch ( Exception e ) {
          e.printStackTrace();
        } 
    }
    public void editDestinationOS(String destID,String os){
        try{
            Class.forName("org.sqlite.JDBC"); 
             
            Connection connessione = DriverManager.getConnection("jdbc:sqlite:booksync.db"); 
            Statement stato = connessione.createStatement(); 
 
            stato.executeUpdate("UPDATE destination set os =\""+os+"\" WHERE DestinationID = \""+destID+"\""); 
            connessione.close();
        } catch ( Exception e ) {
          e.printStackTrace();
        } 
    }
    public void editDestinationBrowser(String destID,String browser){
        try{
            Class.forName("org.sqlite.JDBC"); 
             
            Connection connessione = DriverManager.getConnection("jdbc:sqlite:booksync.db"); 
            Statement stato = connessione.createStatement(); 
 
            stato.executeUpdate("UPDATE destination set browser =\""+browser+"\" WHERE DestinationID = \""+destID+"\""); 
            connessione.close();
        } catch ( Exception e ) {
          e.printStackTrace();
        } 
    }
    public void editDestinationDropbox(String destID,String drop){
        try{
            Class.forName("org.sqlite.JDBC"); 
             
            Connection connessione = DriverManager.getConnection("jdbc:sqlite:booksync.db"); 
            Statement stato = connessione.createStatement(); 
 
            stato.executeUpdate("UPDATE destination set dropboxpath =\""+drop+"\" WHERE DestinationID = \""+destID+"\""); 
            connessione.close();
        } catch ( Exception e ) {
          e.printStackTrace();
        } 
    }
    
}
