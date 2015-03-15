package DataAccessTier;

import java.util.LinkedList;

public interface DataAccessInterface {
    public LinkedList getBookmarks(String id);
    public LinkedList getDestinations(String id);
    public boolean login(String id,String username,String password);
}
