package DataAccessTier;

public class Bookmark {
    private String BookID;
    private String title;
    private String url;
    private String lasteditdate;
    private String fatherfolder;
    private String tag;
    private String description;
    private String type;
    private String icon;
    
    public Bookmark(){
        
    }
    
    public String getBookID(){
        return this.BookID;
    }
    
    public void setBookID(String bookid){
        this.BookID=bookid;
    }
    
    public String getTitle(){
        return this.title;
    }
    
    public void setTitle(String title){
        this.title = title;
    }
    
    public String getUrl(){
        return this.url;
    }
    
    public void setUrl(String url){
        this.url = url;
    }
    
    public String getLastEditDate(){
        return this.lasteditdate;
    }
    
    public void setLastEditDate(String lasteditdate){
        this.lasteditdate = lasteditdate;
    }
    
    public String getFatherFolder(){
        return this.fatherfolder;
    }
    
    public void setFatherFolder(String fatherfolder){
        this.fatherfolder = fatherfolder;
    }
    
    public String getTag(){
        return this.tag;
    }
    
    public void setTag(String tag){
        this.tag = tag;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public void setDescription(String desc){
        this.description = desc;
    }
    
    public String getType(){
        return this.type;
    }
    
    public void setType(String type){
        this.type = type;
    }
    
    public String getIcon(){
        return this.icon;
    }
    
    public void setIcon(String icon){
        this.icon = icon;
    }
}
