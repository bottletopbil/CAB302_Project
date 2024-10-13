package com.ctrlaltz.inventorymanager;

public class Users {
    private int id;
    private String userName;
    private String hashedPass; //Let's just use plaintext for now
    private String firstName;
    private String lastName;
    private String dateRegistered;

    /**
     * Constructor to create the User object based on information gotten from the database
     * @param id - User ID
     * @param userName - Username
     * @param hashedPass - Hashed and Salted Password (currently just stored in plaintext)
     * @param firstName - User's First Name
     * @param lastName - User's Last Name
     * @param dateRegistered - Date of User Registration
     */
    public Users(int id, String userName, String hashedPass, String firstName, String lastName, String dateRegistered) {
        this.id = id;
        this.userName = userName;
        this.hashedPass = hashedPass;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateRegistered = dateRegistered;
    }
    /**
     * Constructor to create the User object from the frontend
     * @param userName - Username
     * @param hashedPass - Hashed and Salted Password (currently just stored in plaintext)
     * @param firstName - User's First Name
     * @param lastName - User's Last Name
     * @param dateRegistered - Date of User Registration
     */
    public Users(String userName, String hashedPass, String firstName, String lastName, String dateRegistered) {
        this.userName = userName;
        this.hashedPass = hashedPass;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateRegistered = dateRegistered;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getHashedPass() {
        return hashedPass;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getdateRegistered() {
        return dateRegistered;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setHashedPass(String hashedPass) {
        this.hashedPass = hashedPass;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
