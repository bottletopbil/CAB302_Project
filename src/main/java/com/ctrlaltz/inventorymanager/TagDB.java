package com.ctrlaltz.inventorymanager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public void update(Tag tag) {
        try {
            PreparedStatement updateTag = connection.prepareStatement(
                    "UPDATE Tags tagName = ?, tagDesc = ? WHERE id = ?"
            );
            updateTag.setString(1, tag.getName());
            updateTag.setString(2, tag.getDesc());
            updateTag.setInt(3, tag.getId());
            updateTag.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public void delete(int id) {
        try {
            PreparedStatement deleteTag = connection.prepareStatement(
                    "DELETE FROM Tags WHERE id = ?"
            );
            deleteTag.setInt(1, id);
            deleteTag.execute();
        } catch(SQLException ex) {
            System.err.println(ex);
        }
    }

    /**
     * Function to get Tag Object by ID
     * @param id - Tag ID
     * @return Tag Object
     */
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

    public List<Tag> getAll() {
        List<Tag> Tags = new ArrayList<>();
        try {
            PreparedStatement getItems = connection.prepareStatement("SELECT * FROM Tags");
            ResultSet rs = getItems.executeQuery();
            while (rs.next()) {
                Tags.add(new Tag(
                        rs.getInt("id"),
                        rs.getString("tagName"),
                        rs.getString("tagDesc")
                ));
            }
            return Tags;
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return null;
    }
}
