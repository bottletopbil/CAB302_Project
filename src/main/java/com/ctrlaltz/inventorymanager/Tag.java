package com.ctrlaltz.inventorymanager;

import com.google.gson.Gson;

public class Tag {
    private int id;
    private String name;
    private String desc;

    /**
     * Constructor to create the Tag object based on information gotten from the database
     * @param id - Tag ID
     * @param name - Tag Name
     * @param desc - Tag Description
     */
    public Tag(int id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    /**
     * Constructor to create the Tag object from the frontend
     * @param name - Tag Name
     * @param desc - Tag Description
     */
    public Tag(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    //Getters
    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDesc() {
        return this.desc;
    }

    //Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    /**
     * Function to convert instance of Tag to a JSON string
     * @return JSON string
     */
    public String toJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
