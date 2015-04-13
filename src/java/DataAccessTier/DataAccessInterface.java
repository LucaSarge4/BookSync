package DataAccessTier;

import java.util.LinkedList;

public interface DataAccessInterface {
    public String getID(String username);
    public LinkedList getBookmarks(String id);
    public LinkedList getDestinations(String id);
    public LinkedList getDestinationBookmarks(String destID);
    public LinkedList getUnselectedDestinationBookmarks(String userID,String destID);
    public boolean login(String id,String password);
    public void addUser(String username,String firstname,String lastname,String password,String email,String country,String regdate);
    public void addBookmark(String userid,String title,String url,String lasteditdate,String fatherpath,String type,String description,String tag);
    public void editBookmarkTitle(String bookid,String title);
    public void editBookmarkUrl(String bookid,String url);
    public void editBookmarkLastEditDate(String bookid,String date);
    public void editBookmarkFatherPath(String bookid,String fatherPath);
    public void editBookmarkTag(String bookid,String tag);
    public void editBookmarkDescription(String bookid,String desc);
    public void editBookmarkIcon(String bookid,String icon);
    public void deleteBookmark(String bookID);
    public void addDestination(String userid,String device,String os,String browser);
    public void localized(String bookid,String destid);
    public void deleteLocalized(String bookID,String destID);
    public void editDestinationName(String destID,String name);
    public void editDestinationOS(String destID,String os);
    public void editDestinationBrowser(String destID,String browser);
    public void editDestinationDropbox(String destID,String drop);
}
