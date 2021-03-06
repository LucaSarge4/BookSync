package BusinessLogicTier;

import DataAccessTier.DataAccessInterface;
import DataAccessTier.DataAccess;
import DataAccessTier.Bookmark;
import DataAccessTier.Destination;
import DataAccessTier.User;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import static java.net.URLDecoder.decode;
import java.util.logging.Level;
import java.util.logging.Logger;


public class BusinessLogic implements BusinessLogicInterface{
    
    DataAccessInterface dt;
    
    public BusinessLogic(){
        this.dt= new DataAccess();
    }
    
    public LinkedList getUsers(){
        return this.dt.getUsers();
    }
    
    public boolean checkUsername(String username){
        LinkedList<User> user = getUsers();
        for(int i=0;i<user.size();i++){
            if(user.get(i).getUserName().equals(username))
                return false;//username  già in uso errore in registrazione 
        }
        return true;// username libero
    }
    
    public boolean checkEmail(String email){
        LinkedList<User> user = getUsers();
        for(int i=0;i<user.size();i++){
            if(user.get(i).getEmail().equals(email))
                return false;//email  già in uso errore in registrazione 
        }
        return true;// email libero
    }
    
    public LinkedList getBookmarks(String username){
        return this.dt.getBookmarks(this.dt.getID(username));
    }
    
    public LinkedList getDestinations(String username){
        return this.dt.getDestinations(this.dt.getID(username));
    }
    
    public LinkedList getDestinationBookmarks(String username,String deviceName){
        return this.dt.getDestinationBookmarks(this.getDestinationID(username,deviceName));
    }
    
    public LinkedList getUnselectedDestinationBookmarks(String username,String deviceName){
        return this.dt.getUnselectedDestinationBookmarks(this.dt.getID(username),getDestinationID(username,deviceName));
    }
    
    public boolean login(String username,String password){
        return this.dt.login(this.dt.getID(username),password);
    }
    
    public void addUser(String username,String firstname,String lastname,String password,String email,String country,String regdate){
        this.dt.addUser(username,firstname,lastname,password,email,country,regdate);
    }
    
    public void addBookmark(String username,String title,String url,String lasteditdate,String fatherfolder,String type,String description,String tag){
        if(this.checkBookmarks(username,title,url))
            this.dt.addBookmark(this.dt.getID(username),title,url,lasteditdate,fatherfolder,type.toLowerCase(),description,tag);
    }
    
    private boolean checkBookmarks(String username,String title,String url){
        LinkedList<Bookmark> bm = this.getBookmarks(username);
        for(int i=0;i<bm.size();i++){
            if(bm.get(i).getTitle().equals(title) && bm.get(i).getUrl().equals(url))
                return false;
        }
        return true;
    }
    
    public void editBookmarkTitle(String bookid,String title){
        this.dt.editBookmarkTitle(bookid, title);
    }
    
    public void editBookmarkUrl(String bookid,String url){
        this.dt.editBookmarkUrl(bookid, url);
    }
    
    public void editBookmarkLastEditDate(String bookid,String date){
        this.dt.editBookmarkLastEditDate(bookid, date);
    }
    
    public void editBookmarkFatherFolder(String bookid,String fatherFolder){
        this.dt.editBookmarkFatherFolder(bookid, fatherFolder);
    }
    
    public void editBookmarkTag(String bookid,String tag){
        this.dt.editBookmarkTag(bookid, tag);
    }
    
    public void editBookmarkDescription(String bookid,String desc){
        this.dt.editBookmarkDescription(bookid, desc);
    }
    
    public void editBookmarkIcon(String bookid,String icon){
        this.dt.editBookmarkIcon(bookid, icon);
    }
    
