package com.ctrlaltz.inventorymanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TagTest {
    Tag tag;
    Item item;
    TagDB tagDb;
    ItemTagDB itemTagDb;
    @BeforeEach
    void setUp() {
        float testPrice = 1.99F;
        item = new Item(5, 1, "Test Item", "Test Brand", testPrice, "1 Year", 1, "New", "", "Test Description", "1234", "Australia", "26/10/2024", "26/10/2024");
        tag = new Tag("TestTag", "This is a test tag");
        tagDb = new TagDB();
        tagDb.initializeTable();
        tagDb.insert(tag);
    }

    @Test
    void setName() {
        String newName = "TestTag2";
        tag.setName(newName);
        tagDb.update(tag);
        assertEquals(newName, tag.getName());
    }

    @Test
    void setDesc() {
        String newDesc = "Testing setting a new tag description";
        tag.setDesc(newDesc);
        tagDb.update(tag);
        assertEquals(newDesc, tag.getDesc());
    }

    @Test
    void tagItem() {
        itemTagDb = new ItemTagDB();
        itemTagDb.initializeTable();
        itemTagDb.removeTag(item, tag);
        itemTagDb.tagItem(item, tag);
        List<Item> items = itemTagDb.getItemsByTag(tag);
        Item retrievedItem = items.get(0);
        assertEquals(item.getName(), retrievedItem.getName());
    }
}