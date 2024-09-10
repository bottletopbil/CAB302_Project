package com.example.addressbook;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.SplitPane; // Correct import for SplitPane
import java.io.IOException;

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
        mainContentArea.getChildren().clear(); // Clear existing content
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("items-view.fxml"));
            SplitPane itemsView = (SplitPane) loader.load(); // Cast the loaded object to SplitPane
            mainContentArea.getChildren().add(itemsView); // Add the SplitPane to the AnchorPane
            AnchorPane.setTopAnchor(itemsView, 0.0); // Ensure the SplitPane fits correctly
            AnchorPane.setBottomAnchor(itemsView, 0.0);
            AnchorPane.setLeftAnchor(itemsView, 0.0);
            AnchorPane.setRightAnchor(itemsView, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
