package Core.Util;

import javax.swing.*;
import java.sql.*;

public class DataBaseCon {
    private Connection con=null;
    public DataBaseCon() throws SQLException {
        //Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://73.212.150.220:3306/rcon","RCON","RCON");
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
    public byte[][] getServerinfo(String serverName){
        byte[][] info = new byte[2][];
        try {
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM servers WHERE name='"+serverName+"'");
            result.next();
            info[0]=result.getBytes("ip");
            info[1]=result.getBytes("pass");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return info;
    }
}
