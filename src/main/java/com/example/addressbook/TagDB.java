package com.example.addressbook;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TagDB {
    private Connection connection;

    public TagDB() {
        connection = DatabaseConnection.getInstance();
    }

    // Method to create the Tags table if it doesn't exist
    public void initializeTable() {
        try {
            Statement createTable = connection.createStatement();
            createTable.execute(
                    "CREATE TABLE IF NOT EXISTS Tags (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "tagName VARCHAR NOT NULL)"
            );
        } catch (SQLException ex) {
            System.err.println("Error creating Tags table: " + ex.getMessage());
        }
    }

    // Method to add a new tag
    public boolean addTag(String tagName) {
        String insertSQL = "INSERT INTO Tags (tagName) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(insertSQL)) {
            stmt.setString(1, tagName);
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error adding tag: " + ex.getMessage());
            return false;
        }
    }

    // Method to get all tags
    public List<Tag> getAllTags() {
        List<Tag> tags = new ArrayList<>();
        String selectSQL = "SELECT * FROM Tags";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {
            while (rs.next()) {
                Tag tag = new Tag(rs.getInt("id"), rs.getString("tagName"));
                tags.add(tag);
            }
        } catch (SQLException ex) {
            System.err.println("Error retrieving tags: " + ex.getMessage());
        }
        return tags;
    }

    // Method to delete a tag by id
    public boolean deleteTag(int id) {
        String deleteSQL = "DELETE FROM Tags WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(deleteSQL)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error deleting tag: " + ex.getMessage());
            return false;
        }
    }

    // Method to update a tag's name by id
    public boolean updateTag(int id, String newTagName) {
        String updateSQL = "UPDATE Tags SET tagName = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(updateSQL)) {
            stmt.setString(1, newTagName);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error updating tag: " + ex.getMessage());
            return false;
        }
    }
}
