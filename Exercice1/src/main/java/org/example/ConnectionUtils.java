package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionUtils {
    public static Connection getMySQLConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/jdbc_1";
        String userName = "root";
        String password = "admin";
        return DriverManager.getConnection(url, userName, password);
    }
}
