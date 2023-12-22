package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public  abstract class DatabaseManager {
    private static final String URI = "jdbc:mysql://localhost:3306/bdd_voiture";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static Connection connection;



    private DatabaseManager(){

    }

    public static Connection getConnection()  {
        if (connection == null){
            try {
                connection = DriverManager.getConnection(URI,USER,PASSWORD);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeConnection(){
        if (connection != null){
            try {
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
            finally {
                connection = null;
            }
        }
    }
}
