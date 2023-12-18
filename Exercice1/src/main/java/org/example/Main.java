package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
//        Database.insertStudent("Boets","Aurélien",5,"2021-08-15");
//        Database.getAllStudents();
        System.out.println(Database.deleteStudentById(1));
    }
}