package com.ctrlaltz.inventorymanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class SearchController {
    @FXML private TextField searchField;
    @FXML private Button searchButton;

    @FXML
    private void onsearchButtonClick(){
        System.out.println("searchbutton clicked");
        String query = searchField.getText();
        ItemDB searchDB = new ItemDB();
        List<Item> items = searchDB.searchLikeName(query);
        System.out.println(items);

    }
}
