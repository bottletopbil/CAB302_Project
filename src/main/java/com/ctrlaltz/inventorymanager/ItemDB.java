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
                            + "itemBrand VARCHAR,"
                            + "itemPrice FLOAT,"
                            + "itemWarranty VARCHAR,"
                            + "itemQuantity INTEGER NOT NULL,"
                            + "itemCondition VARCHAR,"
                            + "photoStr BLOB,"
                            + "itemDesc VARCHAR,"
                            + "datePurchased DATETIME,"
                            + "dateRegistered DATETIME,"
                            + "FOREIGN KEY (groupId) REFERENCES ItemGroups(id),"
                            + "FOREIGN KEY (ownerId) REFERENCES Users(id)"
                            + ")"
            );
        } catch (SQLException ex) {
            System.err.println("ItemDB Error: " + ex);
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
        //UPDATE Items SET groupId = ?, ownerId = ?, itemName = ?, itemBrand = ?, itemPrice = ?, itemWarranty = ?, itemQuantity = ?, itemCondition = ?, photoStr = ?, itemDesc = ?, datePurchased = ?, dateRegistered = ? WHERE  id = ?
        try {
            PreparedStatement updateItem = connection.prepareStatement(
                "UPDATE Items SET groupId = ?, ownerId = ?, itemName = ?, itemBrand = ?, itemPrice = ?, itemWarranty = ?, itemQuantity = ?, itemCondition = ?, photoStr = ?, itemDesc = ?, datePurchased = ?, dateRegistered = ? WHERE id = ?"
            );
            updateItem.setInt(1, item.getGroupId());
            updateItem.setInt(2, item.getOwnerId());
            updateItem.setString(3, item.getName());
            updateItem.setString(4, item.getBrand());
            updateItem.setFloat(5, item.getPrice());
            updateItem.setString(6, item.getWarranty());
            updateItem.setInt(7, item.getQuantity());
            updateItem.setString(8, item.getCondition());
            updateItem.setString(9, item.getPhotoAsString());
            updateItem.setString(10, item.getItemDesc());
            updateItem.setString(11, item.getDatePurchased());
            updateItem.setString(12, item.getDateRegistered());
            updateItem.setInt(13, item.getId());
            updateItem.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public void delete (int id) {
        try {
            PreparedStatement deleteItem = connection.prepareStatement(
              "DELETE FROM Items WHERE id = ?"
            );
            deleteItem.setInt(1, id);
            deleteItem.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
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

    public List<Item> getItemsByID(Integer userId, Integer roomId) {
        List<Item> Items = new ArrayList<>();
        try {
            PreparedStatement getItems = connection.prepareStatement("SELECT * FROM Items WHERE ownerId = " + userId + " AND groupId = " + roomId);
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
        return new ArrayList<>();
    }

    public List<Item> getItemsByID(Integer userId) {
        List<Item> Items = new ArrayList<>();
        try {
            PreparedStatement getItems = connection.prepareStatement("SELECT * FROM Items WHERE ownerId = " + userId);
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
        return new ArrayList<>();
    }

    public List<Float> GetItemPrices(Integer userId)
    {
        List<Float> prices = new ArrayList<>();
        try {
            PreparedStatement getItems = connection.prepareStatement("SELECT * FROM Items WHERE ownerId = " + userId);
            ResultSet rs = getItems.executeQuery();
            while (rs.next()) {
                prices.add(rs.getFloat("itemPrice"));
            }
            return prices;
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return new ArrayList<>();
    }

    /**
     * Function to get Item by id
     * @param id Item id
     * @return Item object with matching ID
     */
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

    /**
     * Function to get all Items that shares the same Room ID
     * @param id - ID of the Room that all items are contained in
     * @return - List of Item that matches the search parameter
     */
    public List<Item> getByGroupId(int id) {
        List<Item> Items = new ArrayList<>();
        try {
            PreparedStatement getItems = connection.prepareStatement("SELECT * FROM Items WHERE groupId = ?");
            getItems.setInt(1, id);
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
    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
}
