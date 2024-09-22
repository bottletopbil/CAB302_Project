package com.ctrlaltz.inventorymanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    /**
     * The singleton instance of the database connection.
     */
    private static Connection instance = null;

    private DatabaseConnection() {
        String url = "jdbc:sqlite:database.db"; // Replace with your actual database URL
        try {
            instance = DriverManager.getConnection(url);
            System.out.println("Database connection established.");
        } catch (SQLException sqlEx) {
            System.err.println("Error connecting to the database: " + sqlEx.getMessage());
        }
    }
    /*
     * Provides global access to the singleton instance of the database connection.
     * @return a handle to the singleton instance of the database connection.
     */
    public static Connection getInstance() {
        if (instance == null) {
            new DatabaseConnection();
        }
        return instance;
    }
}
