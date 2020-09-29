
package models;

import javafx.beans.property.*;
import java.time.LocalDate;


public class Order_Summary {
    private IntegerProperty jobID, quantity;
    private StringProperty firstname, lastname, phone, email, productName, size, empLname, username, status;
    private DoubleProperty amountDue, amountPaid, balance;
    private ObjectProperty<LocalDate> orderDate, claimDate;

    public Order_Summary(int jobID, String firstname, String lastname, String phone, String email, String productName, String size, int quantity, LocalDate orderDate, LocalDate claimDate, double amountDue, double amountPaid, double balance, String empLname, String username, String status) {

        this.jobID = new SimpleIntegerProperty(jobID);
        this.firstname = new SimpleStringProperty(firstname);
        this.lastname = new SimpleStringProperty(lastname);
        this.phone = new SimpleStringProperty(phone);
        this.email = new SimpleStringProperty(email);
        this.productName = new SimpleStringProperty(productName);
        this.size = new SimpleStringProperty(size);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.orderDate = new SimpleObjectProperty<LocalDate>(orderDate);
        this.claimDate = new SimpleObjectProperty<LocalDate>(claimDate);
        this.amountDue = new SimpleDoubleProperty(amountDue);
        this.amountPaid = new SimpleDoubleProperty(amountPaid);
        this.balance = new SimpleDoubleProperty(balance);
        this.empLname = new SimpleStringProperty(empLname);
        this.username = new SimpleStringProperty(username);
        this.status = new SimpleStringProperty(status);
    }

    public Order_Summary() {
        this.claimDate = claimDate;
    }

    public int getJobID() {
        return jobID.get();
    }

    public IntegerProperty jobIDProperty() {
        return jobID;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public String getFirstname() {
        return firstname.get();
    }

    public StringProperty firstnameProperty() {
        return firstname;
    }

    public String getLastname() {
        return lastname.get();
    }

    public StringProperty lastnameProperty() {
        return lastname;
    }

    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getProductName() {
        return productName.get();
    }

    public StringProperty productNameProperty() {
        return productName;
    }

    public String getSize() {
        return size.get();
    }

    public StringProperty sizeProperty() {
        return size;
    }

    public String getEmpLname() {
        return empLname.get();
    }

    public StringProperty empLnameProperty() {
        return empLname;
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public double getAmountDue() {
        return amountDue.get();
    }

    public DoubleProperty amountDueProperty() {
        return amountDue;
    }

    public double getAmountPaid() {
        return amountPaid.get();
    }

    public DoubleProperty amountPaidProperty() {
        return amountPaid;
    }

    public double getBalance() {
        return balance.get();
    }

    public DoubleProperty balanceProperty() {
        return balance;
    }

    public LocalDate getOrderDate() {
        return orderDate.get();
    }

    public ObjectProperty<LocalDate> orderDateProperty() {
        return orderDate;
    }

    public LocalDate getClaimDate() {
        return claimDate.get();
    }

    public ObjectProperty<LocalDate> claimDateProperty() {
        return claimDate;
    }

    public void setJobID(int jobID) {
        this.jobID.set(jobID);
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public void setFirstname(String firstname) {
        this.firstname.set(firstname);
    }

    public void setLastname(String lastname) {
        this.lastname.set(lastname);
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public void setSize(String size) {
        this.size.set(size);
    }

    public void setEmpLname(String empLname) {
        this.empLname.set(empLname);
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public void setAmountDue(double amountDue) {
        this.amountDue.set(amountDue);
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid.set(amountPaid);
    }

    public void setBalance(double balance) {
        this.balance.set(balance);
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate.set(orderDate);
    }

    public void setClaimDate(LocalDate claimDate) {
        this.claimDate.set(claimDate);
    }
}
