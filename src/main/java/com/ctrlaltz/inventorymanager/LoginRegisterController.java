package com.ctrlaltz.inventorymanager;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.atomic.AtomicReference;

public class LoginRegisterController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button actionButton;
    @FXML private Label titleLabel;
    @FXML private Hyperlink toggleModeLink;
    @FXML private Hyperlink forgotPasswordLink;

    private boolean isLoginMode = true;
    private boolean isLoginState = false;

    public Boolean getHashedPasswordCheck(String plainPassword, String hashedPassword) {
        AtomicReference<Boolean> responseHolder = new AtomicReference<>();
        // Define the parameters for the request
        int cost = 4;  // Example value for cost

        // Create a Runnable task to perform the POST request
        Runnable postRequestTask = new Runnable() {
            @Override
            public void run() {
                try {
                    // URL to make the POST request to
                    URL url = new URL("https://www.toptal.com/developers/bcrypt/api/check-password.json");

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
                    String formData = "hash=" + URLEncoder.encode(hashedPassword, "UTF-8") +
                            "&password=" + URLEncoder.encode(plainPassword, "UTF-8");

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
                    Boolean passwordTrue = jsonResponse.get("ok").getAsBoolean();  // Extract the "hash" key

                    responseHolder.set(passwordTrue);

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

    @FXML
    public void initialize() {
        setLoginMode(true);
    }
    @FXML
    private void addUser() throws IOException {
        String email = usernameField.getText();
        String password = passwordField.getText();
        //String hashedPassword = getHashedPassword(password);

        UsersDB userDb = new UsersDB();
        userDb.initializeTable();

        try {
            Users newUser = new Users(email, password, "temp", "temp", "temp");
            userDb.insert(newUser);

            setLoginStatus(!isLoginState);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registration");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Registered");
            alert.showAndWait();
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
        }
    }

    private void Login() {
        String email = usernameField.getText();
        String password = passwordField.getText();
        String hashedPass = null;

        UsersDB userDb = new UsersDB();
        userDb.initializeTable();

        try {
            Users user = userDb.getByUserName(email);
            if (user == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Incorrect Details");
                alert.setHeaderText(null);
                alert.setContentText("User does not exist. Try a different email or registering a new account.");
                alert.showAndWait();
            }
            else {
                hashedPass = user.getHashedPass();
                Boolean passwordTrue = getHashedPasswordCheck(password, hashedPass);

                if (passwordTrue)
                {
                    setLoginStatus(!isLoginState);

                    UserHolder loggedUser = UserHolder.getInstance();
                    Integer userId = userDb.getByUserName(email).getId();
                    loggedUser.setUser(userId);

                    Stage stage = (Stage) usernameField.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
                    stage.setScene(scene);
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Incorrect Details");
                    alert.setHeaderText(null);
                    alert.setContentText(hashedPass + " - " + password);
                    alert.showAndWait();
                    System.out.println("Stored Hashed Pass: " + hashedPass);
                    System.out.println("Gotten Hashed Pass: " + password);
                }
            }
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
        }
    }

    private void setLoginStatus(boolean loginState) {
        isLoginState = loginState;
    }

    @FXML
    private void onActionButtonClick() throws IOException {
        if (isLoginMode) {
            // Perform login
            Login();
            System.out.println("Login with: " + usernameField.getText());
        } else {
            // Perform registration
            addUser();

            UsersDB userDb = new UsersDB();
            String email = usernameField.getText();
            UserHolder userHolder = UserHolder.getInstance();
            Integer userId = userDb.getByUserName(email).getId();
            userHolder.setUser(userId);

            System.out.println("Register with: " + usernameField.getText());
            // Here you would normally handle the registration process
        }
        if (isLoginState){
            //only continue to dashboard if login was successful
            switchToDashboardView();
        }
        else{
            //stay on the current login screen
            System.out.println("Login attempt failed");
        }


    }

    @FXML
    private void onToggleModeClick() {
        setLoginMode(!isLoginMode);
    }

    private void setLoginMode(boolean loginMode) {
        isLoginMode = loginMode;
        titleLabel.setText(loginMode ? "Login" : "Register");
        actionButton.setText(loginMode ? "LOGIN" : "REGISTER");
        toggleModeLink.setText(loginMode ? "SIGN UP" : "LOGIN");
        forgotPasswordLink.setVisible(loginMode);
    }

    private void switchToDashboardView() throws IOException {
        Stage stage = (Stage) actionButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
        stage.setWidth(HelloApplication.WIDTH);
        stage.setHeight(HelloApplication.HEIGHT);
        stage.centerOnScreen();
    }
}