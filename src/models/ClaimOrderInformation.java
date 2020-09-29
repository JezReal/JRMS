package models;

import java.time.LocalDate;

public class ClaimOrderInformation {
    private int jobID;
    private LocalDate orderDate;
    private String productName;
    private String size;
    private String quantity;
    private String amountDue;
    private Double amountPaid;
    private Double balance;
    private String empFName;
    private String userName;

    public ClaimOrderInformation(int jobID, LocalDate orderDate, String productName, String size, String quantity, String amountDue, Double amountPaid, Double balance, String empFName, String username) {
        this.jobID = jobID;
        this.orderDate = orderDate;
        this.productName = productName;
        this.size = size;
        this.quantity = quantity;
        this.amountDue = amountDue;
        this.amountPaid = amountPaid;
        this.balance = balance;
        this.empFName = empFName;
        this.userName = username;
    }

    public String getUserName() {
        return userName;
    }

    public int getJobID() {
        return jobID;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public String getProductName() {
        return productName;
    }

    public String getSize() {
        return size;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getAmountDue() {
        return amountDue;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public Double getBalance() {
        return balance;
    }

    public String getEmpFName() {
        return empFName;
    }
}
