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
        this.dt= new DataAccess();
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
    
    public void addBookmark(String username,String title,String url,String lasteditdate,String fatherpath,String type,String description,String tag){
        this.dt.addBookmark(this.dt.getID(username),title,url,lasteditdate,fatherpath,type,description,tag);
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
    
    public void editBookmarkFatherPath(String bookid,String fatherPath){
        this.dt.editBookmarkFatherPath(bookid, fatherPath);
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
        LinkedList<Bookmark> list = this.dt.getBookmarks(this.dt.getID(username));
        for(int i=0;i<list.size();i++){
            if(list.get(i).getUrl().equals(url))
                index=i;
        }
        String bookID= list.get(index).getBookID();
        return bookID;
    }
    
    public Bookmark getBookmark(String username,String bookID){
        int index=-1;
        LinkedList<Bookmark> list = this.dt.getBookmarks(this.dt.getID(username));
        for(int i=0;i<list.size();i++){
            if(list.get(i).getBookID().equals(bookID))
                index=i;
        }
        return list.get(index);
    }
    
    public void deleteBookmark(String username,String url){
        this.dt.deleteBookmark(getBookID(username,url));
    }
    
    public void addDestination(String username,String device,String os,String browser){
        this.dt.addDestination(this.dt.getID(username),device,os,browser);
    }
}
