package com.example.addressbook;

import javafx.fxml.FXML;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import java.util.Optional;

public class ItemsController {

    @FXML
    private VBox sideMenu;

    @FXML
    private Button addRoomButton;

    @FXML
    private HBox roomsItem;

    private HBox currentlySelected;

    @FXML
    public void initialize() {
        currentlySelected = roomsItem;
        highlightSelected(roomsItem);
    }

    @FXML
    private void addNewRoom() {
        HBox newRoomHBox = createRoomHBox("New Room");
        sideMenu.getChildren().add(newRoomHBox);
        onItemClicked(new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, null, 0, false, false, false, false, true, false, false, false, false, false, null));
    }

    private HBox createRoomHBox(String roomName) {
        HBox newRoomHBox = new HBox();
        newRoomHBox.setSpacing(10);
        newRoomHBox.setAlignment(Pos.CENTER_LEFT);
        newRoomHBox.setOnMouseClicked(this::onItemClicked);
        newRoomHBox.setPadding(new javafx.geometry.Insets(5, 10, 5, 10));

        Label roomLabel = new Label(roomName);
        roomLabel.setStyle("-fx-text-fill: white;");
        HBox.setHgrow(roomLabel, Priority.ALWAYS);

        Button optionsButton = new Button("...");
        optionsButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-opacity: 0;");
        optionsButton.setOnAction(e -> showRoomOptions(newRoomHBox, roomLabel));

        newRoomHBox.getChildren().addAll(roomLabel, optionsButton);

        // Add hover effect
        newRoomHBox.setOnMouseEntered(e -> optionsButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-opacity: 1;"));
        newRoomHBox.setOnMouseExited(e -> optionsButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-opacity: 0;"));

        return newRoomHBox;
    }

    private void showRoomOptions(HBox roomHBox, Label roomLabel) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem renameItem = new MenuItem("Rename");
        MenuItem deleteItem = new MenuItem("Delete");

        renameItem.setOnAction(e -> renameRoom(roomLabel));
        deleteItem.setOnAction(e -> deleteRoom(roomHBox));

        contextMenu.getItems().addAll(renameItem, deleteItem);
        contextMenu.show(roomHBox, javafx.geometry.Side.BOTTOM, 0, 0);
    }

    private void renameRoom(Label roomLabel) {
        TextInputDialog dialog = new TextInputDialog(roomLabel.getText());
        dialog.setTitle("Rename Room");
        dialog.setHeaderText("Enter new room name:");
        dialog.setContentText("Name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newName -> roomLabel.setText(newName));
    }

    private void deleteRoom(HBox roomHBox) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Room");
        alert.setHeaderText("Are you sure you want to delete this room?");
        alert.setContentText("This action cannot be undone.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            sideMenu.getChildren().remove(roomHBox);
            if (currentlySelected == roomHBox) {
                currentlySelected = null;
            }
        }
    }

    @FXML
    private void onItemClicked(MouseEvent event) {
        HBox clickedItem = (HBox) event.getSource();
        if (currentlySelected != null) {
            currentlySelected.setStyle("-fx-background-color: transparent;");
        }
        highlightSelected(clickedItem);
        currentlySelected = clickedItem;
    }

    private void highlightSelected(HBox item) {
        item.setStyle("-fx-background-color: #454952;");
    }
}