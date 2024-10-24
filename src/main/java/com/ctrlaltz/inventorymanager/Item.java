package com.ctrlaltz.inventorymanager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.scene.image.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Base64;
import java.nio.file.Files;
import java.awt.image.BufferedImage;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class Item {
    private int id;
    private int groupId;
    private int ownerId;

    private String name;
    private String brand;
    private float price;
    private List<Tag> tags = new ArrayList<>();
    private String warranty;
    private int quantity;
    private String condition;
    private Image photo;
    private String photoB64;
    private Boolean hasPhoto;

    private String itemDesc;
    private String datePurchased;
    private String purchaseLocation;
    private String itemSerial;
    private String dateRegistered;

    /**
     * Constructor to create the Item object based on information gotten from the database
     *
     * @param id - Item ID (Int)
     * @param groupId - Room ID (Int)
     * @param ownerId - Owner ID (Int)
     * @param name - Item Name (String)
     * @param brand - Item Brand (String)
     * @param price - Item Price (Float)
     * @param warranty - Item Warranty (String)
     * @param quantity - Item Quantity (Int)
     * @param condition - Item Condition (String)
     * @param photo - Item Photo (String)
     * @param itemDesc - Item Description (String)
     * @param itemSerial - Item Serial (String)
     * @param purchaseLocation - Item Purchase Location (String)
     * @param datePurchased - Item Date Purchased (String)
     * @param dateRegistered - Item Date Registered (String)
     */
    public Item(int id, int groupId, int ownerId, String name, String brand, float price, String warranty, int quantity, String condition, String photo, String itemDesc, String itemSerial, String purchaseLocation, String datePurchased, String dateRegistered) {
        this.id = id;
        this.groupId = groupId;
        this.ownerId = ownerId;
        this.name = name;
        this.brand = brand;
        this.price = price;
        //this.tags = tags;
        this.warranty = warranty;
        this.quantity = quantity;
        this.condition = condition;
        this.photoB64 = photo;

        if (photo != null) {
            this.photo = convertBase64ToImage(photo);
            this.hasPhoto = true;
        } else {
            this.hasPhoto = false;
        }
        this.itemDesc = itemDesc;
        this.itemSerial = itemSerial;
        this.purchaseLocation = purchaseLocation;
        this.datePurchased = datePurchased;
        this.dateRegistered = dateRegistered;
    }
    /**
     * Constructor to create the Item object from the frontend
     *
     * @param groupId - Room ID (Int)
     * @param ownerId - Owner ID (Int)
     * @param name - Item Name (String)
     * @param brand - Item Brand (String)
     * @param price - Item Price (Float)
     * @param warranty - Item Warranty (String)
     * @param quantity - Item Quantity (Int)
     * @param condition - Item Condition (String)
     * @param photo - Item Photo (File)
     * @param itemDesc - Item Description (String)
     * @param itemSerial - Item Serial (String)
     * @param purchaseLocation - Item Purchase Location (String)
     * @param datePurchased - Item Date Purchased (String)
     * @param dateRegistered - Item Date Registered (String)
     */
    public Item(int groupId, int ownerId, String name, String brand, float price, String warranty, int quantity, String condition, File photo, String itemDesc, String itemSerial, String purchaseLocation, String datePurchased, String dateRegistered) {
        this.groupId = groupId;
        this.ownerId = ownerId;
        this.name = name;
        this.brand = brand;
        this.price = price;
        //this.tags = tags;
        this.warranty = warranty;
        this.quantity = quantity;
        this.condition = condition;
        if (photo == null) {
            this.hasPhoto = false;
        } else {
            this.photo = new Image(photo.toURI().toString());
            this.photoB64 = encodeFileToBase64(photo);
            this.hasPhoto = true;
        }

        this.itemDesc = itemDesc;
        this.itemSerial = itemSerial;
        this.purchaseLocation = purchaseLocation;
        this.dateRegistered = dateRegistered;
        this.datePurchased = datePurchased;
    }

    /**
     * Constructor to create the Item object when restoring from backup
     *
     * @param groupId - Room ID (Int)
     * @param ownerId - Owner ID (Int)
     * @param name - Item Name (String)
     * @param brand - Item Brand (String)
     * @param price - Item Price (Float)
     * @param warranty - Item Warranty (String)
     * @param quantity - Item Quantity (Int)
     * @param condition - Item Condition (String)
     * @param photo - Item Photo (String)
     * @param itemDesc - Item Description (String)
     * @param itemSerial - Item Serial (String)
     * @param purchaseLocation - Item Purchase Location (String)
     * @param datePurchased - Item Date Purchased (String)
     * @param dateRegistered - Item Date Registered (String)
     */
    public Item(int groupId, int ownerId, String name, String brand, float price, String warranty, int quantity, String condition, String photo, String itemDesc, String itemSerial, String purchaseLocation, String datePurchased, String dateRegistered) {
        this.groupId = groupId;
        this.ownerId = ownerId;
        this.name = name;
        this.brand = brand;
        this.price = price;
        //this.tags = tags;
        this.warranty = warranty;
        this.quantity = quantity;
        this.condition = condition;
        this.photoB64 = photo;

        if (photo != null) {
            this.photo = convertBase64ToImage(photo);
            this.hasPhoto = true;
        } else {
            this.hasPhoto = false;
        }

        this.itemDesc = itemDesc;
        this.itemSerial = itemSerial;
        this.purchaseLocation = purchaseLocation;
        this.datePurchased = datePurchased;
        this.dateRegistered = dateRegistered;
    }

    /**
     * Constructor to create the Item object from the frontend
     *
     * @param name - Item Name (String)
     * @param brand - Item Brand (String)
     * @param price - Item Price (Float)
     * @param warranty - Item Warranty (String)
     * @param quantity - Item Quantity (Int)
     * @param condition - Item Condition (String)
     * @param photo - Item Photo (File)
     * @param itemDesc - Item Description (String)
     * @param itemSerial - Item Serial (String)
     * @param purchaseLocation - Item Purchase Location (String)
     * @param datePurchased - Item Date Purchased (String)
     * @param dateRegistered - Item Date Registered (String)
     */
    public Item(String name, String brand, float price, String warranty, int quantity, String condition, File photo, String itemDesc, String itemSerial, String purchaseLocation, String datePurchased, String dateRegistered) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        //this.tags = tags;
        this.warranty = warranty;
        this.quantity = quantity;
        this.condition = condition;
        if (photo == null) {
            this.hasPhoto = false;
        } else {
            this.photo = new Image(photo.toURI().toString());
            this.photoB64 = encodeFileToBase64(photo);
            this.hasPhoto = true;
        }
        this.itemDesc = itemDesc;
        this.itemSerial = itemSerial;
        this.purchaseLocation = purchaseLocation;
        this.datePurchased = datePurchased;
        this.dateRegistered = dateRegistered;
    }

    /**
     * Function to convert File object to Base64 string
     * @param file - File object
     * @return Base64 encoded string
     */
    private static String encodeFileToBase64(File file) {
        try {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            throw new IllegalStateException("could not read file " + file, e);
        }
    }

    /**
     * Function to convert Image class photo to Base64 String - NO LONGER USED
     * @param image - Image class photo
     * @return Base64 encoded String version of the photo
     */
    /*private String convertImageToBase64(Image image) {
        // Create a WritableImage to hold the image data
        WritableImage writableImage = new WritableImage((int) image.getWidth(), (int) image.getHeight());

        // Read the pixels from the original image and write them to the WritableImage
        PixelReader pixelReader = image.getPixelReader();
        if (pixelReader != null) {
            writableImage.getPixelWriter().setPixels(0, 0, (int) image.getWidth(), (int) image.getHeight(), pixelReader, 0, (int) image.getWidth());
        }

        // Convert WritableImage to BufferedImage
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage, null);

        // Write BufferedImage to ByteArrayOutputStream
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // Choose the image format (e.g., "png" or "jpeg")
            ImageIO.write(bufferedImage, "png", outputStream);
            byte[] imageBytes = outputStream.toByteArray();

            // Encode the byte array to Base64
            String imgString = Base64.getEncoder().encodeToString(imageBytes);
            System.out.println(imgString);
            return imgString;
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Handle error appropriately in production code
        }
    }*/

    /**
     * Function to convert a Base64 encoded photo back to Image
     * @param base64String - Base64 String containing the image
     * @return photo in Image class
     */
    private Image convertBase64ToImage(String base64String) {
        // Decode the Base64 string
        byte[] imageBytes = Base64.getDecoder().decode(base64String);

        // Convert byte array to Image
        return new Image(new ByteArrayInputStream(imageBytes));
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
    public float getPrice() { return price; }
    public Boolean hasPhoto() {
        return hasPhoto;
    }
    public String getItemSerial() {
        return itemSerial;
    }
    public String getPurchaseLocation() {
        return purchaseLocation;
    }
    public List<Tag> getTags() { return tags; }

    /**
     * Function to convert the List of Tags to a string containing the Item's tags' names
     * @return Comma delimited string of tag names
     */
    public String getTagsToString() {
        //return String.join(",", tags);
        List<String> tagsStr = new ArrayList<>();
        for (Tag tag: tags) {
            tagsStr.add(tag.getName());
        }
        return String.join(",", tagsStr);
    }
    public String getWarranty() { return warranty; }
    public int getQuantity() { return quantity; }
    public String getCondition() { return condition; }
    public Image getPhoto() {
        return photo;
    }
    public String getPhotoAsString() {
        return photoB64;
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

    //Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setTags() {
        //Access ItemTagDB and get all tags by item, set String Array
        ItemTagDB itemTagDb = new ItemTagDB();
        //Loop through tags and fill in String array
        this.tags = itemTagDb.getTagsByItem(this);
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setPhoto(String photoB64) {
        //Convert base64 to Photo
        this.photoB64 = photoB64;
        this.photo = convertBase64ToImage(photoB64);
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }
    public void setItemSerial(String itemSerial) {
        this.itemSerial = itemSerial;
    }
    public void setPurchaseLocation(String purchaseLocation) {
        this.purchaseLocation = purchaseLocation;
    }
    public void setDatePurchased(String datePurchased) {
        this.datePurchased = datePurchased;
    }

    public void setDateRegistered(String dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    /**
     * Function to convert instance of Item to a JSON string
     * @return JSON string
     */
    public String toJSON() {
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(this);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        //Add tags
        ItemTagDB itemTagDb = new ItemTagDB();
        List<Tag> tags = itemTagDb.getTagsByItem(this);
        JsonArray itemTags = new JsonArray();
        for (Tag tag: tags) {
            itemTags.add(tag.getId());
        }
        jsonObject.add("tags", itemTags);

        return gson.toJson(jsonObject);
    }
}