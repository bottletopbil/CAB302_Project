package com.ctrlaltz.inventorymanager;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.atomic.AtomicReference;

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
        this.hashedPass = getHashedPassword(hashedPass);
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

    public void setHashedPass(String plainPassword) {
        this.hashedPass = getHashedPassword(plainPassword);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHashedPassword(String plainPassword) {
        AtomicReference<String> responseHolder = new AtomicReference<>();
        // Define the parameters for the request
        int cost = 4;  // Example value for cost

        // Create a Runnable task to perform the POST request
        Runnable postRequestTask = new Runnable() {
            @Override
            public void run() {
                try {
                    // URL to make the POST request to
                    URL url = new URL("https://www.toptal.com/developers/bcrypt/api/generate-hash.json");

                    // Open connection
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    // Set the request method to POST
                    connection.setRequestMethod("POST");

                    // Enable input/output streams
                    connection.setDoOutput(true);
                    connection.setDoInput(true);

                    // Set headers, including Content-Type for form-encoded data
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                    // Encode form parameters
                    String formData = "password=" + URLEncoder.encode(plainPassword, "UTF-8") +
                            "&cost=" + URLEncoder.encode(String.valueOf(cost), "UTF-8");

                    // Send the form-encoded data
                    try (OutputStream os = connection.getOutputStream()) {
                        byte[] input = formData.getBytes("utf-8");
                        os.write(input, 0, input.length);
                    }

                    // Get the response code
                    int responseCode = connection.getResponseCode();
                    System.out.println("POST Response Code: " + responseCode);

                    // Read the response body
                    InputStream inputStream;
                    if (responseCode >= 200 && responseCode < 300) {
                        // Success, use regular input stream
                        inputStream = connection.getInputStream();
                    } else {
                        // Error, use error stream
                        inputStream = connection.getErrorStream();
                    }

                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line.trim());
                    }

                    // Close the streams
                    reader.close();
                    inputStream.close();

                    // Output the response body
                    //System.out.println("POST Response Body: " + response.toString());
                    // Parse the JSON response to extract the "hash" key using GSON
                    JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
                    String hashValue = jsonResponse.get("hash").getAsString();  // Extract the "hash" key

                    responseHolder.set(hashValue);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        // Create a thread and start it
        Thread thread = new Thread(postRequestTask);
        thread.start();

        try {
            // Wait for the thread to complete
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return responseHolder.get();
    }
}
