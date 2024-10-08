module com.ctrlaltz.inventorymanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires javafx.swing;
    requires com.google.gson;


    opens com.ctrlaltz.inventorymanager to javafx.fxml;
    exports com.ctrlaltz.inventorymanager;
}