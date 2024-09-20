package com.example.addressbook;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button actionButton;
    @FXML private Label titleLabel;
    @FXML private Hyperlink toggleModeLink;
    @FXML private Hyperlink forgotPasswordLink;

    private boolean isLoginMode = true;

    @FXML
    public void initialize() {
        setLoginMode(true);
    }

    @FXML
    private void onActionButtonClick() throws IOException {
        if (isLoginMode) {
            // Perform login
            System.out.println("Login with: " + usernameField.getText());
        } else {
            // Perform registration
            System.out.println("Register with: " + usernameField.getText());
            // Here you would normally handle the registration process
        }
        // In both cases, switch to the dashboard view
        switchToDashboardView();
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