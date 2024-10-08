package com.ctrlaltz.inventorymanager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

public class BackupRestore {
    public String backupAsJSON(String backupName) {
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

        //Get all Tag JSON

        return gson.toJson(backupObj);
    }

    public void restoreFromJSON() {

    }
}
