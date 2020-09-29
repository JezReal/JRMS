
package controllers;

import connection.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.ClaimOrderInformation;
import models.ClientName;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.ResourceBundle;

import static models.ClientName.isDuplicate;


public class ClaimPageController implements Initializable {

    @FXML
    private Label clientNameLabel;

    @FXML
    private ComboBox<String> claimClientNameCombobox;

    @FXML
    private Button searchButton;

    @FXML
    private Label amountPaidLabel;

    @FXML
    private TextField amountPaidTextfield;

    @FXML
    private Button computeButton;

    @FXML
    private Label changeLabel;

    @FXML
    private TextField changeField;

    @FXML
    private TextField remainingBalanceField;

    @FXML
    private Label orderInformationLabel;

    @FXML
    private TextArea orderInformationArea;

    @FXML
    private Button releaseButton;

    ConnectionClass conn;
    double remainingBalance;
    ResultSet rs, rs2;
    HomeController homeController;

    private int selectedJobID;
    ArrayList<ClaimOrderInformation> claimOrderInformationArrayList;

    ObservableList<String> clientNameObservable;
    ArrayList<ClientName> clientName;

    @FXML
    void clientNameSelect(ActionEvent event) {
        int index = claimClientNameCombobox.getSelectionModel().getSelectedIndex();

        if (claimClientNameCombobox.getItems().size() == 0) {
            return;
        } else if (index < 0) {
//            To avoid errors
            loadClaimOrderInfo(0);
            setSelectedJobID(clientName.get(0).getJobID());
        } else {
            loadClaimOrderInfo(claimClientNameCombobox.getSelectionModel().getSelectedIndex());
            setSelectedJobID(clientName.get(index).getJobID());
        }
    }

