package org.example;

import org.example.utils.DatabaseManager;
import org.example.utils.Ihm;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Ihm ihm = new Ihm();
        ihm.start();
        DatabaseManager.closeConnection();
    }
}