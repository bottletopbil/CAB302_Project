package com.ctrlaltz.inventorymanager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

public class Room {
    private int id;
    private int ownerId;
    private String name;
    private String dateCreated;

    /**
     * Constructor to create the Room object based on information gotten from the database
     * @param id - Room ID
     * @param ownerId - Owner ID
     * @param name - Room Name
     * @param dateCreated - Room Date Created
     */
    public Room(int id, int ownerId, String name, String dateCreated) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.dateCreated = dateCreated;
    }

    /**
     * Constructor to create the Room object from the frontend or from a backup
     * @param ownerId - Owner ID
     * @param name - Room Name
     * @param dateCreated - Room Date Created
     */
    public Room(int ownerId, String name, String dateCreated) {
        this.ownerId = ownerId;
        this.name = name;
        this.dateCreated = dateCreated;
    }

    //Getters
    public int getId() {
        return this.id;
    }

    public int getOwnerId() {
        return this.ownerId;
    }

    public String getName() {
        return this.name;
    }

    public String getDateCreated() {
        return this.dateCreated;
    }
    //Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
    /**
     * Function to convert instance of Room as well as all Item stored in the Room to a JSON string
     * @return JSON string
     */
    public String toJSON() {
        Gson gson = new Gson();
        JsonObject room = new JsonObject();
        room.addProperty("id", this.id);
        room.addProperty("ownerId", this.ownerId);
        room.addProperty("name", this.name);
        room.addProperty("dateCreated", this.dateCreated);

        //Get all Items belonging to group
        ItemDB itemDb = new ItemDB();
        List<Item> items = itemDb.getByGroupId(this.id);

        JsonArray itemsArray = new JsonArray();
        for (Item item: items) {
            itemsArray.add(item.toJSON());
        }
        room.add("items", itemsArray);

        return gson.toJson(room);
    }
}
