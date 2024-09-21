package com.example.addressbook;

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
