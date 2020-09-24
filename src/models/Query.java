
package models;

import connection.ConnectionClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;



public class Query{
    private String sql;
    ConnectionClass condb;

    public Query(String sql){
        this.sql=sql;
    }

    public String getSql() {
        return sql;
    }


    public void update(){
        try {
            Connection connection=condb.getConnection();
            String update=this.getSql();
            PreparedStatement ps=connection.prepareStatement(update);
            ps.execute();
            connection.close();
        } catch (Exception ex) {
//            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void delete(){
        try {
            Connection connection=condb.getConnection();
            String update=this.getSql();
            PreparedStatement ps=connection.prepareStatement(update);
            ps.execute();
            connection.close();
        } catch (Exception ex) {
//            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void insert(){
        try {
            Connection connection=condb.getConnection();
            String insert=this.getSql();
            Statement statement=connection.createStatement();
            statement.executeUpdate(insert);
            connection.close();
        } catch (Exception ex) {
//            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public ResultSet select(){
        ResultSet rs = null;
        try {
            Connection connection=condb.getConnection();
            rs=connection.createStatement().executeQuery(getSql());
            connection.close();
        } catch (Exception ex) {
//            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return rs;
    }
}
