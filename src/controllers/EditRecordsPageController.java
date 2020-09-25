package controllers;

import connection.ConnectionClass;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import models.Employee_Records;
import models.Notifications;
import models.Products_Record;
import models.userAccounts;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.ResourceBundle;

public class EditRecordsPageController implements Initializable {

    ConnectionClass condb;
    Connection connection;
    ObservableList<String> employeeItems, userItems, productItems ;
    ObservableList<TextField> employeeFields, userFields, productFields;
    
    HomeController controller;

    ObservableList<String> employeeRecordsFilter, userAccountsRecordsFilter, productsRecordsFilter, selectedFilter;
    ArrayList<String> employeeRecordsColumns, userAccountsRecordsColumns, productsRecordsColumns;
    
    private boolean admin;

    @FXML
    private VBox employeeControlsBox;
    
    @FXML
    private VBox userControlsBox;
    
    @FXML
    private VBox productControlsBox;
    
    @FXML
    private RadioButton adminRadioButton;
    
    @FXML
    private Button productsRecordsEditButton;
    
    @FXML
    private Button searchButton;

    @FXML
    private Button userAccountsEditButton;

    @FXML
    private TextField userAccountsFirstnameField;

    @FXML
    private Button employeeUpdateButton;

    @FXML
    private TextField userAccountsLastnameField;

    @FXML
    private TabPane myTabPane;

    @FXML
    private Tab employeeRecordsTab;

    @FXML
    private TextField employeeLastnameField;

    @FXML
    private Label employeeLastnameLabel;

    @FXML
    private TextField employeeFirstnameField;

    @FXML
    private Label employeeFirstnameLabel;

    @FXML
    private TextField employeeMiddlenameField;

    @FXML
    private Label employeeMiddlenameLabel;

    @FXML
    private Label employeeMobileLabel;

    @FXML
    private TextField employeeMobileField;

    @FXML
    private TextField employeeEmailField;

    @FXML
    private Label employeeEmailLabel;

    @FXML
    private Button employeeSaveButton;

    @FXML
    private Button employeeDeleteButton;

    @FXML
    private TableView<Employee_Records> employeeInfoTable;

    @FXML
    private TableColumn<Employee_Records, Integer> userAccountsEmpIdColumn1;

    @FXML
    private TableColumn<Employee_Records, Integer> employeeNumberColumn;

    @FXML
    private TableColumn<Employee_Records, String> employeeLastnameColumn;

    @FXML
    private TableColumn<Employee_Records, String> employeeFirstnameColumn;

    @FXML
    private TableColumn<Employee_Records, String> employeeMIddlenameColumn;

    @FXML
    private TableColumn<Employee_Records, String> employeeMobileColumn;

    @FXML
    private TableColumn<Employee_Records, String> employeeEmailColumn;

    @FXML
    private Tab userAccountsRecordsTab;

    @FXML
    private TextField userAccountsUsernameField;

    @FXML
    private Label userAccountsUsernameLabel;

    @FXML
    private TextField userAccountsPasswordField;

    @FXML
    private Label userAccountsPasswordLabel;

    @FXML
    private Button userAccountsSaveButton;

    @FXML
    private Button userAccountsDeleteButton;

    @FXML
    private ComboBox<String> userAccountSelectEmployeeComboBox;
    
    @FXML
    private ComboBox<String> searchByCombobox;

    @FXML
    private TableView<userAccounts> userAccountInfoTable;

    @FXML
    private TableColumn<userAccounts, Integer> userAccountsNumberColumn;

    @FXML
    private TableColumn<userAccounts, Integer> userAccountsEmpIdColumn;

    @FXML
    private TableColumn<userAccounts, String> userAccountsUsernameColumn;

    @FXML
    private TableColumn<userAccounts, String> userAccountsPasswordColumn;

    @FXML
    private TableColumn<userAccounts, String> userAccountsFirstnameColumn;

    @FXML
    private TableColumn<userAccounts, String> userAccountsLastnameColumn;
    
    @FXML
    private TableColumn<userAccounts, Boolean> userAccountsAdminColumn;

    @FXML
    private Tab productsRecordsTab;

    @FXML
    private TextField productsRecordsNameField;
    
    @FXML
    private TextField searchForField;

    @FXML
    private Label productsRecordsDescriptionLabel;

    @FXML
    private TextField productsRecordsPriceField;

    @FXML
    private Label productsRecordsPriceLabel;

    @FXML
    private Button productsRecordsSaveButton;

    @FXML
    private Button productsRecordsDeleteButton;

    @FXML
    private TableView<Products_Record> productInfoTable;

    @FXML
    private TableColumn<Products_Record, Integer> productRecordsNumberColumn;

    @FXML
    private TableColumn<Products_Record, String> productRecordsDescriptionColumn;

