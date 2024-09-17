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

public class RegisterController {


    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button returnToTitleBtn;
    @FXML
    private Button registerUserBtn;

    @FXML
    private void onRegReturnButtonClick() throws IOException {
        Stage stage = (Stage) (returnToTitleBtn) .getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/addressbook/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    @FXML
    private void onReturnButtonClick() throws IOException {

        String email = emailField.getText();
        String password = passwordField.getText();

        UsersDB userDb = new UsersDB();
        userDb.initializeTable();

        try {
            userDb.insert(new Users(email, password, "temp", "temp", "temp"));

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Incorrect Details");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Registered");
            alert.showAndWait();

            Stage stage = (Stage) (returnToTitleBtn) .getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/addressbook/login-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
            stage.setScene(scene);
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
        }
    }

}
