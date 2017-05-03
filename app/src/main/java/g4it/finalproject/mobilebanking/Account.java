package g4it.finalproject.mobilebanking;

/**
 * Created by AlphaGeek on 3/16/2017.
 */

public class Account {
    private String firstName;
    private String lastName;
    private String address;
    private String id;
    private String username;
    private String password;
    private String pin;
    static Account currentAccount=new Account("Test","Check","id","addre","test","pass");
    public static String persistentPIN;

    public Account(String fn,String ln,String add,String id,String username,String password){
        this.firstName=fn;
        this.lastName=ln;
        this.address=add;
        this.id=id;
        this.username=username;
        this.password=password;
        this.pin="0000";
    }
    public void setFirstName(String firstName){
        this.firstName=firstName;
    }
    public void setLastName(String lastName){
        this.lastName=lastName;
    }
    public void setAddress(String address){
        this.address=address;
    }
    public void setId(String id){
        this.id=id;
    }
    public void setUsername(String username){
        this.username=username;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public String getFirstName(){
        return this.firstName;
    }
    public String getLastName(){
        return this.lastName;
    }
    public String getPassword(){
        return this.password;
    }
    public String getUsername(){
        return this.username;
    }
    public String getId(){
        return this.id;
    }
    public String getPin(){
        return this.pin;
    }
    public String getPersistentPIN(){return Account.persistentPIN;}
}
