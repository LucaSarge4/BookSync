package DataAccessTier;

import java.util.LinkedList;

public interface DataAccessInterface {
    public LinkedList getBookmarks(String id);
    public LinkedList getDestinations(String id);
    public String getID(String username);
    public boolean login(String id,String password);
}
