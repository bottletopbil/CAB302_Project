package com.ctrlaltz.inventorymanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.ctrlaltz.inventorymanager.UsersDB;

import static org.junit.jupiter.api.Assertions.*;

class UsersDBTest {
    //ItemDB itemDb;
    UsersDB userDb;

    @BeforeEach
    void setUp() {
        userDb = new UsersDB();
    }

    @Test
    void testDBReadWrite() {
        String testUser = "testUser";
        String testPassword = "testPassword123";
        Users testUserObj = new Users(testUser, testPassword, "temp", "temp", "temp");
        userDb.insert(testUserObj);

        Users actualUserObj = userDb.getByUserName(testUser);

        assertEquals(testUserObj.getUserName(), actualUserObj.getUserName());
    }
}