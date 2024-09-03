package com.example.addressbook;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class InventoryDashboardController {
    // Define any FXML-linked components or data-related operations here
    @FXML
    private Label totalItemsLabel;
    @FXML
    private Label categoriesLabel;
    @FXML
    private Label roomsLabel;
    @FXML
    private Label inventoryValueLabel;

    @FXML
    public void initialize() {
        // Initialization logic, such as setting initial values or loading data
        totalItemsLabel.setText("172");  // Example data, replace with actual logic
        categoriesLabel.setText("15");
        roomsLabel.setText("4");
        inventoryValueLabel.setText("$5,012");
    }
}
