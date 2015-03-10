package DataAccessTier;

public class Destination {
    
    private String ID;
    private String device;
    private String os;
    private String browser;
    
    public Destination(){
        
    }
    
    public String getID(){
        return this.ID;
    }
    
    public void setID(String ID){
        this.ID = ID;
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
}
