/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessTier;

import java.util.LinkedList;

public interface DataAccessInterface {
    public LinkedList getBookmarks(String id);
    public LinkedList getDestinations(String id);
}
