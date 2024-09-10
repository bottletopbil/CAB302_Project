package com.example.addressbook;

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
import java.util.Optional;
import java.util.HashMap;
import java.util.ArrayList;

public class ItemsController {

    @FXML private VBox sideMenu;
    @FXML private VBox roomsList;
    @FXML private Button addRoomButton;
    @FXML private VBox itemsPanel;
    @FXML private Label selectedRoomLabel;
    @FXML private ListView<Item> itemsListView;
    @FXML private VBox itemDetailsPanel;
    @FXML private Label itemNameLabel;
    @FXML private GridPane itemDetailsGrid;
    @FXML private ImageView itemImageView;

    private HBox currentlySelectedRoom;
    private HashMap<String, ArrayList<Item>> roomItems = new HashMap<>();

    @FXML
    public void initialize() {
        itemsListView.setCellFactory(param -> new ListCell<Item>() {
            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });

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
        ArrayList<Item> items = roomItems.get(roomName);
        ObservableList<Item> observableItems = FXCollections.observableArrayList(items);
        itemsListView.setItems(observableItems);
    }

    private void showItemDetails(Item item) {
        if (item == null) return;

        itemNameLabel.setText(item.getName());
        itemDetailsGrid.getChildren().clear();

        addItemDetail("Brand", item.getBrand(), 0);
        addItemDetail("Price", String.format("$%.2f", item.getPrice()), 1);
        addItemDetail("Tags", item.getTags(), 2);
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
            ArrayList<Item> items = roomItems.remove(oldName);
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
        Dialog<Item> dialog = new Dialog<>();
        dialog.setTitle("Add New Item");
        dialog.setHeaderText("Enter item details:");

        // Set the button types
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        // Create the form fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));

        TextField name = new TextField();
        TextField brand = new TextField();
        TextField price = new TextField();
        TextField tags = new TextField();
        TextField warranty = new TextField();
        TextField quantity = new TextField();
        ComboBox<String> condition = new ComboBox<>(FXCollections.observableArrayList(
                "New", "Excellent", "Good", "Fair", "Poor"
        ));
        Button uploadPhoto = new Button("Upload Photo");
        Label photoLabel = new Label("No photo selected");

        grid.add(new Label("Name:"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label("Brand:"), 0, 1);
        grid.add(brand, 1, 1);
        grid.add(new Label("Price:"), 0, 2);
        grid.add(price, 1, 2);
        grid.add(new Label("Tags:"), 0, 3);
        grid.add(tags, 1, 3);
        grid.add(new Label("Warranty:"), 0, 4);
        grid.add(warranty, 1, 4);
        grid.add(new Label("Quantity:"), 0, 5);
        grid.add(quantity, 1, 5);
        grid.add(new Label("Condition:"), 0, 6);
        grid.add(condition, 1, 6);
        grid.add(uploadPhoto, 0, 7);
        grid.add(photoLabel, 1, 7);

        dialog.getDialogPane().setContent(grid);

        // Image upload handling
        final Image[] selectedImage = {null};
        uploadPhoto.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Item Photo");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
            );
            File selectedFile = fileChooser.showOpenDialog(dialog.getOwner());
            if (selectedFile != null) {
                selectedImage[0] = new Image(selectedFile.toURI().toString());
                photoLabel.setText(selectedFile.getName());
            }
        });

        // Enable/Disable add button depending on whether a name was entered
        javafx.scene.Node addButton = dialog.getDialogPane().lookupButton(addButtonType);
        addButton.setDisable(true);

        // Do some validation
        name.textProperty().addListener((observable, oldValue, newValue) -> {
            addButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                try {
                    double priceValue = Double.parseDouble(price.getText());
                    int quantityValue = Integer.parseInt(quantity.getText());
                    return new Item(name.getText(), brand.getText(), priceValue, tags.getText(),
                            warranty.getText(), quantityValue, condition.getValue(), selectedImage[0]);
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Invalid Input");
                    alert.setHeaderText("Please enter valid numbers for price and quantity.");
                    alert.showAndWait();
                    return null;
                }
            }
            return null;
        });

        Optional<Item> result = dialog.showAndWait();

        result.ifPresent(item -> {
            roomItems.get(roomName).add(item);
            if (selectedRoomLabel.getText().equals(roomName)) {
                updateItemsList(roomName);
            }
        });
    }
}