package com.ctrlaltz.inventorymanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

public class DashboardController {

    @FXML
    private VBox dashboardContent;

    @FXML
    private AnchorPane otherContentArea;

    @FXML
    private Label itemsCountLabel;

    @FXML
    private Label categoriesCountLabel;

    @FXML
    private Label roomsCountLabel;

    @FXML
    private Label totalValueLabel;

    @FXML
    private Label lblErr;

    private Integer userId;

    @FXML
    public void initialize() {

        UserHolder userHolder = UserHolder.getInstance();
        userId = userHolder.getUser();

        RoomDB roomDb = new RoomDB();
        ItemDB itemDb = new ItemDB();

        int roomCount = roomDb.getRoomsByUserID(userId).size();
        List<Item> items = itemDb.getItemsByID(userId);
        int itemCount = items.size();
        List<Float> prices = itemDb.GetItemPrices(userId);
        float totalValue = 0;

        for (float price : prices)
        {
            totalValue += price;
        }

        updateDashboardValues(itemCount, 0, roomCount, totalValue);
    }

    @FXML
    private void showDashboard() {
        dashboardContent.setVisible(true);
        otherContentArea.setVisible(false);
    }

    @FXML
    private void showSync() {
        loadOtherView("sync-view.fxml");
    }

    @FXML
    private void showItems() {
        loadOtherView("items-view.fxml");
    }

    @FXML
    private void showFilters() {
        loadOtherView("filters-view.fxml");
    }

    @FXML
    private void showSearch() {
        loadOtherView("search-view.fxml");
    }

    private void loadOtherView(String fxmlFile) {

        dashboardContent.setVisible(false);
        otherContentArea.setVisible(true);
        otherContentArea.getChildren().clear();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Node view = loader.load();
            otherContentArea.getChildren().add(view);
            AnchorPane.setTopAnchor(view, 0.0);
            AnchorPane.setBottomAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert("Error loading view: " + e.getMessage());
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void updateDashboardValues(int items, int categories, int rooms, double totalValue) {
        itemsCountLabel.setText(String.valueOf(items));
        categoriesCountLabel.setText(String.valueOf(categories));
        roomsCountLabel.setText(String.valueOf(rooms));

        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        totalValueLabel.setText(currencyFormatter.format(totalValue));
    }

    // Methods for handling button clicks
    @FXML
    private void handleAddButton() {
    }

    @FXML
    private void handleNotificationButton() {
    }

    @FXML
    private void handleSettingsButton() {
    }
}