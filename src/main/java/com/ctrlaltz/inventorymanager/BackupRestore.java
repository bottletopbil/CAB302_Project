package com.ctrlaltz.inventorymanager;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import javafx.scene.image.Image;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BackupRestore {
    private final Gson gson = new Gson();

    /**
     * Function to perform a full backup of all Rooms
     *
     * @param backupName - name to give the backup, will be stored in the JSON as a property
     * @return JSON string containing the full backup of all Rooms and Items inside
     */
    public String backupAllAsJSON(String backupName) {

        JsonObject backupObj = new JsonObject();

        backupObj.addProperty("name", backupName);

        //Get all Room
        RoomDB roomDb = new RoomDB();
        List<Room> rooms = roomDb.getAll();

        //Iterate through individual Room to get JSON
        JsonArray roomsArray = new JsonArray();
        for (Room group: rooms) {
            roomsArray.add(group.toJSON());
        }
        backupObj.add("rooms", roomsArray);
        /*//Get all Tag JSON
        TagDB tagDb = new TagDB();
        List<Tag> tags = tagDb.getAll();

        JsonArray tagsArray = new JsonArray();
        for (Tag tag: tags) {
            tagsArray.add(tag.toJSON());
        }
        backupObj.add("tags", tagsArray);*/

        return gson.toJson(backupObj);
    }

    /**
     * Function to perform a partial backup of selected Rooms
     *
     * @param backupName - name to give the backup, will be stored in the JSON as a property
     * @param roomIds - List of IDs of Rooms to perform the backup on
     * @return JSON string containing the partial backup of specified Rooms and all Items inside
     */
    public String backupSelectedAsJSON(String backupName, List<Integer> roomIds) {
        JsonObject backupObj = new JsonObject();
        backupObj.addProperty("name", backupName);

        //Get selected Room
        RoomDB roomDb = new RoomDB();
        List<Room> rooms = new ArrayList<>();

        for (Integer roomId: roomIds) {
            rooms.add(roomDb.getGroupById(roomId));
        }

        //Iterate through individual Room to get JSON
        JsonArray roomsArray = new JsonArray();
        for (Room group: rooms) {
            roomsArray.add(group.toJSON());
        }
        backupObj.add("rooms", roomsArray);
        /*//Get all Tag JSON
        TagDB tagDb = new TagDB();
        List<Tag> tags = tagDb.getAll();

        JsonArray tagsArray = new JsonArray();
        for (Tag tag: tags) {
            tagsArray.add(tag.toJSON());
        }
        backupObj.add("tags", tagsArray);*/

        return gson.toJson(backupObj);
    }

    /**
     * Function to restore the backup
     *
     * @param restoreJSON - JSON String generated by one of the two backup functions
     * @param ownerId - ID of the currently logged-in user
     */
    public void restoreFromJSON(String restoreJSON, int ownerId) {
        JsonObject restore = JsonParser.parseString(restoreJSON).getAsJsonObject();
        //Restore rooms
        String roomsJSON = restore.get("rooms").getAsString();
        JsonArray roomsArray = JsonParser.parseString(roomsJSON).getAsJsonArray();

        for (JsonElement element : roomsArray) {
            JsonObject jsonObject = element.getAsJsonObject();

            //Room properties
            String name = jsonObject.get("name").getAsString();
            String dateCreated = jsonObject.get("dateCreated").getAsString();

            //Create new Room under current user
            Room room = new Room(ownerId, name, dateCreated);
            RoomDB roomDb = new RoomDB();
            roomDb.insert(room);

            int roomId = roomDb.getGroupByProperties(room).getId();

            //Unpack items
            String itemsJSON = jsonObject.get("items").getAsString();
            JsonArray itemsArray = JsonParser.parseString(itemsJSON).getAsJsonArray();

            for (JsonElement itemElement : itemsArray) {
                JsonObject itemObject = itemElement.getAsJsonObject();
                //Create new Item
                String itemName = itemObject.get("name").getAsString();
                String itemBrand = itemObject.get("brand").getAsString();
                float itemPrice = itemObject.get("price").getAsFloat();
                String itemWarranty = itemObject.get("warranty").getAsString();
                int itemQuantity = itemObject.get("quantity").getAsInt();
                String itemCondition = itemObject.get("condition").getAsString();
                String itemPhoto = itemObject.get("photoB64").getAsString();
                String itemDesc = itemObject.get("itemDesc").getAsString();
                String itemSerial = itemObject.get("itemSerial").getAsString();
                String purchaseLocation = itemObject.get("purchaseLocation").getAsString();
                String itemDatePurchased = itemObject.get("datePurchased").getAsString();
                String itemDateRegistered = itemObject.get("dateRegistered").getAsString();
                Item item = new Item(roomId, ownerId, itemName, itemBrand, itemPrice, itemWarranty, itemQuantity, itemCondition, itemPhoto, itemDesc, itemSerial, purchaseLocation, itemDatePurchased, itemDateRegistered);

                //Insert into DB
                ItemDB itemDb = new ItemDB();
                itemDb.insert(item);
            }
        }
        /*Type tagListType = new TypeToken<List<Tag>>() {}.getType();
        List<Tag> tagList = gson.fromJson(tagsJSON, tagListType);*/

    }
}
