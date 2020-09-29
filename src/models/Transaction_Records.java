package models;

import java.time.LocalDate;

public class Transaction_Records {
    private LocalDate dateOrdered, dateClaim;
    private int jobID, productID, customerID, empID, userID, quantity;
    private double amountPaid, balance, amountDue;
    private String size, status;

    public Transaction_Records(int jobID, int productID, int customerID, int empID, int userID, int quantity, LocalDate dateOrdered, LocalDate dateClaim, double amountPaid, double balance, double amountDue, String size, String status) {
        this.dateOrdered = dateOrdered;
        this.dateClaim = dateClaim;
        this.jobID = jobID;
        this.productID = productID;
        this.customerID = customerID;
        this.empID = empID;
        this.userID = userID;
        this.quantity = quantity;
        this.amountPaid = amountPaid;
        this.balance = balance;
        this.amountDue = amountDue;
        this.size = size;
        this.status = status;
    }

    public Transaction_Records() {
        this.status = status;
    }

    public LocalDate getDateOrdered() {
        return dateOrdered;
    }

    public void setDateOrdered(LocalDate dateOrdered) {
        this.dateOrdered = dateOrdered;
    }

    public LocalDate getDateClaim() {
        return dateClaim;
    }

    public void setDateClaim(LocalDate dateClaim) {
        this.dateClaim = dateClaim;
    }

    public int getJobID() {
        return jobID;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getEmpID() {
        return empID;
    }

    public void setEmpID(int empID) {
        this.empID = empID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(double amountDue) {
        this.amountDue = amountDue;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
