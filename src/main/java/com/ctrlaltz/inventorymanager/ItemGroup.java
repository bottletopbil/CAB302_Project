package com.ctrlaltz.inventorymanager;

public class ItemGroup {
    private int id;
    private int ownerId;
    private String name;
    private String dateCreated;

    public ItemGroup(int id, int ownerId, String name, String dateCreated) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.dateCreated = dateCreated;
    }

    public ItemGroup(int ownerId, String name, String dateCreated) {
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
}