    @FXML
    void computeButtonClicked(ActionEvent event) {

        double amountPaid = 0.0, change, balance;
        balance = Double.parseDouble(remainingBalanceField.getText());

        try {

            amountPaid = Double.valueOf(amountPaidTextfield.getText());

            if (balance != 0) {
                if (amountPaid < balance) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("Invalid Input");
                    a.setContentText("Please settle your balance first before releasing your order");
                    a.showAndWait();
                } else {
                    change = amountPaid - balance;
                    changeField.setText(Double.toString(change));
                }
            } else {
                if (amountPaid != 0) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("Invalid Input");
                    a.setContentText("Balance is already settled!");
                    a.showAndWait();
                }
            }
        } catch (NumberFormatException e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Invalid Input");
            a.setContentText("Enter amount paid to compute change and remaining balance!");
            a.showAndWait();
        }
    }

    @FXML
    void releaseButtonClicked(ActionEvent event) {
        release();
        clearFields();
        fetchRecord();
        populateNameChoiceBox();
        claimClientNameCombobox.getSelectionModel().select(0);
    }

    @FXML
    void searchButtonClicked(ActionEvent event) {
        fetchRecord();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            conn = new ConnectionClass();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        clientNameObservable = FXCollections.observableArrayList();
        clientName = new ArrayList<>();
        claimOrderInformationArrayList = new ArrayList<>();

        fetchRecord();
        populateNameChoiceBox();
        claimClientNameCombobox.getSelectionModel().select(0);
    }

    private void populateNameChoiceBox() {

        clientName.clear();
        clientNameObservable.clear();
        ClientName.clearNames();

        try {
            Connection connection = conn.getConnection();
            rs = connection.createStatement().executeQuery("SELECT transaction_records.jobID, CONCAT(customer_records.lastname, ', ', customer_records.firstname ) AS name FROM customer_records JOIN transaction_records ON customer_records.customerID =transaction_records.customerID WHERE transaction_records.status='for claiming'");

            while (rs.next()) {
                clientName.add(new ClientName(rs.getInt("jobID"), rs.getString("name")));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        for (int i = 0, j = 1; i < clientName.size(); i++) {
            if (isDuplicate(clientName.get(i).getName())) {
                clientNameObservable.add(clientName.get(i).getName().concat("(" + j + ")"));
                j++;
            } else {
                clientNameObservable.add(clientName.get(i).getName());
            }
        }

        claimClientNameCombobox.setItems(clientNameObservable);

        if (clientNameObservable.size() > 0) {
            loadClaimOrderInfo(0);
        }
    }

    private void fetchRecord() {

        claimOrderInformationArrayList.clear();

        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder);

        String clientName = claimClientNameCombobox.getValue();

        try {
            formatter.format("SELECT transaction_records.jobID, transaction_records.orderDate, product_records.productName, transaction_records.size, transaction_records.quantity, transaction_records.amountDue, transaction_records.amountPaid, transaction_records.balance, employees_record.empFname, employees_record.empLname, useraccounts_records.username, CONCAT(customer_records.lastname, ', ', customer_records.firstname) as clientFullName FROM transaction_records JOIN product_records ON transaction_records.productID=product_records.productID JOIN employees_record ON transaction_records.empID=employees_record.employeeID JOIN useraccounts_records ON transaction_records.userID=useraccounts_records.userID JOIN customer_records ON transaction_records.customerID=customer_records.customerID WHERE transaction_records.status='for claiming'", clientName);
            rs = conn.select(stringBuilder.toString());

            while (rs.next()) {
                claimOrderInformationArrayList.add(new ClaimOrderInformation(rs.getInt("jobID"), rs.getDate("orderDate").toLocalDate(), rs.getString("productName"), rs.getString("size"), rs.getString("quantity"), rs.getString("amountDue"), rs.getDouble("amountPaid"), rs.getDouble("balance"), rs.getString("empFname"), rs.getString("username")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadClaimOrderInfo(int index) {
        orderInformationArea.setText("Job order no.\t\t\t" + claimOrderInformationArrayList.get(index).getJobID());
        orderInformationArea.appendText("\nDate ordered:\t\t\t" + claimOrderInformationArrayList.get(index).getOrderDate());
        orderInformationArea.appendText("\nProduct Description:\t" + claimOrderInformationArrayList.get(index).getProductName());
        orderInformationArea.appendText("\nSize:\t\t\t\t\t" + claimOrderInformationArrayList.get(index).getSize());
        orderInformationArea.appendText("\nQuantity:\t\t\t\t" + claimOrderInformationArrayList.get(index).getQuantity());
        orderInformationArea.appendText("\nAmount Due:\t\t\t" + "₱" + claimOrderInformationArrayList.get(index).getAmountDue());
        orderInformationArea.appendText("\nAmount paid:\t\t\t" + "₱" + claimOrderInformationArrayList.get(index).getAmountPaid());
        orderInformationArea.appendText("\nBalance:\t\t\t\t" + "₱" + claimOrderInformationArrayList.get(index).getBalance());
        orderInformationArea.appendText("\nAssigned:\t\t\t\t" + claimOrderInformationArrayList.get(index).getEmpFName());
        orderInformationArea.appendText("\nReceived by:\t\t\t" + claimOrderInformationArrayList.get(index).getUserName());

        remainingBalanceField.setText(claimOrderInformationArrayList.get(index).getBalance().toString());
    }

    private void clearFields() {
        changeField.clear();
        amountPaidTextfield.clear();
        remainingBalanceField.clear();
    }

    private void release() {
//        Index variable to be able to track the object equivalent of the string. There are 2 arraylists: One contains the ClientName object
//        while the other contains just the name property of that object.
        int index = claimClientNameCombobox.getSelectionModel().getSelectedIndex();
        String lastNameInput = clientName.get(index).getName();
        setSelectedJobID(clientName.get(index).getJobID());

//      Regular expression that splits the name into last name and first name by removing the punctuation.
//      Documentation reference: https://docs.oracle.com/javase/8/docs/api/
        String[] lastName = lastNameInput.split("[\\p{P}{1}]");
        Double amountPaidBefore = 0.0, amountPaidCurrent = 0.0;

        try {
            amountPaidCurrent = Double.parseDouble(amountPaidTextfield.getText());

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Please settle your balance first before releasing your order");
            alert.showAndWait();
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder);

//      use trim method to remove leading whitespace from the split method earlier
        formatter.format("SELECT transaction_records.amountPaid FROM transaction_records JOIN customer_records ON transaction_records.customerID = customer_records.customerID WHERE customer_records.lastname='%s' AND customer_records.firstname='%s' AND transaction_records.status='for claiming' AND transaction_records.jobID=%d", lastName[0].trim(), lastName[1].trim(), getSelectedJobID());

        try {
            rs = conn.select(stringBuilder.toString());

            while (rs.next()) {
                amountPaidBefore = rs.getDouble("amountPaid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Double totalAmountPaid = amountPaidBefore + amountPaidCurrent;

        stringBuilder = new StringBuilder();
        formatter = new Formatter(stringBuilder);
        formatter.format("UPDATE transaction_records JOIN customer_records ON transaction_records.customerID=customer_records.customerID SET transaction_records.status='claimed', transaction_records.amountPaid=%f, transaction_records.balance = 0 WHERE customer_records.lastname='%s' AND customer_records.firstname='%s' AND transaction_records.status='for claiming' AND transaction_records.jobID=%d", totalAmountPaid, lastName[0].trim(), lastName[1].trim(), getSelectedJobID());


        try {
            conn.update(stringBuilder.toString());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        homeController.fillObservableList();

        orderInformationArea.setText("Job successfully released...");
        orderInformationArea.appendText("\nTransaction saved...");
        remainingBalance = 0;
    }

    public void setHomeController(HomeController controller) {
        homeController = controller;
    }

    private void setSelectedJobID(int id) {
        selectedJobID = id;
    }

    private int getSelectedJobID() {
        return selectedJobID;
    }
}