    @FXML
    private TableColumn<Products_Record, Double> productRecordsPriceColumn;
    
    @FXML
    void newItemSelected(ActionEvent event) {
        searchForField.clear();
    } 
    
    @FXML
    void searchButtonClicked(ActionEvent event) throws SQLException, ClassNotFoundException {
        search();
    }
    
    @FXML
    void userAccountSelectionChanged(ActionEvent event) {

        fetchSelectedItemDetails();

    }
    
    @FXML
    void productsRecordsEditButtonClicked(ActionEvent event) {
        
        int id;
        Products_Record editProduct;
        editProduct = productInfoTable.getSelectionModel().getSelectedItem();
        id = editProduct.getProductID();
        
        if (!validateFields()) {
            incompleteFields();
            
        }
        else{
            try {
            connection = condb.getConnection();
            String sql = "UPDATE product_records SET productName= '" + productsRecordsNameField.getText() + "', productPrice= '" + productsRecordsPriceField.getText() + "' WHERE productID = '" + id + "'";
            PreparedStatement prep = connection.prepareStatement(sql);
            prep.execute();
            
    
        } catch (Exception e) {
            unsuccessfull("Error", "The product was not updated successfully");
            e.printStackTrace();
        }
        
        successful("Sucess!", "The product was successfully updated.");
        updateProductsRecord();
        clearFields();
        enableSaving();
        setDefaults();
        }    
    }

    @FXML
    void userAccountsEditButtonClicked(ActionEvent event) {
        int id;
        userAccounts user;
        user = userAccountInfoTable.getSelectionModel().getSelectedItem();
        id = user.getUsernum();
        
        if (!validateFields()) {
            incompleteFields();
            
        }
        
        else{
            try {

            connection = condb.getConnection();
            String sql = "UPDATE useraccounts_records SET username= '" + userAccountsUsernameField.getText() + "', password= '" + userAccountsPasswordField.getText() + "'  WHERE userID = '" + id + "'";
            PreparedStatement prep = connection.prepareStatement(sql);
            prep.execute();
            
        } catch (Exception e) {
            unsuccessfull("Error", "The user update was unsuccessful.");

            e.printStackTrace();
        }
        
        successful("Success", "The user was updated successfully.");
        updateUserAccountTable();
        clearFields();
        enableSaving();
        setDefaults();
        }

        
    }

    @FXML
    void employeeUpdateButtonClick(ActionEvent event) {
        int id;
        Employee_Records employee;
        employee = employeeInfoTable.getSelectionModel().getSelectedItem();

        id = employee.getEmpNum();
        
        if (!validateFields()) {
            incompleteFields();
            
        }
        
        else{
            try {
            connection = condb.getConnection();
            String sql = "UPDATE employees_record SET empLname= '" + employeeLastnameField.getText() + "', empFname= '" + employeeFirstnameField.getText() + "', empMname= '" + employeeMiddlenameField.getText() + "', empMobile= '" + employeeMobileField.getText() + "', empEmail= '" + employeeEmailField.getText() + "' WHERE employeeID = '" + id + "'";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.execute();
            connection.close();

        } catch (Exception e) {
            unsuccessfull("Error", "The employee was not updated successfully.");
        }
        
        successful("Success!", "The employee was updated successfully.");
        updateEmloyeeRecordsTable();
        clearFields();
        enableSaving();
        setDefaults();
        }
        

    }

    @FXML
    void employeeRecordsTableSelected(MouseEvent event) {

        Employee_Records selectRecords;
        selectRecords = employeeInfoTable.getSelectionModel().getSelectedItem();
        
        
        employeeLastnameField.setText(selectRecords.getEmpLname());
        employeeFirstnameField.setText(selectRecords.getEmpFname());
        employeeMiddlenameField.setText(selectRecords.getEmpMname());
        employeeMobileField.setText(selectRecords.getEmpMobile());
        employeeEmailField.setText(selectRecords.getEmpEmail());
        
        enableEditing();
    }

    @FXML
    void productsRecordsTableSelected(MouseEvent event) {
        String str;
        Products_Record product;
        product = productInfoTable.getSelectionModel().getSelectedItem();
        str = String.valueOf(product.getProductPrice());

        productsRecordsNameField.setText(product.getProductName());
        productsRecordsPriceField.setText(str);
        enableEditing();
    }

