package org.example;

import java.sql.*;

public class Database {
    public static int insertStudent(String lastName, String firstName, int numberClass) {
        Connection connection = null;
        int nbRows = 0;
        try {
            connection = ConnectionUtils.getMySQLConnection();
            String query = "INSERT INTO student (firstname,lastname,classnumber) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, numberClass);
            nbRows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return nbRows;
    }

    public static int insertStudent(String lastName, String firstName, int numberClass, String diplomaDate) {
        Connection connection = null;
        int nbRows = 0;
        try {
            connection = ConnectionUtils.getMySQLConnection();
            String query = "INSERT INTO student (firstname,lastname,classnumber,diploma_date) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, numberClass);
            preparedStatement.setDate(4, Date.valueOf(diplomaDate));
            nbRows = preparedStatement.executeUpdate();
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return nbRows;
    }

    public static void getAllStudents() {
        Connection connection = null;
        try {
            connection = ConnectionUtils.getMySQLConnection();
            String query = "SELECT * FROM student";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                System.out.println(resultSet.getInt("id")+": "
                        +resultSet.getString("lastname")+" "
                        +resultSet.getString("firstname")+" "
                        +resultSet.getInt("classnumber")+" "
                        +resultSet.getDate("diploma_date"));
            }
            connection.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int deleteStudentById(int id){
        Connection connection=null;
        int nbrows=0;
        try {
            connection=ConnectionUtils.getMySQLConnection();
            String query="DELETE FROM student WHERE id=?";
            PreparedStatement preparedStatement= connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            nbrows=preparedStatement.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            if (connection != null) {
                try {
                    connection.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return nbrows;
    }

    public static ResultSet getStudentsByClass(int numberClass){
        Connection connection=null;
        ResultSet resultSet=null;
        try {
            connection=ConnectionUtils.getMySQLConnection();
            String query="SELECT * FROM student WHERE classnumber=?";
            PreparedStatement preparedStatement= connection.prepareStatement(query);
            preparedStatement.setInt(1,numberClass);
            resultSet= preparedStatement.executeQuery();
            while(resultSet.next()){
                System.out.println(resultSet.getInt("id")+": "
                        +resultSet.getString("lastname")+" "
                        +resultSet.getString("firstname")+" "
                        +resultSet.getInt("classnumber")+" "
                        +resultSet.getDate("diploma_date"));
            }
            preparedStatement.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            if(connection!=null){
                try {
                    connection.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
                }
            }
        return resultSet;
    }
    }
