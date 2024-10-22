package com.ctrlaltz.inventorymanager;

import javafx.fxml.FXML;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class SearchController {
    @FXML private TextField searchField;
    @FXML private Button searchButton;
    @FXML private VBox roomsList;
    @FXML private Label itemNameLabel;
    @FXML private GridPane itemDetailsGrid;
    @FXML private ImageView itemImageView;
    @FXML private ListView<Item> itemsListView;

    @FXML
    private void initialize() {
        // Set custom cell factory to display item names
        itemsListView.setCellFactory(lv -> new ListCell<Item>() {
            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName()); // Assuming Item class has a getName() method
                }
            }
        });

        itemsListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showItemDetails(newValue)
        );
    }

    @FXML
    private void onsearchButtonClick(){
        String query = searchField.getText();
        ItemDB searchDB = new ItemDB();
        UserHolder holder = UserHolder.getInstance();
        Integer userId = holder.getUser();
        List<Item> items = searchDB.searchLikeName(query, userId);

        for (Item item : items)
        {
            ObservableList<Item> observableItems = FXCollections.observableArrayList(items);
            itemsListView.setItems(observableItems);
        }
    }

    private void addItemDetail(String label, String value, int row) {
        itemDetailsGrid.add(new Label(label + ":"), 0, row);
        itemDetailsGrid.add(new Label(value), 1, row);
    }

    private void showItemDetails(Item item) {
        if (item == null) return;

        itemNameLabel.setText(item.getName());
        itemDetailsGrid.getChildren().clear();

        addItemDetail("Brand", item.getBrand(), 0);
        addItemDetail("Price", String.format("$%.2f", item.getPrice()), 1);
        addItemDetail("Tags", item.getTagsToString(), 2);
        addItemDetail("Warranty", item.getWarranty(), 3);
        addItemDetail("Quantity", String.valueOf(item.getQuantity()), 4);
        addItemDetail("Condition", item.getCondition(), 5);

        if (item.getPhoto() != null) {
            itemImageView.setImage(item.getPhoto());
            itemImageView.setFitWidth(200);
            itemImageView.setFitHeight(200);
            itemImageView.setPreserveRatio(true);
        } else {
            itemImageView.setImage(null);
        }
    }
}
