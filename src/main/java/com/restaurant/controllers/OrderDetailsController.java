package com.restaurant.controllers;

import com.restaurant.models.MenuItem;
import com.restaurant.models.Order;
import com.restaurant.utils.AppConstants;
import com.restaurant.utils.MoneyUtils;
import com.restaurant.utils.NavigationManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * Controller for order details.
 *
 * Role: Displays backend-provided order data, including decorator output from
 * MenuItem descriptions and backend totals.
 */
public class OrderDetailsController {
    @FXML
    private Label orderIdLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private Label totalLabel;

    @FXML
    private ListView<String> itemsList;

    private final RestaurantUiContext uiContext = RestaurantUiContext.getInstance();

    @FXML
    private void initialize() {
        uiContext.getSelectedOrder().ifPresent(this::renderOrder);
    }

    @FXML
    private void handleBackToOrders() {
        NavigationManager.show(AppConstants.ORDERS_FXML);
    }

    private void renderOrder(Order order) {
        orderIdLabel.setText(order.getId().toString());
        statusLabel.setText(order.getStatus().name());
        totalLabel.setText(MoneyUtils.format(order.getTotal()));
        itemsList.getItems().setAll(order.getItems().stream()
                .map(this::formatItem)
                .toList());
    }

    private String formatItem(MenuItem item) {
        return item.getName() + " | " + item.getDescription() + " | " + MoneyUtils.format(item.getPrice());
    }
}
