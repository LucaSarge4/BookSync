/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicTier;

import DataAccessTier.Bookmark;
import DataAccessTier.Destination;
import java.util.LinkedList;

public interface BusinessLogicInterface {
    public LinkedList getUsers();
    public boolean checkUsername(String username);
    public boolean checkEmail(String email);
    public LinkedList getBookmarks(String username);
    public LinkedList getDestinations(String username);
    public LinkedList getDestinationBookmarks(String username,String deviceName);
    public LinkedList getUnselectedDestinationBookmarks(String username,String deviceName);
    public boolean login(String username,String password);
    public void addUser(String username,String firstname,String lastname,String password,String email,String country,String regdate);
    public void addBookmark(String userid,String title,String url,String lasteditdate,String fatherfolder,String type,String description,String tag);
    public void editBookmarkTitle(String bookid,String title);
    public void editBookmarkUrl(String bookid,String url);
    public void editBookmarkLastEditDate(String bookid,String date);
    public void editBookmarkFatherFolder(String bookid,String fatherFolder);
    public void editBookmarkTag(String bookid,String tag);
    public void editBookmarkDescription(String bookid,String desc);
    public void editBookmarkIcon(String bookid,String icon);
    public String getBookID(String username,String url);
    public Bookmark getBookmark(String username,String bookID);
    public Destination getDestination(String username,String destID);
    public void deleteBookmark(String username,String url);
    public void addDestination(String username,String device,String os,String browser,String dropboxPath);
    public String getDestinationID(String username,String deviceName);
    public void addLocalized(String username,String url,String deviceName);
    public void deleteLocalized(String username,String url,String deviceName);
    public void editDestinationName(String destID,String name);
    public void editDestinationOS(String destID,String os);
    public void editDestinationBrowser(String destID,String browser);
    public void editDestinationDropbox(String destID,String drop);
    public String getBookmarksDescriptionList(String username, String device);
    public String getBookmarksDescriptionList(String username);
    public boolean isBookmarkToRemove(String user, String url);
    public String getUpperFolder(String username, String father);
    public void cloneAllBookmarks(String username, String device);
    public void cloneDeviceBookmarks(String username, String deviceToClone, String device);
}
