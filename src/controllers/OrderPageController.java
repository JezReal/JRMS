package controllers;

import connection.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import models.Notifications;
import models.Order_Summary;
import models.Transaction_Records;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.ResourceBundle;

public class OrderPageController implements Initializable {

    @FXML
    private TextField quantityField;

    @FXML
    private Button viewSummaryButton;

    @FXML
    private Text orderDetailsText;

    @FXML
    private Label dateOrderLabel;

    @FXML
    private DatePicker dateOrderPicker;

    @FXML
    private Label clientFnameLabel;

    @FXML
    private Label clientLnameLabel;

    @FXML
    private TextField clientFnameField;

    @FXML
    private TextField clientLnameField;

    @FXML
    private Label clientEmailLabel;

    @FXML
    private Label contactNoLabel;

    @FXML
    private TextField contactField;

    @FXML
    private TextField emailAddressField;

    @FXML
    private Label dateOfClaimLabel;

    @FXML
    private DatePicker dateOfClaimPicker;

    @FXML
    private Label chooseProductLabel;

    @FXML
    private ComboBox<String> chooseProductComboBox;

    @FXML
    private Label sizeLabel;

    @FXML
    private TextField heightField;

    @FXML
    private Label xLabel;

    @FXML
    private TextField widthField;

    @FXML
    private Label amountDueLabel;

    @FXML
    private TextField AmountDueField;

    @FXML
    private Label amountPaidLabel;

    @FXML
    private TextField amountPaidField;

    @FXML
    private Label changeLabel;

    @FXML
    private TextField changeField;

    @FXML
    private Label balanceLabel;

    @FXML
    private TextField balanceField;

    @FXML
    private Label assignToLabel;

    @FXML
    private ComboBox<String> assignToComboBox;

    @FXML
    private Button clearButton;

    @FXML
    private Button saveTransactionButton;

    @FXML
    private TextArea summaryTextArea;
    
    private int id;

    HomeController homeController;

    ResultSet resultSet, resultSet2;

    ConnectionClass condb;

    ObservableList<Order_Summary> pending;
    ObservableList<Order_Summary> forClaim;

    @FXML
    void calculatePriceClicked(ActionEvent event) {
        calculatePrice();

    }

    @FXML
    void calculateChangeAndBalanceClicked(ActionEvent event) {
        calculateBalance();

    }

    @FXML
    void viewSummaryButtonClicked(ActionEvent event) {
        
        if(incompleteFields()){
            getSelected();

        summaryTextArea.setText("MY PRINTING COMPANY");
        summaryTextArea.appendText("\nYour number 1 printing solution");
        summaryTextArea.appendText("\nNo.8, Corpuz St., West Tapinac, Olongapo City\n");
        summaryTextArea.appendText("\nDate of order:\t\t\t"+dateOrderPicker.getValue().toString());
        summaryTextArea.appendText("\nSold to:\t\t\t\t"+clientFnameField.getText()+" "+clientLnameField.getText());
        summaryTextArea.appendText("\nContact:\t\t\t\t"+contactField.getText());
        summaryTextArea.appendText("\nDate of claim:\t\t\t"+dateOfClaimPicker.getValue().toString());
        summaryTextArea.appendText("\nProduct:\t\t\t\t"+chooseProductComboBox.getValue());
        summaryTextArea.appendText("\nQuantity:\t\t\t\t"+quantityField.getText()+"pc/s.");
        summaryTextArea.appendText("\nSize:\t\t\t\t\t"+heightField.getText()+"ft. "+"x "+widthField.getText()+"ft.");
        summaryTextArea.appendText("\nAmount Due\t\t\t"+"₱"+AmountDueField.getText());
        summaryTextArea.appendText("\nAmount Paid\t\t\t"+"₱"+amountPaidField.getText());
        summaryTextArea.appendText("\nBalance:\t\t\t\t" +"₱"+balanceField.getText());
        summaryTextArea.appendText("\nAssigned:\t\t\t\t" +assignToComboBox.getValue());
            
        }
        
        else{
            Notifications error=new Notifications("Incomplete Fields!", "Please complete all the fields");
            error.showError();
        }

        
    }

