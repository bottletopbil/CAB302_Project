package com.example.addressbook;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;

public class DashboardController {

    @FXML
    private AnchorPane mainContentArea; // The main content area where views will be swapped

    @FXML
    private Button homeButton;

    @FXML
    private Button itemsButton;

    @FXML
    private Button reportsButton;

    @FXML
    private Button quickAddButton;

    @FXML
    public void initialize() {
        // Set default view
        showHome();
    }

    @FXML
    private void showHome() {
        mainContentArea.getChildren().clear(); // Clear the existing content
        Label label = new Label("Home View");
        label.setStyle("-fx-font-size: 24px;");
        mainContentArea.getChildren().add(label); // Add new content
    }

    @FXML
    private void showItems() {
        mainContentArea.getChildren().clear();
        Label label = new Label("Items View");
        label.setStyle("-fx-font-size: 24px;");
        mainContentArea.getChildren().add(label);
    }

    @FXML
    private void showReports() {
        mainContentArea.getChildren().clear();
        Label label = new Label("Reports View");
        label.setStyle("-fx-font-size: 24px;");
        mainContentArea.getChildren().add(label);
    }

    @FXML
    private void showQuickAdd() {
        mainContentArea.getChildren().clear();
        Label label = new Label("Quick Add View");
        label.setStyle("-fx-font-size: 24px;");
        mainContentArea.getChildren().add(label);
    }
}
