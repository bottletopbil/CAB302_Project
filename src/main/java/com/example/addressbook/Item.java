package com.example.addressbook;

import javafx.scene.image.Image;

public class Item {
    private int id;
    private int groupId;
    private int ownerId;

    private String name;
    private String brand;
    private double price;
    private String tags;
    private String warranty;
    private int quantity;
    private String condition;
    private Image photo;

    private String itemDesc;
    private String datePurchased;
    private String dateRegistered;

    public Item(String name, String brand, double price, String tags, String warranty, int quantity, String condition, Image photo, String itemDesc, String datePurchased, String dateRegistered) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.tags = tags;
        this.warranty = warranty;
        this.quantity = quantity;
        this.condition = condition;
        this.photo = photo;

        this.itemDesc = itemDesc;
        this.datePurchased = datePurchased;
        this.dateRegistered = dateRegistered;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getGroupId() {
        return groupId;
    }

    public int getOwnerId() {
        return ownerId;
    }
    public String getName() { return name; }
    public String getBrand() { return brand; }
    public double getPrice() { return price; }
    public String getTags() { return tags; }
    public String getWarranty() { return warranty; }
    public int getQuantity() { return quantity; }
    public String getCondition() { return condition; }
    public Image getPhoto() {
        //Convert base64 string back to image
        return photo;
    }
    public String getItemDesc() {
        return itemDesc;
    }
    public String getDatePurchased() {
        return datePurchased;
    }

    public String getDateRegistered() {
        return dateRegistered;
    }
}