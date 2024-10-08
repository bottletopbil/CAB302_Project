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

    public Room(int id, int ownerId, String name, String dateCreated) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.dateCreated = dateCreated;
    }

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

    public String toJSON() {
        Gson gson = new Gson();
        JsonObject itemGroup = new JsonObject();
        itemGroup.addProperty("id", this.id);
        itemGroup.addProperty("ownerId", this.ownerId);
        itemGroup.addProperty("name", this.name);
        itemGroup.addProperty("dateCreated", this.dateCreated);

        //Get all Items belonging to group
        ItemDB itemDb = new ItemDB();
        List<Item> items = itemDb.getByGroupId(this.id);

        JsonArray itemsArray = new JsonArray();
        for (Item item: items) {
            itemsArray.add(item.toJSON());
        }
        itemGroup.add("items", itemsArray);

        return gson.toJson(itemGroup);
    }
}
