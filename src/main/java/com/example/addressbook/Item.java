package com.example.addressbook;

import javafx.scene.image.Image;

public class Item {
    private String name;
    private String brand;
    private double price;
    private String tags;
    private String warranty;
    private int quantity;
    private String condition;
    private Image photo;

    public Item(String name, String brand, double price, String tags, String warranty, int quantity, String condition, Image photo) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.tags = tags;
        this.warranty = warranty;
        this.quantity = quantity;
        this.condition = condition;
        this.photo = photo;
    }

    // Getters
    public String getName() { return name; }
    public String getBrand() { return brand; }
    public double getPrice() { return price; }
    public String getTags() { return tags; }
    public String getWarranty() { return warranty; }
    public int getQuantity() { return quantity; }
    public String getCondition() { return condition; }
    public Image getPhoto() { return photo; }
}