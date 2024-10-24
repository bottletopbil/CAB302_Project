package com.ctrlaltz.inventorymanager;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ItemTagDB {
    private Connection connection;
    public ItemTagDB() {
        connection = DatabaseConnection.getInstance();
    }

    private String getDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    public void initializeTable() {
        try {
            Statement createTable = connection.createStatement();
            createTable.execute(
                    "CREATE TABLE IF NOT EXISTS ItemTags ("
                            + "itemId INTEGER NOT NULL REFERENCES Items(id), "
                            + "tagId INTEGER NOT NULL REFERENCES Tags(id),"
                            + "dateTagged DATETIME NOT NULL"
                            + "PRIMARY KEY (itemId, tagId)"
                            + ")"
            );
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    /**
     * Function to insert an Item-Tag association
     * @param item Item to be tagged
     * @param tag Tag object
     */
    public void tagItem(Item item, Tag tag) {
        try {
            PreparedStatement insertItemTag = connection.prepareStatement(
                    "INSERT INTO ItemTags (itemId, tagId, dateTagged) VALUES (?, ?, ?)"
            );
            insertItemTag.setInt(1, item.getId());
            insertItemTag.setInt(2, tag.getId());
            insertItemTag.setString(3, getDateTime());
            insertItemTag.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    /**
     * Function to remove an Item-Tag association
     * @param item Item to have the tag removed
     * @param tag Tag object
     */
    public void removeTag(Item item, Tag tag) {
        try {
            PreparedStatement removeItemTag = connection.prepareStatement(
                    "DELETE FROM ItemTags WHERE itemId = ? AND tagId = ?"
            );
            removeItemTag.setInt(1, item.getId());
            removeItemTag.setInt(2, tag.getId());
            removeItemTag.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    /**
     * Function to get all Tags associated with an Item
     * @param item - Item object
     * @return List of Tag
     */
    public List<Tag> getTagsByItem(Item item) {
        List<Tag> tags = new ArrayList<>();
        try {
            PreparedStatement getTags = connection.prepareStatement("SELECT * FROM ItemTags WHERE itemId = ?");
            getTags.setInt(1, item.getId());
            ResultSet rs = getTags.executeQuery();
            while (rs.next()) {
                //Get tag by ID
                TagDB tagDb = new TagDB();
                tags.add(tagDb.getTagById(rs.getInt("id")));
            }
            return tags;
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return null;
    }

    /**
     * Function to get all Items associated with a Tag
     * @param tag - Tag object
     * @return List of Item
     */
    public List<Item> getItemsByTag(Tag tag) {
        List<Item> items = new ArrayList<>();
        try {
            PreparedStatement getItems = connection.prepareStatement("SELECT * FROM ItemTags WHERE tagId = ?");
            getItems.setInt(1, tag.getId());
            ResultSet rs = getItems.executeQuery();
            while (rs.next()) {
                //Get tag by ID
                ItemDB itemDb = new ItemDB();
                items.add(itemDb.getById(rs.getInt("id")));
            }
            return items;
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return null;
    }
}
