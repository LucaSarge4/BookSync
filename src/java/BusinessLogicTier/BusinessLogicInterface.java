/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicTier;

import java.util.LinkedList;

public interface BusinessLogicInterface {
    public LinkedList getBookmarks(String username);
    public LinkedList getDestinations(String username);
    public boolean login(String username,String password);
    public void addUser(String username,String firstname,String lastname,String password,String email,String country,String regdate);
    public void addBookmark(String userid,String title,String url,String lasteditdate,String fatherpath,String type);
    public void deleteBookmark(String username,String url);
}
