
package controllers;

import connection.ConnectionClass;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Order_Summary;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Formatter;
import java.util.ResourceBundle;


public class HomeController implements Initializable {
    
    EditRecordsPageController recordsController;
    
    private boolean admin;
    
    @FXML
    private Label activeUserLabel;
    
    @FXML
    private Label seachByLabel;
    
    @FXML
    private Label searchForLabel;
    
    @FXML
    private Button cancelJobButton;

    @FXML
    private Button markButton;
    
    @FXML
    private Button orderButton;

    @FXML
    private Button claimButton;

    @FXML
    private Button searchButton;

    @FXML
    private Button recordsButton;

    @FXML
    private Button editRecordsButton;

    @FXML
    private Button logoutButton;

    @FXML
    private TableView<Order_Summary> pendingTable;
    
    @FXML
    private TableColumn<Order_Summary, Double> pendingAmountDueColumn;

    @FXML
    private TableColumn<Order_Summary, Integer> pendingJobNUmColumn;

    @FXML
    private TableColumn<Order_Summary, String> pendingClientFirstnameColumn;
    
    @FXML
    private TableColumn<Order_Summary, String> pendingClientLastnameColumn;

    @FXML
    private TableColumn<Order_Summary, String> pendingContactNumColumn;
    
    @FXML
    private TableColumn<Order_Summary, String> pendingEmailColumn;

    @FXML
    private TableColumn<Order_Summary, String> pendingProductNameColumn;

    @FXML
    private TableColumn<Order_Summary, String> pendingSizeColumn;

    @FXML
    private TableColumn<Order_Summary, Integer> pendingQuantityColumn;

    @FXML
    private TableColumn<Order_Summary, LocalDate> pendingDateOrderedColumn;

    @FXML
    private TableColumn<Order_Summary, LocalDate> pendingDateClaimColumn;

    @FXML
    private TableColumn<Order_Summary, Double> pendingAmountPaidColumn;

    @FXML
    private TableColumn<Order_Summary, Double> pendingBalanceColumn;

    @FXML
    private TableColumn<Order_Summary, String> pendingAssignedColumn;

    @FXML
    private TableColumn<Order_Summary, String> pendingRecieverColumn;

    @FXML
    private TableColumn<Order_Summary, String> pendingJobStatusColumn;

    @FXML
    private TableView<Order_Summary> ClaimTable;
    
    @FXML
    private TableColumn<Order_Summary, Double> claimAmountDueColumn;
   
    @FXML
    private TableColumn<Order_Summary, Integer> claimJObNumColumn;

    @FXML
    private TableColumn<Order_Summary, String> claimFirstNameColumn;
    
    @FXML
    private TableColumn<Order_Summary, String> claimLastnameColumn;

    @FXML
    private TableColumn<Order_Summary, String> claimContactNumColumn;
    
    @FXML
    private TableColumn<Order_Summary, String> claimEmailColumn;

    @FXML
    private TableColumn<Order_Summary, String> claimProductNameColumn;

    @FXML
    private TableColumn<Order_Summary, String> claimSizeColumn;

    @FXML
    private TableColumn<Order_Summary, Integer> claimQuantityColumn;

    @FXML
    private TableColumn<Order_Summary, LocalDate> claimDateOrderedColumn;

    @FXML
    private TableColumn<Order_Summary, LocalDate> claimClaimDateColumn;

    @FXML
    private TableColumn<Order_Summary, Double> claimAmountPaidColumn;

    @FXML
    private TableColumn<Order_Summary, Double> claimBalanceColumn;

    @FXML
    private TableColumn<Order_Summary, String> claimAssignedColumn;

    @FXML
    private TableColumn<Order_Summary, String> claimReciverColumn;

    @FXML
    private TableColumn<Order_Summary, String> claimJobStatusColumn;

    ObservableList<Order_Summary> pending, forClaim;

    ConnectionClass conn;

    ResultSet rs, rs2;

