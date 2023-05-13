package guitarshopmanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {
    public static Connection getJDBCConnection() throws SQLException {
        final String url = "jdbc:mysql://127.0.0.1:3306/guitarshop";
        final String user = "root";
        final String password = "01082003";
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println(url);
            return DriverManager.getConnection(url, user, password);
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}