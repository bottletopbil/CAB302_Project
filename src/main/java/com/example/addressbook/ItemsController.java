package com.example.addressbook;

import javafx.fxml.FXML;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Optional;
import java.util.HashMap;
import java.util.ArrayList;

public class ItemsController {

    @FXML private VBox sideMenu;
    @FXML private VBox roomsList;
    @FXML private Button addRoomButton;
    @FXML private VBox itemsPanel;
    @FXML private Label selectedRoomLabel;
    @FXML private ListView<String> itemsListView;
    @FXML private VBox itemDetailsPanel;
    @FXML private Label itemNameLabel;
    @FXML private GridPane itemDetailsGrid;

    private HBox currentlySelectedRoom;
    private HashMap<String, ArrayList<String>> roomItems = new HashMap<>();

    @FXML
    public void initialize() {
        itemsListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showItemDetails(newValue)
        );
    }

    @FXML
    private void addNewRoom() {
        TextInputDialog dialog = new TextInputDialog("New Room");
        dialog.setTitle("Add New Room");
        dialog.setHeaderText("Enter room name:");
        dialog.setContentText("Name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(roomName -> {
            HBox newRoomHBox = createRoomHBox(roomName);
            roomsList.getChildren().add(newRoomHBox);
            roomItems.put(roomName, new ArrayList<>());
        });
    }

    private HBox createRoomHBox(String roomName) {
        HBox newRoomHBox = new HBox();
        newRoomHBox.setSpacing(10);
        newRoomHBox.setAlignment(Pos.CENTER_LEFT);
        newRoomHBox.setOnMouseClicked(this::onRoomClicked);
        newRoomHBox.setPadding(new javafx.geometry.Insets(5, 10, 5, 10));

        Label roomLabel = new Label(roomName);
        roomLabel.setStyle("-fx-text-fill: white;");
        HBox.setHgrow(roomLabel, Priority.ALWAYS);

        Button optionsButton = new Button("...");
        optionsButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-opacity: 0;");
        optionsButton.setOnAction(e -> showRoomOptions(newRoomHBox, roomLabel));

        newRoomHBox.getChildren().addAll(roomLabel, optionsButton);

        newRoomHBox.setOnMouseEntered(e -> optionsButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-opacity: 1;"));
        newRoomHBox.setOnMouseExited(e -> optionsButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-opacity: 0;"));

        return newRoomHBox;
    }

    private void onRoomClicked(MouseEvent event) {
        HBox clickedRoom = (HBox) event.getSource();
        if (currentlySelectedRoom != null) {
            currentlySelectedRoom.setStyle("-fx-background-color: transparent;");
        }
        clickedRoom.setStyle("-fx-background-color: #454952;");
        currentlySelectedRoom = clickedRoom;

        String roomName = ((Label) clickedRoom.getChildren().get(0)).getText();
        selectedRoomLabel.setText(roomName);
        updateItemsList(roomName);
    }

    private void updateItemsList(String roomName) {
        ArrayList<String> items = roomItems.get(roomName);
        ObservableList<String> observableItems = FXCollections.observableArrayList(items);
        itemsListView.setItems(observableItems);
    }

    private void showItemDetails(String itemName) {
        if (itemName == null) return;

        itemNameLabel.setText(itemName);
        itemDetailsGrid.getChildren().clear();

        // Simulated item details
        addItemDetail("Category", "Furniture", 0);
        addItemDetail("Condition", "Excellent", 1);
        addItemDetail("Purchase Date", "Aug 4, 2006", 2);
        addItemDetail("Price", "$123.45", 3);
    }

    private void addItemDetail(String label, String value, int row) {
        itemDetailsGrid.add(new Label(label + ":"), 0, row);
        itemDetailsGrid.add(new Label(value), 1, row);
    }

    private void showRoomOptions(HBox roomHBox, Label roomLabel) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem renameItem = new MenuItem("Rename");
        MenuItem deleteItem = new MenuItem("Delete");
        MenuItem addItemItem = new MenuItem("Add Item");

        renameItem.setOnAction(e -> renameRoom(roomLabel));
        deleteItem.setOnAction(e -> deleteRoom(roomHBox));
        addItemItem.setOnAction(e -> addItemToRoom(roomLabel.getText()));

        contextMenu.getItems().addAll(renameItem, deleteItem, addItemItem);
        contextMenu.show(roomHBox, javafx.geometry.Side.BOTTOM, 0, 0);
    }

    private void renameRoom(Label roomLabel) {
        TextInputDialog dialog = new TextInputDialog(roomLabel.getText());
        dialog.setTitle("Rename Room");
        dialog.setHeaderText("Enter new room name:");
        dialog.setContentText("Name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newName -> {
            String oldName = roomLabel.getText();
            roomLabel.setText(newName);
            ArrayList<String> items = roomItems.remove(oldName);
            roomItems.put(newName, items);
            if (selectedRoomLabel.getText().equals(oldName)) {
                selectedRoomLabel.setText(newName);
            }
        });
    }

    private void deleteRoom(HBox roomHBox) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Room");
        alert.setHeaderText("Are you sure you want to delete this room?");
        alert.setContentText("This action cannot be undone.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String roomName = ((Label) roomHBox.getChildren().get(0)).getText();
            roomsList.getChildren().remove(roomHBox);
            roomItems.remove(roomName);
            if (currentlySelectedRoom == roomHBox) {
                currentlySelectedRoom = null;
                selectedRoomLabel.setText("");
                itemsListView.getItems().clear();
            }
        }
    }

    private void addItemToRoom(String roomName) {
        TextInputDialog dialog = new TextInputDialog("New Item");
        dialog.setTitle("Add New Item");
        dialog.setHeaderText("Enter item name:");
        dialog.setContentText("Name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(itemName -> {
            roomItems.get(roomName).add(itemName);
            if (selectedRoomLabel.getText().equals(roomName)) {
                updateItemsList(roomName);
            }
        });
    }
}