/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicTier;

import DataAccessTier.DataAccessInterface;
import DataAccessTier.DataAccess;
import DataAccessTier.User;
import DataAccessTier.Bookmark;
import DataAccessTier.Destination;
import java.util.LinkedList;


public class BusinessLogic implements BusinessLogicInterface{
    
    DataAccessInterface dt;
    
    public BusinessLogic(){
        this.dt = new DataAccess();
    }
    
    public LinkedList getBookmarks(String username){
        return this.dt.getBookmarks(this.dt.getID(username));
    }
    public LinkedList getDestinations(String username){
        return this.dt.getDestinations(this.dt.getID(username));
    }
    public boolean login(String username,String password){
        return this.dt.login(this.dt.getID(username),password);
    }
    
    public void addUser(String username,String firstname,String lastname,String password,String email,String country,String regdate){
        this.dt.addUser(username,firstname,lastname,password,email,country,regdate);
    }
}
