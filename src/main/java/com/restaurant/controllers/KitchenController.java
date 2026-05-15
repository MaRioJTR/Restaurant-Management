package com.restaurant.controllers;

import com.restaurant.models.Order;
import com.restaurant.models.OrderStatus;
import com.restaurant.patterns.command.DoneOrderCommand;
import com.restaurant.patterns.observer.OrderObserver;
import com.restaurant.utils.AppConstants;
import com.restaurant.utils.MoneyUtils;
import com.restaurant.utils.NavigationManager;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controller for kitchen display.
 *
 * Role: Renders observer-fed order cards with structured sections.
 * Pattern use: Observer refresh is used for smooth real-time updates.
 */
public class KitchenController implements OrderObserver {
    @FXML
    private VBox cardsContainer;

    private final RestaurantUiContext uiContext = RestaurantUiContext.getInstance();

    @FXML
    private void initialize() {
        uiContext.getOrderService().addObserver(this);
        renderCards();
    }

    @Override
    public void update(Order order) {
        Platform.runLater(() -> {
            uiContext.upsertOrder(order);
            renderCards();
            animate();
        });
    }

    @FXML
    private void handleDashboard() {
        NavigationManager.show(AppConstants.DASHBOARD_FXML);
    }

    @FXML
    private void handleOrders() {
        NavigationManager.show(AppConstants.ORDERS_FXML);
    }

    @FXML
    private void handleMenu() {
        NavigationManager.show(AppConstants.MENU_FXML);
    }

    @FXML
    private void handlePayments() {
        NavigationManager.show(AppConstants.PAYMENT_FXML);
    }

    private void renderCards() {
        cardsContainer.getChildren().clear();
        uiContext.snapshotOrders().stream()
                .filter(order -> order.getStatus() != OrderStatus.DONE && order.getStatus() != OrderStatus.CANCELLED)
                .forEach(order -> cardsContainer.getChildren().add(cardFor(order)));
    }

    private VBox cardFor(Order order) {
        Label header = new Label("Order " + order.getId().toString().substring(0, 8) + " | " + order.getStatus());
        header.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #111827;");
        Label body = new Label(order.getItems().stream()
                .map(item -> item.getName() + " - " + item.getDescription())
                .reduce("", (left, right) -> left + System.lineSeparator() + right).trim());
        body.setWrapText(true);
        Label footer = new Label("Table " + order.getTableNumber() + " | " + order.getCreatedAt() + " | " + MoneyUtils.format(order.getTotal()));
        footer.setStyle("-fx-text-fill: #6b7280;");
        Button doneButton = new Button("Done!");
        doneButton.setDisable(order.getStatus() != OrderStatus.PAID);
        doneButton.setStyle("-fx-background-color: #16a34a; -fx-text-fill: white; -fx-font-weight: bold;");
        doneButton.setOnAction(event -> markDone(order));
        VBox card = new VBox(8, header, body, footer, doneButton);
        String border = switch (order.getStatus()) {
            case NEW -> "#1d4ed8";
            case PAID -> "#16a34a";
            default -> "#f97316";
        };
        card.setStyle("-fx-background-color: white; -fx-border-color: " + border + "; -fx-border-width: 0 0 0 5; -fx-padding: 14; -fx-background-radius: 8; -fx-effect: dropshadow(gaussian, rgba(24,32,56,0.10), 14, 0, 0, 3);");
        return card;
    }

    private void markDone(Order order) {
        uiContext.getOrderInvoker().submit(new DoneOrderCommand(uiContext.getOrderService(), order));
        uiContext.getOrderInvoker().executeAll();
        uiContext.upsertOrder(order);
        renderCards();
        animate();
    }

    private void animate() {
        FadeTransition transition = new FadeTransition(Duration.millis(220), cardsContainer);
        transition.setFromValue(0.35);
        transition.setToValue(1.0);
        transition.play();
    }
}