    @FXML
    void productsRecordsDeleteButtonClicked(ActionEvent event) {

        int num;
        Products_Record deleteProduct;
        deleteProduct = productInfoTable.getSelectionModel().getSelectedItem();

        num = deleteProduct.getProductID();
        
        if (!validateFields()) {
            incompleteFields();
            
        }
        
        else{
            try {
            connection = condb.getConnection();
            String sql = "UPDATE product_records SET status='unavailable' WHERE productID='"+num+"'";
            //"DELETE  FROM product_records WHERE productID = '" + num + "'"
            //"UPDATE product_records SET status='removed' WHERE productID='"+num+"'"
            PreparedStatement prep = connection.prepareStatement(sql);
            prep.execute();
            connection.close();
            
        } catch (Exception e) {
            unsuccessfull("Error", "The product was not deleted successfully");
        }
        
        successful("Success!", "The product was successfully updated.");
        updateProductsRecord();
        clearFields();
        enableSaving();
        setDefaults();
        }
        

    }

    @FXML
    void employeeDeleteButtonClick(ActionEvent event) {
        int id;
        Employee_Records deleteRecord;
        deleteRecord = employeeInfoTable.getSelectionModel().getSelectedItem();

        id = deleteRecord.getEmpNum();
        
        if (!validateFields()) {
            incompleteFields();
            
        }
        
        else{
            
            try {
            connection = condb.getConnection();
            String sql = "UPDATE employees_record SET status='unemployed' WHERE employeeID = '"+id+"'";
            PreparedStatement prep = connection.prepareStatement(sql);
            prep.execute();
        } catch (Exception e) {
            unsuccessfull("Error", "The employee was not removed successfully");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        
        successful("Success!", "The employee was removed successfully.");
        updateEmloyeeRecordsTable();
        clearFields();
        enableSaving();
        setDefaults();
            
        }
        

    }

    @FXML
    void userAccountsTableSelected(MouseEvent event) {

        userAccounts selectedUserAccounts;
        selectedUserAccounts = userAccountInfoTable.getSelectionModel().getSelectedItem();

        userAccountsUsernameField.setText(selectedUserAccounts.getUsername());
        userAccountsPasswordField.setText(selectedUserAccounts.getPassword());
        userAccountsFirstnameField.setText(selectedUserAccounts.getFirstname());
        userAccountsLastnameField.setText(selectedUserAccounts.getLastname());
        enableEditing();
    }

    @FXML
    void userAccountsDeleteButtonClicked(ActionEvent event) {
        int id;
        userAccounts deleteUser;
        deleteUser = userAccountInfoTable.getSelectionModel().getSelectedItem();

        id = deleteUser.getUsernum();
        
        if (!validateFields()) {
            incompleteFields();  
        }
        
        else{
            
            try {
            connection = condb.getConnection();
            String sql ="UPDATE useraccounts_records SET status ='inactive' WHERE userID='"+id+"'";
            //"DELETE  FROM useraccounts_records WHERE userID = '" + id + "'"
            PreparedStatement prep = connection.prepareStatement(sql);
            prep.execute();
        } catch (Exception e) {
            unsuccessfull("Error", "The user was not deleted successfully");
            e.printStackTrace();
        }
        
        successful("Success!", "The user was deleted successfully");
        updateUserAccountTable();
        clearFields();
        enableSaving();
        setDefaults();
            
        }
        
    }

    @FXML
    void productsRecordsSaveButtonClicked(ActionEvent event) {
        
        if (!validateFields()) {
            defaultLabels();
            incompleteFields();  
            
        }
        
        else{
            
            try {
            Products_Record product = new Products_Record();
            product.setProductID(0);
            product.setProductName(productsRecordsNameField.getText());
            product.setProductPrice(Double.parseDouble(productsRecordsPriceField.getText()));

            productInfoTable.getItems().add(product);

            connection = condb.getConnection();
            String sql = "INSERT INTO product_records VALUES('" + product.getProductID() + "','" + product.getProductName() + "','" + product.getProductPrice() + "','available')";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            
            e.printStackTrace();
        }
        successful("Success!", "The new product was saved successfully.");
        updateProductsRecord();
        clearFields();
        setDefaults();
            
        }
    }

    @FXML
    void employeeSaveButtonClick(ActionEvent event) {
        
        if (!validateFields()) {
            defaultLabels();
            incompleteFields();   
        }
        else{
            try {
            Employee_Records employee = new Employee_Records();
            employee.setEmpNum(0);
            employee.setEmpLname(employeeLastnameField.getText());
            employee.setEmpFname(employeeFirstnameField.getText());
            employee.setEmpMname(employeeMiddlenameField.getText());
            employee.setEmpMobile(employeeMobileField.getText());
            employee.setEmpEmail(employeeEmailField.getText());
            employeeInfoTable.getItems().add(employee);

            connection = condb.getConnection();
            String sql = "INSERT INTO employees_record VALUES('" + employee.getEmpNum() + "','" + employee.getEmpLname() + "','" + employee.getEmpFname() + "','" + employee.getEmpMname() + "','" + employee.getEmpMobile() + "','" + employee.getEmpEmail() + "','employed')";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (Exception e) {
           System.out.println(e.getMessage());
            e.printStackTrace();
        }
        updateEmloyeeRecordsTable();
        clearFields();
        fetchSelectedItemDetails();
        setDefaults();
            
        }
    }

    @FXML
    void userAccountsSaveButtonClicked(ActionEvent event) {
        if (!validateFields()) {
            defaultLabels();
            incompleteFields(); 
        }
        
        else{
            try {
                
                boolean admin;
        
        if(adminRadioButton.isSelected()){
            admin=true;
        }
        else{
            admin=false;
            
        }
        
        int value = admin ? 1 : 0;
            userAccounts user = new userAccounts();
            connection=condb.getConnection();
            ResultSet get=connection.createStatement().executeQuery("SELECT employeeID FROM employees_record WHERE CONCAT(employees_record.employeeID,'-',employees_record.empLname) = '" + userAccountSelectEmployeeComboBox.getValue()+"'");
            while(get.next()){
                user.setEmpid(get.getInt("employeeID"));
                System.out.println(get.getInt("employeeID"));

            }

            user.setUsernum(0);

            user.setUsername(userAccountsUsernameField.getText());
            user.setPassword(userAccountsPasswordField.getText());
            user.setFirstname(userAccountsFirstnameField.getText());
            user.setLastname(userAccountsLastnameField.getText());
            userAccountInfoTable.getItems().add(user);

            connection = condb.getConnection();
            String sql = "INSERT INTO useraccounts_records VALUES('" + user.getUsernum() + "','" + user.getEmpid() + "','" + user.getUsername() + "','" + user.getPassword() + "','"+value+"','active')";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            connection.close();

        } catch (Exception e) {
//            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        updateUserAccountTable();
        clearFields();
        setDefaults();
            
        }    
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            condb= new ConnectionClass();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        setDefaults();
        fillObservablelist();
        changeTabs();
        populateEmpIdChoiceBox();
        updateUserAccountTable();
        updateEmloyeeRecordsTable();
        updateProductsRecord();
        populateEmpIdChoiceBox();
        userAccountSelectEmployeeComboBox.getSelectionModel().select(0);
        fetchSelectedItemDetails();
        populateSearchForCombobox();
    }

    //Method that updates the tables from database
    public void updateUserAccountTable() {
        
        ObservableList<userAccounts> user = FXCollections.observableArrayList();
        try {

            connection = condb.getConnection();
            ResultSet rs = connection.createStatement().executeQuery("SELECT useraccounts_records.userID, useraccounts_records.empID,useraccounts_records.username, useraccounts_records.password, employees_record.empFname, employees_record.empLname, useraccounts_records.Admin FROM useraccounts_records JOIN employees_record ON useraccounts_records.empID=employees_record.employeeID WHERE useraccounts_records.status='active'");

            while (rs.next()) {
                user.add(new userAccounts(rs.getInt("userID"), rs.getInt("empID"), rs.getString("username"), rs.getString("password"), rs.getString("empFname"), rs.getString("empLname"), rs.getBoolean("Admin")));
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        userAccountsNumberColumn.setCellValueFactory(new PropertyValueFactory<userAccounts, Integer>("usernum"));
        userAccountsEmpIdColumn.setCellValueFactory(new PropertyValueFactory<userAccounts, Integer>("empid"));
        userAccountsFirstnameColumn.setCellValueFactory(new PropertyValueFactory<userAccounts, String>("firstname"));
        userAccountsLastnameColumn.setCellValueFactory(new PropertyValueFactory<userAccounts, String>("lastname"));
        userAccountsUsernameColumn.setCellValueFactory(new PropertyValueFactory<userAccounts, String>("username"));
        userAccountsPasswordColumn.setCellValueFactory(new PropertyValueFactory<userAccounts, String>("password"));
        userAccountsAdminColumn.setCellValueFactory(new PropertyValueFactory<userAccounts, Boolean>("admin"));
        userAccountInfoTable.setItems(user);

    }

    public void clearFields() {
        userAccountsPasswordField.clear();
        userAccountsUsernameField.clear();
        employeeLastnameField.clear();
        employeeFirstnameField.clear();
        employeeMobileField.clear();
        employeeEmailField.clear();
        employeeMiddlenameField.clear();
        productsRecordsNameField.clear();
        productsRecordsPriceField.clear();
    }

    private void updateEmloyeeRecordsTable() {
        ObservableList<Employee_Records> employee = FXCollections.observableArrayList();

        try {
            Connection connection = condb.getConnection();
            ResultSet rs2 = connection.createStatement().executeQuery("select * from employees_record WHERE status='employed'");

            while (rs2.next()) {
                employee.add(new Employee_Records(rs2.getInt("employeeID"), rs2.getString("empLname"), rs2.getString("empFname"), rs2.getString("empMname"), rs2.getString("empMobile"), rs2.getString("empEmail")));
            }
            connection.close();
        } catch (Exception e) {
           System.out.println(e.getMessage());
            e.printStackTrace();
        }
        employeeLastnameColumn.setCellValueFactory(new PropertyValueFactory<Employee_Records, String>("empLname"));
        employeeFirstnameColumn.setCellValueFactory(new PropertyValueFactory<Employee_Records, String>("empFname"));
        employeeMIddlenameColumn.setCellValueFactory(new PropertyValueFactory<Employee_Records, String>("empMname"));
        employeeNumberColumn.setCellValueFactory(new PropertyValueFactory<Employee_Records, Integer>("empNum"));
        employeeMobileColumn.setCellValueFactory(new PropertyValueFactory<Employee_Records, String>("empMobile"));
        employeeEmailColumn.setCellValueFactory(new PropertyValueFactory<Employee_Records, String>("empEmail"));
        employeeInfoTable.setItems(employee);
    }

    private void updateProductsRecord() {
        ObservableList<Products_Record> product = FXCollections.observableArrayList();

        try {
            connection = condb.getConnection();
            ResultSet rs3 = connection.createStatement().executeQuery("select * from product_records WHERE status='available'");

            while (rs3.next()) {

                product.add(new Products_Record(rs3.getInt("productID"), rs3.getString("productName"), rs3.getDouble("productPrice")));
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        productRecordsNumberColumn.setCellValueFactory(new PropertyValueFactory<Products_Record, Integer>("productID"));
        productRecordsDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Products_Record, String>("productName"));
        productRecordsPriceColumn.setCellValueFactory(new PropertyValueFactory<Products_Record, Double>("productPrice"));
        productInfoTable.setItems(product);
      
    }

    private void populateEmpIdChoiceBox() {

        ObservableList<String> empID = FXCollections.observableArrayList();
        try {
            connection = condb.getConnection();
            ResultSet rs = connection.createStatement().executeQuery("SELECT CONCAT(employees_record.employeeID,'-',employees_record.empLname) AS employee FROM employees_record WHERE employees_record.status='employed'");

            while (rs.next()) {
                empID.add(rs.getString("employee"));
                userAccountSelectEmployeeComboBox.setItems(empID);
            }
            connection.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

    }

    private void fetchSelectedItemDetails() {
        
        String selectedEmployee;
        selectedEmployee = userAccountSelectEmployeeComboBox.getValue();

        try {
             connection = condb.getConnection();
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM employees_record WHERE CONCAT(employees_record.employeeID,'-',employees_record.empLname) = '" + selectedEmployee + "'");

            while (rs.next()) {
                userAccountsLastnameField.setText(rs.getString("empLname"));
                userAccountsFirstnameField.setText(rs.getString("empFname"));

            }

        } catch (Exception ex) {
//            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private void changeTabs(){
        
          myTabPane.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tab> ov, Tab t, Tab t1) -> {
              
              if(t1.equals(productsRecordsTab) ){
                searchByCombobox.setItems(productsRecordsFilter);
//                  searchByCombobox.setItems(productItems);
                  searchByCombobox.getSelectionModel().select(0);
                  setDefaults();
                  updateEmloyeeRecordsTable();
                  updateUserAccountTable();
                  populateEmpIdChoiceBox();
              }
              else if(t1.equals(userAccountsRecordsTab)){
                  searchByCombobox.setItems(userAccountsRecordsFilter);
//                  searchByCombobox.setItems(userItems);
                  searchByCombobox.getSelectionModel().select(0);
                  setDefaults();
                  updateEmloyeeRecordsTable();
                  updateProductsRecord();
              }
              else if(t1.equals(employeeRecordsTab)){
                  searchByCombobox.setItems(employeeRecordsFilter);
//                  searchByCombobox.setItems(employeeItems);
                  searchByCombobox.getSelectionModel().select(0);
                  setDefaults();
                  updateProductsRecord();
                  updateUserAccountTable();
                  populateEmpIdChoiceBox();
              }
              
              searchForField.clear();
          });
    }

    private void populateSearchForCombobox() {
        
        if(employeeRecordsTab.isSelected()){
//            searchByCombobox.setItems(employeeItems);
            searchByCombobox.setItems(employeeRecordsFilter);
            searchByCombobox.getSelectionModel().select(0);
        }
        else if(userAccountsRecordsTab.isSelected()){
//            searchByCombobox.setItems(userItems);
            searchByCombobox.setItems(userAccountsRecordsFilter);
            searchByCombobox.getSelectionModel().select(0);
        }
        else if(productsRecordsTab.isSelected()){
//            searchByCombobox.setItems(productItems);
            searchByCombobox.setItems(productsRecordsFilter);
            searchByCombobox.getSelectionModel().select(0);
        }          
    }

    private void fillObservablelist() {
        selectedFilter = FXCollections.observableArrayList();
        employeeRecordsFilter = FXCollections.observableArrayList("ID", "Last Name", "First Name", "Middle Name");
        userAccountsRecordsFilter = FXCollections.observableArrayList("Username");
        productsRecordsFilter = FXCollections.observableArrayList("Product Name");

        employeeRecordsColumns = new ArrayList<>(Arrays.asList("employeeID", "empLname", "empFname", "empMname"));
        userAccountsRecordsColumns = new ArrayList<>(Arrays.asList("username"));
        productsRecordsColumns = new ArrayList<>(Arrays.asList("productName"));
//
//        searchByCombobox.setItems(employeeRecordsFilter);

//        employeeItems =FXCollections.observableArrayList();
//        userItems=FXCollections.observableArrayList();
//        productItems=FXCollections.observableArrayList();
//
        employeeFields = FXCollections.observableArrayList();
        userFields = FXCollections.observableArrayList();
        productFields = FXCollections.observableArrayList();

        employeeFields.addAll(employeeLastnameField, employeeFirstnameField, employeeMiddlenameField, employeeMobileField, employeeEmailField);
        userFields.addAll(userAccountsFirstnameField, userAccountsLastnameField, userAccountsUsernameField, userAccountsPasswordField);
        productFields.addAll(productsRecordsNameField,  productsRecordsPriceField);
//
//        try {
//            connection=condb.getConnection();
//            ResultSet rs1=connection.createStatement().executeQuery("SELECT column_name AS items FROM information_schema.columns WHERE table_name = 'employees_record' AND table_schema = 'final_project'");
//            ResultSet rs2=connection.createStatement().executeQuery("SELECT column_name AS items FROM information_schema.columns WHERE table_name = 'useraccounts_records' AND table_schema = 'final_project'");
//            ResultSet rs3=connection.createStatement().executeQuery("SELECT column_name AS items FROM information_schema.columns WHERE table_name = 'product_records' AND table_schema = 'final_project'");
//            while(rs1.next()){
//                employeeItems.add(rs1.getString("items"));
//            }
//            while(rs2.next()){
//                userItems.add(rs2.getString("items"));
//            }
//            while(rs3.next()){
//                productItems.add(rs3.getString("items"));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private void search() throws SQLException, ClassNotFoundException{
        
        String filter, searchFor = searchForField.getText();
        int index = 0;
        ObservableList<Employee_Records> employee = FXCollections.observableArrayList();
        ObservableList<Products_Record> product = FXCollections.observableArrayList();
        ObservableList<userAccounts> user = FXCollections.observableArrayList();

        connection = condb.getConnection();

        if (searchFor.length() == 0) {
            if (employeeRecordsTab.isSelected()) {
                updateEmloyeeRecordsTable();
                return;
            } else if (userAccountsRecordsTab.isSelected()) {
                updateUserAccountTable();
                return;
            } else if (productsRecordsTab.isSelected()) {
                updateProductsRecord();
                return;
            }
        }


        if (employeeRecordsTab.isSelected()) {
            index = searchByCombobox.getSelectionModel().getSelectedIndex();
            filter = employeeRecordsColumns.get(index);

            if (filter == "ID") {
                StringBuilder stringBuilder = new StringBuilder();
                Formatter formatter = new Formatter(stringBuilder);

                formatter.format("SELECT * FROM employees_record WHERE employeeID = %d AND status = 'employed'", Integer.valueOf(searchFor));

                ResultSet resultSet = connection.createStatement().executeQuery(stringBuilder.toString());

                while (resultSet.next()) {
                    employee.add(new Employee_Records(resultSet.getInt("employeeID"), resultSet.getString("empLname"), resultSet.getString("empFname"), resultSet.getString("empMname"), resultSet.getString("empMobile"), resultSet.getString("empEmail")));
                }

                employeeLastnameColumn.setCellValueFactory(new PropertyValueFactory<>("empLname"));
                employeeFirstnameColumn.setCellValueFactory(new PropertyValueFactory<>("empFname"));
                employeeMIddlenameColumn.setCellValueFactory(new PropertyValueFactory<>("empMname"));
                employeeNumberColumn.setCellValueFactory(new PropertyValueFactory<>("empNum"));
                employeeMobileColumn.setCellValueFactory(new PropertyValueFactory<>("empMobile"));
                employeeEmailColumn.setCellValueFactory(new PropertyValueFactory<>("empEmail"));
                employeeInfoTable.setItems(employee);

                return;
            }

            ResultSet rs= connection.createStatement().executeQuery("SELECT * FROM employees_record WHERE "+filter+" = '"+searchFor+"'");
        
            while (rs.next()) {
                employee.add(new Employee_Records(rs.getInt("employeeID"), rs.getString("empLname"), rs.getString("empFname"), rs.getString("empMname"), rs.getString("empMobile"), rs.getString("empEmail")));
            }
        
            employeeLastnameColumn.setCellValueFactory(new PropertyValueFactory<>("empLname"));
            employeeFirstnameColumn.setCellValueFactory(new PropertyValueFactory<>("empFname"));
            employeeMIddlenameColumn.setCellValueFactory(new PropertyValueFactory<>("empMname"));
            employeeNumberColumn.setCellValueFactory(new PropertyValueFactory<>("empNum"));
            employeeMobileColumn.setCellValueFactory(new PropertyValueFactory<>("empMobile"));
            employeeEmailColumn.setCellValueFactory(new PropertyValueFactory<>("empEmail"));
            employeeInfoTable.setItems(employee);
            
        } else if (userAccountsRecordsTab.isSelected()) {
            index = searchByCombobox.getSelectionModel().getSelectedIndex();
            filter = userAccountsRecordsColumns.get(index);
                    
            ResultSet rs = connection.createStatement().executeQuery("SELECT useraccounts_records.userID, useraccounts_records.empID,useraccounts_records.username, useraccounts_records.password, useraccounts_records.Admin, employees_record.empFname, employees_record.empLname FROM useraccounts_records JOIN employees_record ON useraccounts_records.empID=employees_record.employeeID WHERE "+filter+"='"+searchFor+"'");

            while (rs.next()) {
                user.add(new userAccounts(rs.getInt("userID"), rs.getInt("empID"), rs.getString("username"), rs.getString("password"), rs.getString("empFname"), rs.getString("empLname"), rs.getBoolean("Admin")));
            }
            
            userAccountsNumberColumn.setCellValueFactory(new PropertyValueFactory<>("usernum"));
            userAccountsEmpIdColumn.setCellValueFactory(new PropertyValueFactory<>("empid"));
            userAccountsFirstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
            userAccountsLastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
            userAccountsUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
            userAccountsPasswordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
            userAccountsAdminColumn.setCellValueFactory(new PropertyValueFactory<>("admin"));
            userAccountInfoTable.setItems(user);
        } else if(productsRecordsTab.isSelected()){
            index = searchByCombobox.getSelectionModel().getSelectedIndex();
            filter = productsRecordsColumns.get(index);
            
            ResultSet rs3 = connection.createStatement().executeQuery("select * from product_records WHERE "+filter+"='"+searchFor+"'");

            while (rs3.next()) {
                product.add(new Products_Record(rs3.getInt("productID"), rs3.getString("productName"), rs3.getDouble("productPrice")));
            }
            
            productRecordsNumberColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
            productRecordsDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
            productRecordsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
            productInfoTable.setItems(product);
        }     
        } 
    
    //Method that enables the saving button and disables the editing buttons(update and delete button).
    private void enableSaving(){
        
        if (employeeRecordsTab.isSelected()) {
                employeeUpdateButton.setDisable(true);
                employeeDeleteButton.setDisable(true);
                employeeSaveButton.setDisable(false);
        }
        else if (userAccountsRecordsTab.isSelected()) {
                userAccountsDeleteButton.setDisable(true);
                userAccountsEditButton.setDisable(true);
                userAccountsSaveButton.setDisable(false);

        }
        else if (productsRecordsTab.isSelected()) { 
                productsRecordsDeleteButton.setDisable(true);
                productsRecordsEditButton.setDisable(true);
        }       productsRecordsSaveButton.setDisable(false);
    }
    
    //Method that enables the editing buttons(upadate and delete) and disables the saving button.
    private void enableEditing(){
        
        if (employeeRecordsTab.isSelected()) {
                employeeSaveButton.setDisable(true);
                employeeUpdateButton.setDisable(false);
                employeeDeleteButton.setDisable(false);  
                
        }
        else if (userAccountsRecordsTab.isSelected()) {
                userAccountsSaveButton.setDisable(true);
                userAccountsDeleteButton.setDisable(false);
                userAccountsEditButton.setDisable(false);
          
        }
        else if (productsRecordsTab.isSelected()) { 
            productsRecordsSaveButton.setDisable(true);
                productsRecordsDeleteButton.setDisable(false);
                productsRecordsEditButton.setDisable(false);
                
        }  
    }
    
    //Method that resets the components to the default values.
    private void setDefaults(){
            
            defaultLabels();
            
            if(adminRadioButton.isSelected()){
                adminRadioButton.setSelected(false);  
            }
            employeeUpdateButton.setDisable(true);
            employeeDeleteButton.setDisable(true);
            employeeSaveButton.setDisable(false);
            userAccountsDeleteButton.setDisable(true);
            userAccountsEditButton.setDisable(true);
            userAccountsSaveButton.setDisable(false);
            productsRecordsDeleteButton.setDisable(true);
            productsRecordsEditButton.setDisable(true);
            productsRecordsSaveButton.setDisable(false);
            clearFields();
            
    }
    
    private void defaultLabels(){
        
            employeeLastnameLabel.setText("Lastname");
            employeeFirstnameLabel.setText("Firstname");
            employeeMiddlenameLabel.setText("Middlename");
            employeeMobileLabel.setText("Mobile number");
            employeeEmailLabel.setText("Email Address");
            
            employeeLastnameLabel.setStyle("-fx-text-fill: #000000");
            employeeFirstnameLabel.setStyle("-fx-text-fill: black");
            employeeMiddlenameLabel.setStyle("-fx-text-fill: black");
            employeeMobileLabel.setStyle("-fx-text-fill: black");
            employeeEmailLabel.setStyle("-fx-text-fill: black");
            
            userAccountsUsernameLabel.setText("Username");
            userAccountsPasswordLabel.setText("Password");
            
            userAccountsUsernameLabel.setStyle("-fx-text-fill: black");
            userAccountsPasswordLabel.setStyle("-fx-text-fill: black");
            
            productsRecordsDescriptionLabel.setText("Product name");
            productsRecordsPriceLabel.setText("Price");
            
            productsRecordsDescriptionLabel.setStyle("-fx-text-fill: black");
            productsRecordsPriceLabel.setStyle("-fx-text-fill: black");   
    }
    
    public void successful(String header, String message){
        Notifications success=new Notifications(header, message );
        success.showInformation();    
    }
    
    public void unsuccessfull(String header, String message){
        Notifications error=new Notifications(header, message );
        error.showError();
    }
    
    public boolean validateFields(){
        
        boolean valid = true;
        
        if(employeeRecordsTab.isSelected()){
            for(TextField x: employeeFields){
                if(x.getText().isEmpty()){
                    valid=false;  
                }
            }    
        }
            
        else if(userAccountsRecordsTab.isSelected()){
            for(TextField y: userFields){
                if(y.getText().isEmpty()){
                 valid=false; 
                }
            }
        } 
            
        else if(productsRecordsTab.isSelected()){
            for(TextField z: productFields){
                if(z.getText().isEmpty()){
                    valid=false; 
                }
            }    
        }
        return valid; 
    } 
   
    public void incompleteFields(){
        
        if(employeeRecordsTab.isSelected()){
            if (employeeFirstnameField.getText().isEmpty()) {
                employeeFirstnameLabel.setStyle("-fx-text-fill: red");
                employeeFirstnameLabel.setText("Fill up this field.");
            }
            if (employeeLastnameField.getText().isEmpty()) {
                employeeLastnameLabel.setStyle("-fx-text-fill: red");
                employeeLastnameLabel.setText("Fill up this field.");   
            }
            if (employeeMiddlenameField.getText().isEmpty()) {
                employeeMiddlenameLabel.setStyle("-fx-text-fill: red");
                employeeMiddlenameLabel.setText("Fill up this field.");   
            }
            if (employeeMobileField.getText().isEmpty()) {
                employeeMobileLabel.setStyle("-fx-text-fill: red");
                employeeMobileLabel.setText("Fill up this field.");   
            }
            if (employeeEmailField.getText().isEmpty()) {
                employeeEmailLabel.setStyle("-fx-text-fill: red");
                employeeEmailLabel.setText("Fill up this field.");   
            }
   
        }
            
        else if(userAccountsRecordsTab.isSelected()){
            
            if (userAccountsUsernameField.getText().isEmpty()) {
                userAccountsUsernameLabel.setStyle("-fx-text-fill: red");
                userAccountsUsernameLabel.setText("Fill up this field.");     
            }
            if (userAccountsPasswordField.getText().isEmpty()) {
                userAccountsPasswordLabel.setStyle("-fx-text-fill: red");
                userAccountsPasswordLabel.setText("Fill up this field.");     
            }

        } 
            
        else if(productsRecordsTab.isSelected()){
            if (productsRecordsNameField.getText().isEmpty()) {
                productsRecordsDescriptionLabel.setStyle("-fx-text-fill: red");
                productsRecordsDescriptionLabel.setText("Fill up this field.");     
            }
            if (productsRecordsPriceField.getText().isEmpty()) {
                productsRecordsPriceLabel.setStyle("-fx-text-fill: red");
                productsRecordsPriceLabel.setText("Fill up this field.");     
            }
        }   
    }
    
    public void setHomeController(HomeController control) {
        controller = control;
    }
    
    public void setAdmin(boolean admin){
        this.admin=admin;
        setAdministrativeActions();
    }
    
    public boolean getAdmin(){
        return admin;
    }
    
    public void setAdministrativeActions(){
        
        if(!getAdmin()){
            employeeControlsBox.setDisable(true);
            userControlsBox.setDisable(true);
            productControlsBox.setDisable(true);
        } 
    }
}
