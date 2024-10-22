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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
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
    private Integer userId;

    private HBox currentlySelectedRoom;
    private HashMap<String, ArrayList<Item>> roomItems = new HashMap<>();

    @FXML
    public void initialize() {
        UserHolder userHolder = UserHolder.getInstance();
        userId = userHolder.getUser();

        RoomDB roomDB = new RoomDB();
        roomDB.initializeTable();
        ItemDB itemDB = new ItemDB();
        itemDB.initializeTable();
        TagDB tagDB = new TagDB();
        tagDB.initializeTable();
        /*ItemTagDB itDB = new ItemTagDB();
        itDB.initializeTable();*/

        List<Room> tempRoomsList = roomDB.getRoomsByUserID(userId);
        if (!tempRoomsList.isEmpty())
        {
            for (Room room : tempRoomsList) {

                HBox newRoomHBox = createRoomHBox(room.getName(), userId);
                roomsList.getChildren().add(newRoomHBox);
                roomItems.put(room.getName(), new ArrayList<>());
                List<Item> tempItemList = itemDB.getItemsByID(userId, room.getId());

                if (!tempItemList.isEmpty())
                {
                    for (Item item : tempItemList)
                    {
                        roomItems.get(room.getName()).add(item);
                        if (selectedRoomLabel.getText().equals(room.getName())) {
                            updateItemsList(room.getName());
                        }
                    }
                }
            }
        }

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

        RoomDB roomDB = new RoomDB();

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(roomName -> {
            Room room = new Room(userId, roomName, LocalDateTime.now().toString());
            HBox newRoomHBox = createRoomHBox(roomName, userId);
            roomsList.getChildren().add(newRoomHBox);
            roomItems.put(roomName, new ArrayList<>());
            roomDB.insert(room);
        });
    }

    private HBox createRoomHBox(String roomName, Integer userId) {
        HBox newRoomHBox = new HBox();
        newRoomHBox.setSpacing(10);
        newRoomHBox.setAlignment(Pos.CENTER_LEFT);
        newRoomHBox.setOnMouseClicked(this::onRoomClicked);
        newRoomHBox.setPadding(new javafx.geometry.Insets(5, 10, 5, 10));
        newRoomHBox.setUserData(userId);

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
        addItemDetail("Tags", item.getTagsToString(), 2);
        addItemDetail("Warranty", item.getWarranty(), 3);
        addItemDetail("Quantity", String.valueOf(item.getQuantity()), 4);
        addItemDetail("Condition", item.getCondition(), 5);

        if (item.hasPhoto()) {
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
        addItemItem.setOnAction(e -> addItemToRoom(roomLabel.getText(), (Integer) roomHBox.getUserData()));

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

    private void addItemToRoom(String roomName, Integer userId) {
        Dialog<Item> dialog = new Dialog<>();
        dialog.setTitle("Add New Item");
        dialog.setHeaderText("Enter item details:");

        RoomDB roomDB = new RoomDB();
        ItemDB itemDB = new ItemDB();
        Integer roomID = roomDB.getRoomByName(roomName, userId);


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
        TextField location = new TextField();
        TextField serial = new TextField();
        DatePicker date = new DatePicker();
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
        grid.add(new Label("Date of Purchase:"), 0, 6);
        grid.add(date, 1, 6);
        grid.add(new Label("Location of Purchase:"), 0, 7);
        grid.add(location, 1, 7);
        grid.add(new Label("Serial Number: "), 0, 8);
        grid.add(serial, 1, 8);
        grid.add(new Label("Condition:"), 0, 9);
        grid.add(condition, 1, 9);
        grid.add(uploadPhoto, 0, 10);
        grid.add(photoLabel, 1, 10);

        dialog.getDialogPane().setContent(grid);

        // Image upload handling
        final Image[] selectedImage = {null};
        final File[] imageFile = {null};


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
                imageFile[0] = selectedFile;
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

                    float priceValue;
                    String tempValue = price.getText();
                    if (tempValue.contains("$"))
                    {
                        String filteredString = tempValue.replace("$", "");
                        priceValue = Float.parseFloat(filteredString);
                    }
                    else
                    {
                        priceValue = Float.parseFloat(price.getText());
                    }

                    LocalDate localdateValue = date.getValue();
                    Date purchaseDate = Date.valueOf(localdateValue);
                    String purchaseLocation = location.getText();
                    String itemSerial = serial.getText();

                    int quantityValue = Integer.parseInt(quantity.getText());
                    LocalDateTime now = LocalDateTime.now();

                    // Define the format
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                    // Format the current date and time
                    String formattedNow = now.format(formatter);
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String purchaseDateString = df.format(purchaseDate);
                    //TODO: Item Description, Purchased Date, get current Date for Registration Date
                    //TODO: Actual tags integration
                    String[] tagsArr = new String[0];
                    return new Item(roomID, userId, name.getText(), brand.getText(), priceValue,
                            warranty.getText(), quantityValue, condition.getValue(), imageFile[0], "", itemSerial, purchaseLocation, purchaseDateString, formattedNow);
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
            itemDB.insert(item);
            roomItems.get(roomName).add(item);
            if (selectedRoomLabel.getText().equals(roomName)) {
                updateItemsList(roomName);
            }
        });
    }
}