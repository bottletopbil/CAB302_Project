package com.ctrlaltz.inventorymanager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class BackupRestore {
    public String backupAllAsJSON(String backupName) {
        Gson gson = new Gson();
        JsonObject backupObj = new JsonObject();

        backupObj.addProperty("name", backupName);

        //Get all ItemGroup
        ItemGroupDB itemGroupDb = new ItemGroupDB();
        List<ItemGroup> itemGroups = itemGroupDb.getAll();

        //Iterate through individual ItemGroup to get JSON
        JsonArray itemGroupsArray = new JsonArray();
        for (ItemGroup group: itemGroups) {
            itemGroupsArray.add(group.toJSON());
        }
        backupObj.add("rooms", itemGroupsArray);
        //Get all Tag JSON
        TagDB tagDb = new TagDB();
        List<Tag> tags = tagDb.getAll();

        JsonArray tagsArray = new JsonArray();
        for (Tag tag: tags) {
            tagsArray.add(tag.toJSON());
        }
        backupObj.add("tags", tagsArray);

        return gson.toJson(backupObj);
    }

    public String backupSelectedAsJSON(String backupName, List<Integer> roomIds) {
        Gson gson = new Gson();
        JsonObject backupObj = new JsonObject();
        backupObj.addProperty("name", backupName);

        //Get selected ItemGroup
        ItemGroupDB itemGroupDb = new ItemGroupDB();
        List<ItemGroup> itemGroups = new ArrayList<>();

        for (Integer roomId: roomIds) {
            itemGroups.add(itemGroupDb.getGroupById(roomId));
        }

        //Iterate through individual ItemGroup to get JSON
        JsonArray itemGroupsArray = new JsonArray();
        for (ItemGroup group: itemGroups) {
            itemGroupsArray.add(group.toJSON());
        }
        backupObj.add("rooms", itemGroupsArray);

        return gson.toJson(backupObj);
    }

    public void restoreFromJSON() {

    }
}