    public String getBookID(String username,String url){
        int index=-1;
        try {
            url = decode(url, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(BusinessLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        LinkedList<Bookmark> list = getBookmarks(username);
        for(int i=0;i<list.size();i++){
            if(list.get(i).getUrl().equals(url))
                index=i;
        }
        String bookID= list.get(index).getBookID();
        return bookID;
    }
    
    public String getDestinationID(String username,String deviceName){
        int index=-1;
        try {
            deviceName = decode(deviceName, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(BusinessLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        LinkedList<Destination> list = getDestinations(username);
        for(int i=0;i<list.size();i++){
            if(list.get(i).getDevice().trim().equals(deviceName.trim()))
                index=i;
        }
        String destID= list.get(index).getDestinationID();
        return destID;
    }
    
    public Bookmark getBookmark(String username,String bookID){
        int index=-1;
        LinkedList<Bookmark> list = getBookmarks(username);
        for(int i=0;i<list.size();i++){
            if(list.get(i).getBookID().equals(bookID))
                index=i;
        }
        return list.get(index);
    }
    
    public Destination getDestination(String username,String destID){
        int index=-1;
        LinkedList<Destination> list = getDestinations(username);
        for(int i=0;i<list.size();i++){
            if(list.get(i).getDestinationID().equals(destID))
                index=i;
        }
        return list.get(index);
    }
    
    public void deleteBookmark(String username,String url){
        this.dt.deleteBookmark(getBookID(username,url));
    }
    
    public void addDestination(String username,String device,String os,String browser,String dropboxPath){
        this.dt.addDestination(this.dt.getID(username),device,os,browser);
    }
    
    public void addDestination(String username,String device,String os,String browser,String dropboxPath,String sync){
        this.dt.addDestination(this.dt.getID(username),device,os,browser);
        if(sync.equals("Automatic")){
            autoSync(username,device,dropboxPath);
        }
    }
    
    private void autoSync(String username,String deviceName,String dropboxPath){
        LinkedList<Bookmark> list = getBookmarks(username);
        if(dropboxPath.equals(null)){
            for(int i=0;i<list.size();i++)
                if(list.get(i).getType().equals("web"))
                    this.dt.localized(list.get(i).getBookID(),this.getDestinationID(username,deviceName));
        }else{
            for(int i=0;i<list.size();i++)
                this.dt.localized(list.get(i).getBookID(),this.getDestinationID(username,deviceName));
        }
    }
    
    public void addLocalized(String username,String bookTitle,String deviceName){
        Bookmark bm = this.getBookmark(username,this.getBookIDByName(username,bookTitle));
        String folder = bm.getFatherFolder();
        String folderID="";
        if(!folder.equals("Booksync"))
           folderID = this.getBookIDByName(username, folder);
        System.out.println("While localizing found FOLDERID: "+folderID);
        this.dt.localized(this.getBookIDByName(username,bookTitle),this.getDestinationID(username,deviceName));
        if(folder.equals("Booksync") || checkDestFolder(username,deviceName,folderID)){
            //do nothing
        }else{
            this.addLocalized(username,folder,deviceName);   
        }
    }
    
    private String getBookIDByName(String username,String bookTitle){
        int index=-1;
        LinkedList<Bookmark> list = this.getBookmarks(username);
        int max = list.size();
        for(int i=0;i<max;i++){
            if(list.get(i).getTitle().equals(bookTitle))
                index=i;
        }
        String bookID= list.get(index).getBookID();
        return bookID;
    }
    
    private boolean checkDestFolder(String username,String deviceName,String folderID){
        LinkedList<Bookmark> list = getDestinationBookmarks(username,deviceName);
        for(int i=0;i<list.size();i++){
            if(list.get(i).getBookID().equals(folderID))
                return true;//significa che la cartella è già presente nel dispositivo
        }
        return false;//devo aggiungere la cartella
    }
    
    public void deleteLocalized(String username,String url,String deviceName){
        this.dt.deleteLocalized(this.getBookID(username,url),this.getDestinationID(username,deviceName));
    }
    
    public void editDestinationName(String destID,String name){
        this.dt.editDestinationName(destID, name);
    }
    
    public void editDestinationOS(String destID,String os){
        this.dt.editDestinationOS(destID, os);
    }
    
    public void editDestinationBrowser(String destID,String browser){
        this.dt.editDestinationBrowser(destID, browser);
    }
    
    public void editDestinationDropbox(String destID,String drop){
        this.dt.editDestinationDropbox(destID, drop);
    }
    
    public String getBookmarksDescriptionList(String username, String device){
        LinkedList<Bookmark> allBm = this.getDestinationBookmarks(username, device);
        return this.parseBookmarkByFatherFolder(allBm, "Booksync");
    }
    
    public String getBookmarksDescriptionList(String username){
        LinkedList<Bookmark> allBm = this.getBookmarks(username);
        return this.parseBookmarkByFatherFolder(allBm, "Booksync");
    }

    private String parseBookmarkByFatherFolder(LinkedList<Bookmark> allBm, String folder) {
        System.out.println("All bm: "+allBm.toString());
        // default case
        if(allBm.isEmpty()){
            return "";
        }
        // recorsive case
        // get first item with fatherFolder equal to "folder" with url not null
        boolean itemIsBm = false;
        int j=-1;
        for(int i=0; i<allBm.size();i++){
            if(allBm.get(i).getFatherFolder().equals(folder)){
                // is a bookmark or a folder in this folder
                if("".equals(allBm.get(i).getUrl()) && !itemIsBm){
                    // is a folder
                    j=i;
                }else{
                    itemIsBm = true;
                    j=i;
                    // exit, found bookmark
                    i=allBm.size();
                }
            }
        }
        if(j==-1){
            // nothing to add in this folder
            return "";
        }
        System.out.println("Checked list, selected: "+allBm.get(j).getTitle()+" on folder: "+allBm.get(j).getFatherFolder()+", and j: "+j);
        // check if current item is a true bookmark or a folder
        if(!"".equals(allBm.get(j).getUrl())){
            // bookmark
            System.out.println("Bookmark case");
            // delete item from list and update String to return
            Bookmark bm = allBm.get(j);
            allBm.remove(j);
            return "<DT><A HREF=\""+bm.getUrl()+"\">"+bm.getTitle()+"</A>\n"+this.parseBookmarkByFatherFolder(allBm, folder);
        }else{
            // folder
            System.out.println("Folder case");
            // get folder item
            Bookmark dir = allBm.get(j);
            // delete from list
            allBm.remove(j);
            // return folder skeleton with dynamic content
            return this.parseBookmarkByFatherFolder(allBm, folder)+
                    "<DT><H3>"+dir.getTitle()+"</H3>\n"+
                    "<DL><p>\n"+
                    this.parseBookmarkByFatherFolder(allBm, dir.getTitle())+
                    "</DL>\n"+
                    "</DT>\n";
        }
    }
    
    public boolean isBookmarkToRemove(String user, String url){
        //System.out.println("----------------------Check if bookmark is to remove----------------------");
        LinkedList<Destination> dest;
        LinkedList<Bookmark> bm;
        //dest = this.dt.getDestinations(this.dt.getID(user));
        dest = this.getDestinations(user);
        int maxDest = dest.size();
        //System.out.println("Length of destinations: "+dest.size());
        for(int i=0;i<maxDest;i++){
            //System.out.println("Checking destination: "+dest.get(i).getDevice());
            bm = this.getDestinationBookmarks(user, dest.get(i).getDevice());
            //System.out.println("Length of bookmarks: "+bm.size());
            for(int j=0;j<bm.size();j++){
                //System.out.println("Checking bookmark: "+bm.get(j).getTitle());
                if(bm.get(j).getUrl().equals(url)){
                    return false;
                }
            }
            //System.out.println("Exit from inner FOR with i: "+i+" and destination length: "+dest.size());
            bm.clear();
        }
        //System.out.println("-------------------------------Exit from double for!-------------------------------");
        return true;
    }
    
    public String getUpperFolder(String username, String folder){
        if(folder.equals("Booksync")){
            return "";
        }
        // get bookmark which title == folder
        try{
            return this.getBookmark(username, this.getBookIDByName(username, folder)).getFatherFolder();
        }catch(Error e){
            // error
            System.out.println(e);
            return "";
        }
        
    }
    
    public void cloneAllBookmarks(String username, String device){
        LinkedList<Bookmark> allBm = null;
        try{
            allBm = this.dt.getBookmarks(this.dt.getID(username));
        }catch(Error e){
            System.out.println("Error on load all bookmarks");
            allBm = new LinkedList();
        }finally{
        
            System.out.println("All bookmarks:\n"+allBm.size());
            Bookmark current;
            int max = allBm.size();
            System.out.println("Max set on: "+max);
            for(int i=0;i<max;i++){
                current = allBm.get(i);
                System.out.println("Adding bookmark: "+i);
                this.addLocalized(username, current.getTitle(), device);
            }
            System.out.println("All bookmarks localizated to: "+device);
        }
    }
    
    public void cloneDeviceBookmarks(String username, String deviceToClone, String device){
        LinkedList<Bookmark> destBm = this.getDestinationBookmarks(username, deviceToClone);
        Bookmark current;
        int max = destBm.size();
        for(int i=0;i<max;i++){
            current = destBm.get(i);
            System.out.println("Adding bookmark: "+i);
            this.addLocalized(username, current.getTitle(), device);
        }
        System.out.println("Bookmarks localizated to: "+device);
    }
}
