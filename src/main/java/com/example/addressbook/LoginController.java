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
        String hashedPass = null;

        UsersDB userDb = new UsersDB();
        userDb.initializeTable();

        try {
            Users user = userDb.getByUserName(email);
            if (user == null) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Incorrect Details");
                alert.setHeaderText(null);
                alert.setContentText("User does not exist. Try a different email or registering a new account.");
                alert.showAndWait();
            }
            else {
                hashedPass = user.getHashedPass();

                if (hashedPass != null)
                {
                    if (hashedPass == password)
                    {
                        Stage stage = (Stage) emailField.getScene().getWindow();
                        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
                        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
                        stage.setScene(scene);
                    }
                    else
                    {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Incorrect Details");
                        alert.setHeaderText(null);
                        alert.setContentText("Incorrect Password, try again.");
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
    @FXML
    private void onReturnButtonClick() throws IOException {
        Stage stage = (Stage) (returnButton) .getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/addressbook/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }
}