package com.ctrlaltz.inventorymanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static final String TITLE = "Home Inventory Manager";
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 600;
    private static final int LOGIN_WIDTH = 340;
    private static final int LOGIN_HEIGHT = 600;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login-Register-New.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), LOGIN_WIDTH, LOGIN_HEIGHT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
