package com.example.addressbook;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginRegisterController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button actionButton;
    @FXML private Label titleLabel;
    @FXML private Hyperlink toggleModeLink;
    @FXML private Hyperlink forgotPasswordLink;

    private boolean isLoginMode = true;
    private boolean isLoginState = false;

    @FXML
    public void initialize() {
        setLoginMode(true);
    }
    @FXML
    private void addUser() throws IOException {
        String email = usernameField.getText();
        String password = passwordField.getText();

        UsersDB userDb = new UsersDB();
        userDb.initializeTable();

        try {
            userDb.insert(new Users(email, password, "temp", "temp", "temp"));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Incorrect Details");
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

                if (hashedPass != null)
                {
                    if (hashedPass.equals(password))
                    {
                        setLoginStatus(!isLoginState);
                        Stage stage = (Stage) usernameField.getScene().getWindow();
                        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
                        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
                        stage.setScene(scene);
                    }
                    else
                    {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Incorrect Details");
                        alert.setHeaderText(null);
                        alert.setContentText(hashedPass + " - " + password);
                        alert.showAndWait();
                    }
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