package com.example.addressbook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class TagController {

    @FXML
    private TextField tagNameField;

    @FXML
    private TableView<Tag> tagsTable;

    @FXML
    private TableColumn<Tag, String> tagNameColumn;

    private TagDB tagDB;
    private ObservableList<Tag> tagList;

    public TagController() {
        tagDB = new TagDB();
        tagList = FXCollections.observableArrayList(tagDB.getAllTags());
    }

    @FXML
    public void initialize() {
        // Bind the tag column to the tagName property
        tagNameColumn.setCellValueFactory(new PropertyValueFactory<>("tagName"));

        // Populate the table with existing tags
        tagsTable.setItems(tagList);
    }

    // Handle adding a new tag
    @FXML
    private void handleAddTag() {
        String tagName = tagNameField.getText();
        if (tagName.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Tag name cannot be empty.");
            return;
        }

        // Add the tag to the database
        if (tagDB.addTag(tagName)) {
            tagList.add(new Tag(tagName));
            tagNameField.clear();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Tag added successfully.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to add tag.");
        }
    }

    // Handle deleting a selected tag
    @FXML
    private void handleDeleteTag() {
        Tag selectedTag = tagsTable.getSelectionModel().getSelectedItem();
        if (selectedTag == null) {
            showAlert(Alert.AlertType.ERROR, "Selection Error", "Please select a tag to delete.");
            return;
        }

        // Delete the tag from the database
        if (tagDB.deleteTag(selectedTag.getId())) {
            tagList.remove(selectedTag);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Tag deleted successfully.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to delete tag.");
        }
    }

    // Handle editing a selected tag
    @FXML
    private void handleEditTag() {
        Tag selectedTag = tagsTable.getSelectionModel().getSelectedItem();
        if (selectedTag == null) {
            showAlert(Alert.AlertType.ERROR, "Selection Error", "Please select a tag to edit.");
            return;
        }

        String newTagName = tagNameField.getText();
        if (newTagName.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Tag name cannot be empty.");
            return;
        }

        // Update the tag in the database
        if (tagDB.updateTag(selectedTag.getId(), newTagName)) {
            selectedTag.setTagName(newTagName);
            tagsTable.refresh();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Tag updated successfully.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to update tag.");
        }
    }

    // Utility method to show alerts
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
