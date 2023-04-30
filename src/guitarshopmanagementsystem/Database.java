/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guitarshopmanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author quoca
 */
public class Database {
    public void mySQLconnect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/guitarshop";
            Connection con = DriverManager.getConnection(url,"root","01082003");
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
    }
}
