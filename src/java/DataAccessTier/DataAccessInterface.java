package DataAccessTier;

import java.util.LinkedList;

public interface DataAccessInterface {
    public String getID(String username);
    public LinkedList getBookmarks(String id);
    public LinkedList getDestinations(String id);
    public boolean login(String id,String password);
}
