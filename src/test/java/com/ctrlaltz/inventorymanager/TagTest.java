package com.ctrlaltz.inventorymanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TagTest {
    Tag tag;
    Item item;
    ItemDB itemDb;
    TagDB tagDb;
    ItemTagDB itemTagDb;
    @BeforeEach
    void setUp() {
        tag = new Tag("TestTag", "This is a test tag");
        tagDb = new TagDB();
        itemDb = new ItemDB();
        //tagDb.insert(tag);
    }

    @Test
    void setName() {
        String newName = "TestTag2";
        tag.setName(newName);
        assertEquals(newName, tag.getName());
    }

    @Test
    void setDesc() {
        String newDesc = "Testing setting a new tag description";
        tag.setDesc(newDesc);
        assertEquals(newDesc, tag.getDesc());
    }

    @Test
    void tagItem() {
        item = itemDb.getById(1);
        tag = tagDb.getTagByName("TestTag");
        itemTagDb = new ItemTagDB();
        itemTagDb.initializeTable();
        itemTagDb.removeTag(item, tag);
        itemTagDb.tagItem(item, tag);
        List<Item> items = itemTagDb.getItemsByTag(tag);
        Item retrievedItem = items.get(0);
        itemTagDb.removeTag(item, tag);
        assertEquals(item.getName(), retrievedItem.getName());
    }
}