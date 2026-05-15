package com.restaurant.app;

import com.restaurant.utils.AppConstants;
import com.restaurant.utils.NavigationManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX entry point for the Restaurant Management System.
 *
 * Role: Starts the desktop application and loads the MVC view.
 * Pattern: MVC bootstrap. It intentionally contains no business logic so UI,
 * controller, and services remain separated.
 */
public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        NavigationManager.initialize(stage);
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(AppConstants.DASHBOARD_FXML));
        Scene scene = new Scene(loader.load(), 980, 640);
        stage.setTitle(AppConstants.APPLICATION_NAME);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
