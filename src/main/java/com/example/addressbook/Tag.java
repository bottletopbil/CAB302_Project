package com.example.addressbook;

public class Tag {
    private int id;
    private String tagName;

    public Tag(int id, String tagName) {
        this.id = id;
        this.tagName = tagName;
    }

    // Constructor without id for creating new tags
    public Tag(String tagName) {
        this.tagName = tagName;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String toString() {
        return tagName;
    }
}
