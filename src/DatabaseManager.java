/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Seif
 */
public class DatabaseManager {
     public static Connection DBConnection() {
        
         Connection conn = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");  
             //cmd :SQLPLUS / AS SYSDBA
             // ALTER USER HR ACCOUNT UNLOCK;
            conn = DriverManager.getConnection("jdbc:oracle:thin:DB/1412@localhost:1521/XE");

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
     
        return conn;
    
}
     public static void insertScore(String name, int score, String difficulty) {
        try (Connection conn = DriverManager.getConnection("jdbc:oracle:thin:DB/1412@localhost:1521/XE")) {
            String sql = "INSERT INTO car (name, score, difficulty) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setInt(2, score);
            stmt.setString(3, difficulty);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
