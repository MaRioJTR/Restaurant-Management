package com.restaurant.utils;

import com.restaurant.patterns.singleton.AppConfig;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

/**
 * JavaFX navigation helper.
 *
 * Role: Centralizes screen changes and a small transition effect so controllers
 * do not duplicate scene-loading code.
 */
public final class NavigationManager {
    private static final AppConfig config = AppConfig.getInstance();
    private static Stage primaryStage;

    private NavigationManager() {
    }

    public static void initialize(Stage stage) {
        primaryStage = stage;
    }

    public static void show(String fxmlPath) {
        if (primaryStage == null) {
            throw new IllegalStateException("NavigationManager has not been initialized.");
        }

        try {
            Parent root = FXMLLoader.load(NavigationManager.class.getResource(fxmlPath));
            primaryStage.setScene(new Scene(root, 980, 640));
            applySettings(root);
            FadeTransition transition = new FadeTransition(Duration.millis(180), root);
            transition.setFromValue(0.45);
            transition.setToValue(1.0);
            transition.play();
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load screen: " + fxmlPath, exception);
        }
    }

    public static String loadTheme() {
        return config.getApplicationTheme();
    }

    public static void applySettings(Parent root) {
        root.getProperties().put("applicationTheme", loadTheme());
    }
}
