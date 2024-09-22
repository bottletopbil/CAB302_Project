package com.ctrlaltz.inventorymanager;

import java.sql.*;

public class TagDB {
    private Connection connection;
    public TagDB() {
        connection = DatabaseConnection.getInstance();
    }

    public void initializeTable() {
        try {
            Statement createTable = connection.createStatement();
            createTable.execute(
                    "CREATE TABLE IF NOT EXISTS Tags ("
                            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + "tagName VARCHAR NOT NULL,"
                            + "tagDesc VARCHAR NOT NULL"
                            + ")"
            );
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public void insert(Tag tag) {
        try {
            PreparedStatement insertTag = connection.prepareStatement(
                    "INSERT INTO Tags (tagName, tagDesc) VALUES (?, ?)"
            );
            insertTag.setString(1, tag.getName());
            insertTag.setString(2, tag.getDesc());
            insertTag.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public Tag getTagById(int id) {
        try {
            PreparedStatement getTag = connection.prepareStatement("SELECT * FROM Tags WHERE id = ?");
            getTag.setInt(1, id);
            ResultSet rs = getTag.executeQuery();
            if (rs.next()) {
                return new Tag(
                        rs.getInt("id"),
                        rs.getString("tagName"),
                        rs.getString("tagDesc")
                );
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return null;
    }
}
