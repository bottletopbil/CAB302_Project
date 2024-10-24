package com.ctrlaltz.inventorymanager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDB {
    private Connection connection;
    public RoomDB() {
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

    public void insert(Room group) {
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

    public void update(Room group) {
        try {
            PreparedStatement updateGroup = connection.prepareStatement(
                    "UPDATE ItemGroups groupName = ?, dateCreated = ?, ownerId = ? WHERE id = ?"
            );
            updateGroup.setString(1, group.getName());
            updateGroup.setString(2, group.getDateCreated());
            updateGroup.setInt(3, group.getOwnerId());
            updateGroup.setInt(4, group.getId());
            updateGroup.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public void delete(int id) {
        try {
            PreparedStatement deleteItem = connection.prepareStatement(
                    "DELETE FROM ItemGroups WHERE id = ?"
            );
            deleteItem.setInt(1, id);
            deleteItem.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public List<Room> getAll() {
        List<Room> Groups = new ArrayList<>();
        try {
            PreparedStatement getItems = connection.prepareStatement("SELECT * FROM ItemGroups");
            ResultSet rs = getItems.executeQuery();
            while (rs.next()) {
                Groups.add(new Room(
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

    public List<Room> getRoomsByUserID(Integer userId) {
        List<Room> Groups = new ArrayList<>();
        try {
            PreparedStatement getItems = connection.prepareStatement("SELECT * FROM ItemGroups WHERE ownerId = " + userId);
            ResultSet rs = getItems.executeQuery();
            while (rs.next()) {
                Groups.add(new Room(
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
        return new ArrayList<>();
    }

    public Integer getRoomByName(String roomName, Integer userId)
    {
        Integer roomId = null;
        try {
            PreparedStatement getItems = connection.prepareStatement("SELECT * FROM ItemGroups WHERE groupName = ? AND ownerId = ? ");
            getItems.setString(1, roomName);
            getItems.setInt(2, userId);
            ResultSet rs = getItems.executeQuery();
            if (rs.next()) {
                roomId = rs.getInt("id");
                return roomId;
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return null;
    }
    /**
     * Function to get Room by Room ID
     * @param id - Room ID
     * @return Room Object
     */
    public Room getGroupById(int id) {
        try {
            PreparedStatement getItemGroups = connection.prepareStatement("SELECT * FROM ItemGroups WHERE id = ?");
            getItemGroups.setInt(1, id);
            ResultSet rs = getItemGroups.executeQuery();
            if (rs.next()) {
                return new Room(
                        rs.getInt("id"),
                        rs.getInt("ownerId"),
                        rs.getString("groupName"),
                        rs.getString("dateCreated")
                );
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return null;
    }

    /**
     * Function to get the Room object with ID using a Room Object without ID
     * @param room - Room Object without ID
     * @return Room Object with ID
     */
    public Room getGroupByProperties(Room room) {
        try {
            PreparedStatement getItemGroups = connection.prepareStatement("SELECT * FROM ItemGroups WHERE (groupName = ? AND dateCreated = ? AND ownerId = ?)");
            getItemGroups.setString(1, room.getName());
            getItemGroups.setString(2, room.getDateCreated());
            getItemGroups.setInt(3, room.getOwnerId());
            ResultSet rs = getItemGroups.executeQuery();
            if (rs.next()) {
                return new Room(
                        rs.getInt("id"),
                        rs.getInt("ownerId"),
                        rs.getString("groupName"),
                        rs.getString("dateCreated")
                );
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return null;
    }
}
