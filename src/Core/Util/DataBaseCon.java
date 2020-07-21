package Core.Util;

import javax.swing.*;
import java.sql.*;

public class DataBaseCon {
    private Connection con=null;
    public DataBaseCon() throws SQLException {
        //Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("","RCON","RCON");
    }
    public String getAuth(String user) throws SQLException {
        String[] out = new String[0];
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM users WHERE user='"+user+"'");
            result.next();
        return result.getString("pass");

    }
    public int getPower(String user) throws SQLException {
        Statement statement = con.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM users WHERE user='"+user+"'");
        result.next();
        return result.getInt("power");
    }

    public void RegisterUser(String user, String pass) throws SQLException {
        PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO `users`(`user`,`pass`)VALUES (?,?)");
        preparedStatement.setString(1,user);
        preparedStatement.setString(2,pass);
        preparedStatement.executeUpdate();
    }
}
