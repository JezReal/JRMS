
package models;

public class Employee_Records {

    private int empNum;
    private String empLname, empFname, empMname, empMobile, empEmail;

    public Employee_Records(int empNum, String empLname, String empFname, String empMname, String empMobile, String empEmail) {
        this.empNum = empNum;
        this.empMobile = empMobile;
        this.empLname = empLname;
        this.empFname = empFname;
        this.empMname = empMname;
        this.empEmail = empEmail;
    }

    public Employee_Records() {
        this.empNum = 0;
        this.empLname = "";
        this.empFname = "";
        this.empMname = "";
        this.empMobile = "";
        this.empEmail = "";
    }


    public int getEmpNum() {
        return empNum;
    }

    public void setEmpNum(int empNum) {
        this.empNum = empNum;
    }

    public String getEmpMobile() {
        return empMobile;
    }

    public void setEmpMobile(String empMobile) {
        this.empMobile = empMobile;
    }

    public String getEmpLname() {
        return empLname;
    }

    public void setEmpLname(String empLname) {
        this.empLname = empLname;
    }

    public String getEmpFname() {
        return empFname;
    }

    public void setEmpFname(String empFname) {
        this.empFname = empFname;
    }

    public String getEmpMname() {
        return empMname;
    }

    public void setEmpMname(String empMname) {
        this.empMname = empMname;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }
}
