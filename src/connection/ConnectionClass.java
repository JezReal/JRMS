
package connection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Order_Summary;

import java.sql.*;


public class ConnectionClass {

    private Connection connection;
    private String username;
    private String password;

    public Connection getConnection() throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/final_project?useTimezone=true&serverTimezone=UTC", "root", "");
        return connection;
    }

    public ConnectionClass() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/final_project?useTimezone=true&serverTimezone=UTC";
        String username = "root";
        String password = "";

        connection = DriverManager.getConnection(url, username, password);
    }

    public ConnectionClass(String username, String password) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/final_project?useTimezone=true&serverTimezone=UTC";
        this.username = username;
        this.password = password;

        connection = DriverManager.getConnection(url, this.username, this.password);
    }

    public ObservableList<Order_Summary> selectToList(String query) throws SQLException {
        ObservableList<Order_Summary> res = FXCollections.observableArrayList();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            res.add(new Order_Summary(resultSet.getInt("jobID"), resultSet.getString("firstname"), resultSet.getString("lastname"), resultSet.getString("phone"), resultSet.getString("email"), resultSet.getString("productName"), resultSet.getString("size"), resultSet.getInt("quantity"), resultSet.getDate("orderDate").toLocalDate(), resultSet.getDate("dateClaim").toLocalDate(), resultSet.getDouble("amountDue"), resultSet.getDouble("amountPaid"), resultSet.getDouble("balance"), resultSet.getString("empLname"), resultSet.getString("username"), resultSet.getString("status")));
        }
        return res;
    }

    public ResultSet select(String query) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        return rs;
    }

    public void insert(String query) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
    }


    public void delete(String query) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
    }

    public void update(String query) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
    }

    public void close() throws SQLException {
        connection.close();
    }

}
