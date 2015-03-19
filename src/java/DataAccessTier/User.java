package DataAccessTier;

public class User {
    private String ID;
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private String email;
    private String country;
    private String regdate;
    
    public User(){
        
    }
    
    public String getID(){
        return this.ID;
    }
    
    public void setID(String ID){
        this.ID=ID;
    }
    
    public String getFirstName(){
        return this.firstname;
    }
    
    public void setFirstName(String firstname){
        this.firstname = firstname;
    }
    
    public String getLastName(){
        return this.lastname;
    }
    
    public void setLastName(String lastname){
        this.lastname = lastname;
    }
    
    public String getUserName(){
        return this.username;
    }
    
    public void setUserName(String username){
        this.username = username;
    }
    
    public String getPassword(){
        return this.password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public String getEmail(){
        return this.email;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getCountry(){
        return this.country;
    }
    
    public void setCountry(String country){
        this.country = country;
    }
    
    public String getDate(){
        return this.regdate;
    }
    
    public void setDate(String regdate){
        this.regdate = regdate;
    }
}
