module com.example.addressbook {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires javafx.swing;


    opens com.example.addressbook to javafx.fxml;
    exports com.example.addressbook;
}