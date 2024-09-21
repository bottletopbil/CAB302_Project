package com.example.addressbook;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ItemGroupDB {
    private Connection connection;
    public ItemGroupDB() {
        connection = DatabaseConnection.getInstance();
    }

    public void initializeTable() {
        try {
            Statement createTable = connection.createStatement();
            createTable.execute(
                    "CREATE TABLE IF NOT EXISTS ItemGroups ("
                            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + "groupName VARCHAR NOT NULL,"
                            + "dateCreated DATETIME NOT NULL,"
                            + "ownerId INTEGER NOT NULL,"
                            + "FOREIGN KEY (ownerId) REFERENCES Users(id)"
                            + ")"
            );
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
}
