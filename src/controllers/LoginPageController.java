
package controllers;

import connection.ConnectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import models.Notifications;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Formatter;
import java.util.ResourceBundle;


public class LoginPageController implements Initializable {
    
    ConnectionClass connectionClass;
    ResultSet resultSet;
    
    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    void loginButtonClicked(ActionEvent event) {

        String username = usernameField.getText();
        String password = passwordField.getText();
        StringBuilder sb = new StringBuilder();
        Formatter fm = new Formatter(sb);
        fm.format("SELECT * FROM useraccounts_records WHERE username='%s' and password='%s' and status='active'", username, password);
        try {

            resultSet = connectionClass.select(sb.toString());

            if (resultSet.next()) {
                FXMLLoader loader=new FXMLLoader();
                loader.setLocation(getClass().getResource("/views/home.fxml"));
                Parent home = loader.load();
                Scene scene2=new Scene(home);

                Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene2);

                HomeController controller=loader.getController();
                controller.setActiveUser(getLoggedinUser());
//                close database connection
                connectionClass.close();
                window.setMaximized(true);
                window.setResizable(true);
                window.show();
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Invalid credentials");
                a.setContentText("Invalid username and/or password. Please try again");
                a.showAndWait();
                usernameField.clear();
                passwordField.clear();
            }
        } catch (SQLException e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("SQL error");
            a.setContentText("Please make sure database is online before logging in.");
            a.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
//            if the database is offline, connectionClass will not be initialized and usage of the class will throw a
//            null pointer exception
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Communication error");
            a.setContentText("Please make sure database is online before logging in.");
            a.showAndWait();
        }

    }

    @FXML
    void onKeyPressed(KeyEvent event) {
//changes start here
        if (event.getCode() == KeyCode.ENTER) {
            String username = usernameField.getText();
            String password = passwordField.getText();
            StringBuilder sb = new StringBuilder();
            Formatter fm = new Formatter(sb);
            fm.format("SELECT * FROM useraccounts_records WHERE username='%s' and password='%s'and status='active'", username, password);
            try {

                resultSet = connectionClass.select(sb.toString());

                if (resultSet.next()) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/views/home.fxml"));
                    Parent home = loader.load();
                    Scene scene2 = new Scene(home);

                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(scene2);

                    HomeController controller = loader.getController();
                    controller.setActiveUser(getLoggedinUser());

//                close database connection
                    connectionClass.close();

                    window.setMaximized(true);
                    window.setResizable(true);
                    window.show();
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("Invalid credentials");
                    a.setContentText("Invalid username and/or password. Please try again");
                    a.showAndWait();
                    usernameField.clear();
                    passwordField.clear();
                }
            } catch (SQLException e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("SQL error");
                a.setContentText("Please make sure database is online before logging in.");
                a.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
//            if the database is offline, connectionClass will not be initialized and usage of the class will throw a
//            null pointer exception
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Communication error");
                a.setContentText("Please make sure database is online before logging in.");
                a.showAndWait();
            }
        } else {
            return;
        }
    }
    
    public String getLoggedinUser(){
        
        String userFullname = "";
        String username = usernameField.getText();
        String password = passwordField.getText();

        StringBuilder sb = new StringBuilder();
        Formatter fm = new Formatter(sb);
        fm.format("SELECT CONCAT(employees_record.empFname,' ',employees_record.empLname) AS fullname FROM employees_record JOIN useraccounts_records ON employees_record.employeeID=useraccounts_records.empID WHERE useraccounts_records.username='%s'AND useraccounts_records.password='%s'", username, password);
        try {

            resultSet = connectionClass.select(sb.toString());

            while (resultSet.next()) {
                userFullname=resultSet.getString("fullname");

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return  userFullname;
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            connectionClass = new ConnectionClass();
        } catch (SQLException e) {
//            TODO: load alert after login page has initialized
//            guide: https://stackoverflow.com/questions/52693199/check-if-a-stage-is-already-open-before-open-it-again-javafx
             Notifications connectionUnsuccessful = new Notifications("Connection Error", "The application cannot connect to the database");
             connectionUnsuccessful.showError();     
        }
    }    
    
}
