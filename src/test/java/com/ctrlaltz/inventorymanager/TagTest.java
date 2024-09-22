package com.ctrlaltz.inventorymanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TagTest {
    Tag tag;

    @BeforeEach
    void setUp() {
        tag = new Tag("TestTag", "This is a test tag");
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
}