    @FXML
    void markButtonClicked(ActionEvent event) {
        
        int id;
        Order_Summary markDone;
        markDone=pendingTable.getSelectionModel().getSelectedItem();
        id=markDone.getJobID();
        
        
        StringBuilder sb = new StringBuilder();
        Formatter fm = new Formatter(sb);
        fm.format("UPDATE transaction_records SET status='for claiming' WHERE jobID=%d", id);
        try {
            conn.update(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        fillObservableList();
        populateTables(pending, forClaim);
    }
    
    @FXML
    void cancelJobButtonClicked(ActionEvent event) {
        
        int id;
        Order_Summary cancelJob;
        cancelJob=pendingTable.getSelectionModel().getSelectedItem();
        id=cancelJob.getJobID();

        StringBuilder sb = new StringBuilder();
        Formatter fm = new Formatter(sb);
        fm.format("DELETE FROM transaction_records WHERE jobID=%d", id);

        try {
            conn.delete(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        fillObservableList();
        populateTables(pending, forClaim);
    }
    
    @FXML
    void claimButtonClicked(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/claimPage.fxml"));
        Parent home = loader.load();

        ClaimPageController claimPageController = loader.getController();
        claimPageController.setHomeController(this);

        Scene scene2=new Scene(home);
        Stage window=new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initOwner(home.getScene().getWindow());
        window.setResizable(false);
        window.setScene(scene2);
        window.show();
    }

    @FXML
    void editRecordsButtonClicked(ActionEvent event) throws IOException{
        
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/editRecordsPage.fxml"));
        Parent home = loader.load();
        EditRecordsPageController recordsPageController=loader.getController();
        recordsPageController.setHomeController(this);
        recordsPageController.setAdmin(isAdmin(activeUserLabel.getText()));

        Scene scene2=new Scene(home);
        Stage window=new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initOwner(home.getScene().getWindow());
        window.setResizable(false);
        window.setScene(scene2);
        window.showAndWait();

    }

    @FXML
    void logoutButtonClicked(ActionEvent event) throws IOException {
        
        Parent home = FXMLLoader.load(getClass().getResource("/views/LoginPage.fxml"));
        Scene scene2=new Scene(home);

        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.centerOnScreen();
        window.setMaximized(false);
        window.setResizable(false);
        window.setScene(scene2);
        window.show();
    }

    @FXML
    void orderButtonClicked(ActionEvent event) throws IOException {
        
        


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/OrderPage.fxml"));
        Parent home = loader.load();
        OrderPageController orderPageController = loader.getController();
        orderPageController.setHomeController(this);
        orderPageController.setActiveUserID(activeUserLabel.getText());

        Scene scene2=new Scene(home);
        Stage window=new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initOwner(home.getScene().getWindow());
        window.setResizable(false);
        window.setScene(scene2);
        window.showAndWait();
    }

    @FXML
    void recordsButtonClicked(ActionEvent event) throws IOException {
        
        Parent home = FXMLLoader.load(getClass().getResource("/views/OrderPage.fxml"));
        
        
        Scene scene2=new Scene(home);
        Stage window=new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initOwner(home.getScene().getWindow());
        window.setResizable(false);
        window.setScene(scene2);
        window.showAndWait();
    }

    @FXML
    void searchButtonClicked(ActionEvent event) throws IOException {
            Parent home = FXMLLoader.load(getClass().getResource("/views/OrderPage.fxml"));
            Scene scene2=new Scene(home); 
            Stage window=new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.initOwner(home.getScene().getWindow());
            window.setResizable(false);
            window.setScene(scene2);
            window.showAndWait();  
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            conn = new ConnectionClass();
        } catch (SQLException e) {
           e.printStackTrace();
        }

        fillObservableList();
    }

    public void fillObservableList() {

        try {
            pending = conn.selectToList("SELECT transaction_records.jobID, customer_records.firstname, customer_records.lastname, customer_records.phone, customer_records.email, product_records.productName, transaction_records.size, transaction_records.quantity, transaction_records.orderDate, transaction_records.dateClaim, transaction_records.amountDue, transaction_records.amountPaid, transaction_records.balance, employees_record.empLname, useraccounts_records.username, transaction_records.status FROM transaction_records JOIN product_records ON transaction_records.productID=product_records.productID JOIN employees_record ON transaction_records.empID=employees_record.employeeID JOIN customer_records ON transaction_records.customerID=customer_records.customerID JOIN useraccounts_records ON transaction_records.userID=useraccounts_records.userID WHERE transaction_records.status='pending'");
            forClaim = conn.selectToList("SELECT transaction_records.jobID, customer_records.firstname, customer_records.lastname, customer_records.phone, customer_records.email, product_records.productName, transaction_records.size, transaction_records.quantity, transaction_records.orderDate, transaction_records.dateClaim, transaction_records.amountDue, transaction_records.amountPaid, transaction_records.balance, employees_record.empLname, useraccounts_records.username, transaction_records.status FROM transaction_records JOIN product_records ON transaction_records.productID=product_records.productID JOIN employees_record ON transaction_records.empID=employees_record.employeeID JOIN customer_records ON transaction_records.customerID=customer_records.customerID JOIN useraccounts_records ON transaction_records.userID=useraccounts_records.userID WHERE transaction_records.status='for claiming'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void populateTables(ObservableList<Order_Summary> pending, ObservableList<Order_Summary> claim) {
        
        pendingJobNUmColumn.setCellValueFactory(cellData -> cellData.getValue().jobIDProperty().asObject());
        pendingClientFirstnameColumn.setCellValueFactory(cellData -> cellData.getValue().firstnameProperty());
        pendingClientLastnameColumn.setCellValueFactory(cellData -> cellData.getValue().lastnameProperty());
        pendingEmailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        pendingContactNumColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        pendingProductNameColumn.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        pendingSizeColumn.setCellValueFactory(cellData -> cellData.getValue().sizeProperty());
        pendingQuantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        pendingDateOrderedColumn.setCellValueFactory(cellData -> cellData.getValue().orderDateProperty());
        pendingDateClaimColumn.setCellValueFactory(cellData -> cellData.getValue().claimDateProperty());
        pendingAmountDueColumn.setCellValueFactory(cellData -> cellData.getValue().amountDueProperty().asObject());
        pendingAmountPaidColumn.setCellValueFactory(cellData -> cellData.getValue().amountPaidProperty().asObject());
        pendingBalanceColumn.setCellValueFactory(cellData -> cellData.getValue().balanceProperty().asObject());
        pendingAssignedColumn.setCellValueFactory(cellData -> cellData.getValue().empLnameProperty());
        pendingRecieverColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        pendingJobStatusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());


        claimJObNumColumn.setCellValueFactory(cellData -> cellData.getValue().jobIDProperty().asObject());
        claimFirstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstnameProperty());
        claimLastnameColumn.setCellValueFactory(cellData -> cellData.getValue().lastnameProperty());
        claimEmailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        claimContactNumColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        claimProductNameColumn.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        claimSizeColumn.setCellValueFactory(cellData -> cellData.getValue().sizeProperty());
        claimQuantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        claimDateOrderedColumn.setCellValueFactory(cellData -> cellData.getValue().claimDateProperty());
        claimClaimDateColumn.setCellValueFactory(cellData -> cellData.getValue().claimDateProperty());
        claimAmountDueColumn.setCellValueFactory(cellData -> cellData.getValue().amountDueProperty().asObject());
        claimAmountPaidColumn.setCellValueFactory(cellData -> cellData.getValue().amountPaidProperty().asObject());
        claimBalanceColumn.setCellValueFactory(cellData -> cellData.getValue().balanceProperty().asObject());
        claimAssignedColumn.setCellValueFactory(cellData -> cellData.getValue().empLnameProperty());
        claimReciverColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        claimJobStatusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        pendingTable.setItems(pending);
        ClaimTable.setItems(claim);
    }

    public void setActiveUser(String name){
        activeUserLabel.setText(name);
    } 
    
    public boolean isAdmin(String fullname){
        ResultSet rs3;
        
        try {
            Connection conb=conn.getConnection();
            rs3=conb.createStatement().executeQuery("SELECT useraccounts_records.Admin FROM useraccounts_records JOIN employees_record ON useraccounts_records.empID=employees_record.employeeID WHERE CONCAT(employees_record.empFname,' ',employees_record.empLname)='"+fullname+"'");
            while(rs3.next()){
                admin=rs3.getBoolean("Admin");
            }
        } catch (SQLException | ClassNotFoundException ex) {    
        }
        
        return admin;
    }
    
    public void setRecordController(EditRecordsPageController controller){
        recordsController=controller;
    }
    
    
}
