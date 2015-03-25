package DataAccessTier;

import java.util.LinkedList;

public interface DataAccessInterface {
    public String getID(String username);
    public LinkedList getBookmarks(String id);
    public LinkedList getDestinations(String id);
    public boolean login(String id,String password);
    public void addUser(String username,String firstname,String lastname,String password,String email,String country,String regdate);
}
