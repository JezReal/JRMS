
package models;

public class userAccounts {
    private int usernum, empid;
    private String username, password, firstname, lastname;
    private boolean admin;
    
    public userAccounts(int usernum,int empid, String username, String password, String firstname, String lastname, boolean admin ){
        this.usernum=usernum;
        this.password=password;
        this.username=username;
        this.firstname=firstname;
        this.lastname=lastname;
        this.empid=empid;
        this.admin=admin;
    }
    
    public userAccounts(){
        this.usernum=0;
        this.password="";
        this.username="";
        this.firstname="";
        this.lastname="";
        this.empid=0;
        this.admin=false;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public int getEmpid() {
        return empid;
    }

    public void setEmpid(int empid) {
        this.empid = empid;
    }

    public int getUsernum() {
        return usernum;
    }

    public void setUsernum(int usernum) {
        this.usernum = usernum;
    }
    
    public String getUsername(){
        return username;
    }
    
    public String getPassword(){
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
