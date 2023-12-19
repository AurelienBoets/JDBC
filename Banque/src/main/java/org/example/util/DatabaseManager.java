package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private final String URI = "jdbc:mysql://localhost:3306/bank_jdbc";
    private final String USER = "root";
    private final String PASSWORD = "admin";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URI,USER,PASSWORD);
    }
}
