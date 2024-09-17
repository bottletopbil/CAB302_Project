package com.example.addressbook;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDB {
    private Connection connection;

    public ItemDB() {
        connection = DatabaseConnection.getInstance();
    }

    public void initializeTable() {
        try {
            Statement createTable = connection.createStatement();
            createTable.execute(
                    "CREATE TABLE IF NOT EXISTS Item ("
                            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + "groupId INTEGER NOT NULL,"
                            + "ownerId INTEGER NOT NULL,"
                            + "itemName VARCHAR NOT NULL,"
                            + "itemDesc VARCHAR NOT NULL,"
                            + "itemPrice FLOAT NOT NULL,"
                            + "datePurchased DATETIME NOT NULL,"
                            + "dateRegistered DATETIME NOT NULL,"
                            + "FOREIGN KEY (groupId) REFERENCES ItemGroups(id),"
                            + "FOREIGN KEY (ownerId) REFERENCES Users(id)"
                            + ")"
            );
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public void insert(Item item) {
        try {
            PreparedStatement insertItem = connection.prepareStatement(
                    "INSERT INTO Item (groupId, ownerId, itemName, itemDesc, itemPrice, datePurchased, dateRegistered) VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            insertItem.setInt(1, item.getGroupId());
            insertItem.setInt(2, item.getOwnerId());
            insertItem.setString(3, item.getItemName());
            insertItem.setString(4, item.getItemDesc());
            insertItem.setFloat(5, item.getItemPrice());
            insertItem.setString(6, item.getDatePurchased());
            insertItem.setString(7, item.getDateRegistered());
            insertItem.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public void update(Item item) {

    }

    public void delete (int id) {

    }

    public List<Item> getAll() {
        List<Item> Item = new ArrayList<>();
        return Item;
    }

    public Item getById(int id) {
        return null;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
}
