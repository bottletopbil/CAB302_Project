package com.example.addressbook;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public void insert(ItemGroup group) {
        try {
            PreparedStatement insertGroup = connection.prepareStatement(
                    "INSERT INTO ItemGroups (groupName, dateCreated, ownerId) VALUES (?, ?, ?)"
            );
            insertGroup.setString(1, group.getName());
            insertGroup.setString(2, group.getDateCreated());
            insertGroup.setInt(3, group.getOwnerId());
            insertGroup.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public List<ItemGroup> getAll() {
        List<ItemGroup> Groups = new ArrayList<>();
        try {
            PreparedStatement getItems = connection.prepareStatement("SELECT * FROM ItemGroups");
            ResultSet rs = getItems.executeQuery();
            while (rs.next()) {
                Groups.add(new ItemGroup(
                        rs.getInt("id"),
                        rs.getInt("ownerId"),
                        rs.getString("groupName"),
                        rs.getString("dateCreated")
                ));
            }
            return Groups;
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return null;
    }
}
