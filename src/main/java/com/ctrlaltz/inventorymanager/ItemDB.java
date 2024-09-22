package com.ctrlaltz.inventorymanager;

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
                    "CREATE TABLE IF NOT EXISTS Items ("
                            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + "groupId INTEGER NOT NULL,"
                            + "ownerId INTEGER NOT NULL,"
                            + "itemName VARCHAR NOT NULL,"
                            + "itemBrand VARCHAR NOT NULL,"
                            + "itemPrice FLOAT NOT NULL,"
                            + "itemWarranty VARCHAR NOT NULL,"
                            + "itemQuantity INTEGER NOT NULL"
                            + "itemCondition VARCHAR NOT NULL,"
                            + "photoStr BLOB NOT NULL,"
                            + "itemDesc VARCHAR NOT NULL,"
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
                    "INSERT INTO Items (groupId, ownerId, itemName, itemBrand, itemPrice, itemWarranty, itemQuantity, itemCondition, photoStr, itemDesc, datePurchased, dateRegistered) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
            insertItem.setInt(1, item.getGroupId());
            insertItem.setInt(2, item.getOwnerId());
            insertItem.setString(3, item.getName());
            insertItem.setString(4, item.getBrand());
            insertItem.setFloat(5, item.getPrice());
            insertItem.setString(6, item.getWarranty());
            insertItem.setInt(7, item.getQuantity());
            insertItem.setString(8, item.getCondition());
            insertItem.setString(9, item.getPhotoAsString());
            insertItem.setString(10, item.getItemDesc());
            insertItem.setString(11, item.getDatePurchased());
            insertItem.setString(12, item.getDateRegistered());
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
        List<Item> Items = new ArrayList<>();
        try {
            PreparedStatement getItems = connection.prepareStatement("SELECT * FROM Items");
            ResultSet rs = getItems.executeQuery();
            while (rs.next()) {
                Items.add(new Item(
                        rs.getInt("id"),
                        rs.getInt("groupId"),
                        rs.getInt("ownerId"),
                        rs.getString("itemName"),
                        rs.getString("itemBrand"),
                        rs.getFloat("itemPrice"),
                        rs.getString("itemWarranty"),
                        rs.getInt("itemQuantity"),
                        rs.getString("itemCondition"),
                        rs.getString("photoStr"),
                        rs.getString("itemDesc"),
                        rs.getString("datePurchased"),
                        rs.getString("dateRegistered")
                ));
            }
            return Items;
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return null;
    }

    public Item getById(int id) {
        try {
            PreparedStatement getItem = connection.prepareStatement("SELECT * FROM Items WHERE id = ?");
            getItem.setInt(1, id);
            ResultSet rs = getItem.executeQuery();
            if (rs.next()) {
                return new Item(
                        rs.getInt("id"),
                        rs.getInt("groupId"),
                        rs.getInt("ownerId"),
                        rs.getString("itemName"),
                        rs.getString("itemBrand"),
                        rs.getFloat("itemPrice"),
                        rs.getString("itemWarranty"),
                        rs.getInt("itemQuantity"),
                        rs.getString("itemCondition"),
                        rs.getString("photoStr"),
                        rs.getString("itemDesc"),
                        rs.getString("datePurchased"),
                        rs.getString("dateRegistered")
                );
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
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
