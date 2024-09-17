package com.example.addressbook;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;
    @FXML
    private Button returnButton;

    @FXML
    private void onLoginButtonClick() {
        String email = emailField.getText();
        String password = passwordField.getText();

        // Validate email and password
        if (email.isEmpty() || password.isEmpty())
        {
            showAlert(Alert.AlertType.ERROR, "Login Error",
                    "Please enter both email and password.");
        }
            else if (!EMAIL_PATTERN.matcher(email).matches()) {
            showAlert(Alert.AlertType.ERROR, "Invalid Email",
                    "Please enter a valid email address.");
        }
            else {
            // For now, just show a confirmation alert with email and password
            showAlert(Alert.AlertType.INFORMATION, "Login Information",
                    "Email: " + email + "\nPassword: " + password);

        }
    }
        // still testing, doesnt actually do anything with login data yet
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Login Information");
        alert.setHeaderText(null);
        alert.setContentText("Email: " + email + "\nPassword: " + password);
        alert.showAndWait();
    }
    @FXML
    private void onReturnButtonClick() throws IOException {
        Stage stage = (Stage) (returnButton) .getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/addressbook/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }
}