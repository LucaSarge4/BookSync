package DataAccessTier;

public class Destination {
    
    private String DestinationID;
    private String device;
    private String os;
    private String browser;
    private String droppath;
    
    public Destination(){
        
    }
    
    public String getDestinationID(){
        return this.DestinationID;
    }
    
    public void setDestinationID(String destinationid){
        this.DestinationID = destinationid;
    }
    
    public String getDevice(){
        return this.device;
    }
    
    public void setDevice(String device){
        this.device = device;
    }
    
    public String getOS(){
        return this.os;
    }
    
    public void setOS(String os){
        this.os = os;
    }
    
    public String getBrowser(){
        return this.browser;
    }
    
    public void setBrowser(String browser){
        this.browser = browser;
    }
    
    public String getDropPath(){
        return this.droppath;
    }
    
    public void setDropPath(String droppath){
        this.droppath = droppath;
    }
}
