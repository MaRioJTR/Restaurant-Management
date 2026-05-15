module com.restaurant {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.restaurant.app;
    exports com.restaurant.controllers;
    exports com.restaurant.models;
    exports com.restaurant.repositories;
    exports com.restaurant.services;
    exports com.restaurant.patterns.command;
    exports com.restaurant.patterns.decorator;
    exports com.restaurant.patterns.factory;
    exports com.restaurant.patterns.observer;
    exports com.restaurant.patterns.singleton;
    exports com.restaurant.patterns.strategy;
    exports com.restaurant.patterns.state;
    exports com.restaurant.utils;

    opens com.restaurant.controllers to javafx.fxml;
}