    @FXML
    void clearButtonClicked(ActionEvent event) {

    }

    @FXML
    void saveTransactionButtonClicked(ActionEvent event) {
        
        if(incompleteFields()){
            saveTransaction();
            transactionSuccessful();
        }
        else{
            Notifications error=new Notifications("Incomplete Fields!!", "Please complete all the fields");
            error.showError();
        }  
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            condb = new ConnectionClass();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//Set the date to current date
        dateOrderPicker.setValue(LocalDate.now());
//method for populating
        addItems();
        pending = FXCollections.observableArrayList();
        forClaim = FXCollections.observableArrayList();

    }

    public void addItems() {

//populate the combobox for products and employee
        ObservableList<String> product=FXCollections.observableArrayList();
        ObservableList<String> employee=FXCollections.observableArrayList();

        try {
            resultSet = condb.select("SELECT productName FROM product_records WHERE status='available'");
            resultSet2 = condb.select("SELECT CONCAT(employees_record.empLname,', ', employees_record.empFname) as fullName FROM employees_record WHERE employees_record.status='employed'");

            while(resultSet.next()){
                product.add(resultSet.getString("productName"));
                chooseProductComboBox.setItems(product);
            }
            while (resultSet2.next()) {
                employee.add(resultSet2.getString("fullName"));
                assignToComboBox.setItems(employee);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        assignToComboBox.getSelectionModel().select(0);
        chooseProductComboBox.getSelectionModel().select(0);
    }

    private void calculatePrice() {
        double height, width, price, product = 0, change;
        int quantity;
        String totalPrice;
        height=Double.parseDouble(heightField.getText());
        width=Double.parseDouble(widthField.getText());
        quantity=Integer.parseInt(quantityField.getText());

//        TODO: fix this query

        try{
            Connection conn=condb.getConnection();
            ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM product_records WHERE productName= '"+chooseProductComboBox.getValue()+"'");

            while(rs.next()){
                product=rs.getDouble("productPrice");

            }

                price=quantity*(height*width)*product;
                totalPrice=String.valueOf(price);
                AmountDueField.setText(totalPrice);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void calculateBalance() {
        double change,balance, amountPaid, price;
        String balanceComputed, changeComputed;

        amountPaid=Double.parseDouble(amountPaidField.getText());
        price=Double.parseDouble(AmountDueField.getText());

        if (amountPaid<price){
            change=0;
        } else{
           change = amountPaid-price;
        }

        if (amountPaid>price) {
            balance=0;

        } else {
            balance=price-amountPaid;
        }

        balanceComputed=String.valueOf(balance);
        changeComputed=String.valueOf(change);

        changeField.setText(changeComputed);
        balanceField.setText(balanceComputed);
    }

    public  void saveTransaction() {
        
            inserCustomer();
            insertTrnsaction();
  
    }

    public void getSelected(){
        String selected;
        selected=chooseProductComboBox.getValue();
    }

    private void inserCustomer() {

        String lastName = clientLnameField.getText();
        String firstName = clientFnameField.getText();
        String phoneNumber = contactField.getText();
        String email = emailAddressField.getText();

        try{

//            TODO: change this query

            StringBuilder stringBuilder = new StringBuilder();
            Formatter formatter = new Formatter(stringBuilder);
            formatter.format("INSERT INTO customer_records VALUES(0, '%s', '%s', '%s', '%s')", lastName, firstName, phoneNumber, email);
            condb.insert(stringBuilder.toString());
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void insertTrnsaction() {
        Transaction_Records transaction=new Transaction_Records();
//TODO: change this query. unnecessary objects
        try {
            Connection connect=condb.getConnection();
            ResultSet rs1 = connect.createStatement().executeQuery("SELECT productID FROM product_records WHERE  productName='"+chooseProductComboBox.getValue()+"'");
            ResultSet rs2 = connect.createStatement().executeQuery("SELECT employeeID FROM employees_record WHERE CONCAT(employees_record.empLname,', ',employees_record.empFname) ='"+assignToComboBox.getValue()+"'");
            ResultSet rs3 = connect.createStatement().executeQuery("SELECT customerID FROM `customer_records` WHERE lastname='"+clientLnameField.getText()+"' and firstname='"+clientFnameField.getText()+"'");
            while (rs1.next()) {
                transaction.setProductID(rs1.getInt("productID"));
            }
            while(rs2.next()){
                transaction.setEmpID(rs2.getInt("employeeID"));
            }
            while(rs3.next()){
                transaction.setCustomerID(rs3.getInt("customerID"));
            }
            connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        transaction.setJobID(0);
        transaction.setUserID(this.getActiveUserID());
        transaction.setSize(heightField.getText()+"X"+widthField.getText());
        transaction.setQuantity(Integer.parseInt(quantityField.getText()));
        transaction.setDateOrdered(dateOrderPicker.getValue());
        transaction.setDateClaim(dateOfClaimPicker.getValue());
        transaction.setAmountDue(Double.parseDouble(AmountDueField.getText()));
        transaction.setAmountPaid(Double.parseDouble(amountPaidField.getText()));
        transaction.setBalance(Double.parseDouble(balanceField.getText()));
        transaction.setStatus("pending");

        try{
            Connection connection=condb.getConnection();
            String insertTransaction="INSERT INTO transaction_records VALUES ('"+transaction.getJobID()+"','"+transaction.getProductID()+"','"+transaction.getCustomerID()+"','"+transaction.getEmpID()+"','"+transaction.getUserID()+"','"+transaction.getSize()+"','"+transaction.getQuantity()+"','"+transaction.getDateOrdered()+"','"+transaction.getDateClaim()+"','"+transaction.getAmountDue()+"','"+transaction.getAmountPaid()+"','"+transaction.getBalance()+"','"+transaction.getStatus()+"')";
            Statement statement=connection.createStatement();
            statement.executeUpdate(insertTransaction);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        homeController.fillObservableList();
    }

    private void transactionSuccessful() {
        summaryTextArea.setText("Transaction saved successfully...");
        changeField.setText("0");
        widthField.clear();
        quantityField.setText("1");
        heightField.clear();
        emailAddressField.clear();
        contactField.clear();
        clientLnameField.clear();
        clientFnameField.clear();
        changeField.clear();
        amountPaidField.setText("0");
        AmountDueField.setText("0");
        dateOfClaimPicker.setValue(null);
    }

    public void setHomeController(HomeController controller) {
        homeController = controller;
    }
    
    public void setActiveUserID(String userName){  
        
        ResultSet rs;
        try {
            Connection connection=condb.getConnection();
            rs=connection.createStatement().executeQuery("SELECT useraccounts_records.userID FROM useraccounts_records JOIN employees_record ON useraccounts_records.empID=employees_record.employeeID WHERE CONCAT(employees_record.empFname,' ',employees_record.empLname)='"+userName+"'");

            while(rs.next()){
                id=rs.getInt("userID");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public int getActiveUserID(){
        return id;
    }
    
    private boolean incompleteFields(){
        
        boolean valid = false;
        
        ArrayList<TextField> fields=new ArrayList<>();
        fields.add(clientFnameField);
        fields.add(clientLnameField);
        fields.add(contactField);
        fields.add(emailAddressField);
        fields.add(heightField);
        fields.add(widthField);
        fields.add(amountPaidField);
        
        for(TextField field: fields){
            if(field.getText().isEmpty()){
                valid=false;
                break;
            }
            else{
                valid=true;
            }
        } 
        return valid;
    }
}










