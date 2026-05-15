package com.restaurant.controllers;

import com.restaurant.models.MenuItem;
import com.restaurant.models.Order;
import com.restaurant.models.OrderStatus;
import com.restaurant.models.PaymentReceipt;
import com.restaurant.patterns.strategy.CashPayment;
import com.restaurant.patterns.strategy.CreditCardPayment;
import com.restaurant.patterns.strategy.WalletPayment;
import com.restaurant.utils.AppConstants;
import com.restaurant.utils.MoneyUtils;
import com.restaurant.utils.NavigationManager;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.util.Duration;

/**
 * Controller for payments.
 *
 * Role: Presents order summary and delegates payment to PaymentService.
 * Pattern use: Strategy remains inside PaymentService via selected strategy.
 */
public class PaymentController {
    @FXML
    private ComboBox<Order> orderSelector;

    @FXML
    private ListView<String> summaryList;

    @FXML
    private Label totalLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private Button cardMethodButton;

    @FXML
    private Button cashMethodButton;

    @FXML
    private Button walletMethodButton;

    @FXML
    private Button confirmPaymentButton;

    private final RestaurantUiContext uiContext = RestaurantUiContext.getInstance();
    private String selectedMethod = "CARD";

    @FXML
    private void initialize() {
        orderSelector.setItems(uiContext.getOrders().filtered(order ->
                order.getStatus() != OrderStatus.PAID
                        && order.getStatus() != OrderStatus.CANCELLED
                        && order.getStatus() != OrderStatus.DONE));
        orderSelector.setConverter(new javafx.util.StringConverter<>() {
            @Override
            public String toString(Order order) {
                return order == null ? "" : order.getId().toString().substring(0, 8) + " | Table " + order.getTableNumber();
            }

            @Override
            public Order fromString(String value) {
                return null;
            }
        });
        uiContext.getSelectedOrder().ifPresent(orderSelector::setValue);
        orderSelector.valueProperty().addListener((observable, oldValue, newValue) -> renderOrder(newValue));
        renderOrder(orderSelector.getValue());
        selectCard();
    }

    @FXML
    private void selectCard() {
        selectedMethod = "CARD";
        styleMethods();
    }

    @FXML
    private void selectCash() {
        selectedMethod = "CASH";
        styleMethods();
    }

    @FXML
    private void selectWallet() {
        selectedMethod = "WALLET";
        styleMethods();
    }

    @FXML
    private void handleConfirmPayment() {
        Order order = orderSelector.getValue();
        if (order == null) {
            return;
        }
        confirmPaymentButton.setDisable(true);
        statusLabel.setText("Processing payment...");
        PauseTransition loading = new PauseTransition(Duration.millis(550));
        loading.setOnFinished(event -> {
            applySelectedStrategy();
            PaymentReceipt receipt = uiContext.getPaymentService().processPayment(order);
            uiContext.upsertOrder(order);
            statusLabel.setText("Paid via " + receipt.getPaymentMethod());
            totalLabel.setText(MoneyUtils.format(receipt.getAmount()));
            confirmPaymentButton.setDisable(false);
        });
        loading.play();
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
    private void handleKitchen() {
        NavigationManager.show(AppConstants.KITCHEN_FXML);
    }

    private void renderOrder(Order order) {
        summaryList.getItems().clear();
        if (order == null) {
            totalLabel.setText(MoneyUtils.format(java.math.BigDecimal.ZERO));
            statusLabel.setText("Select an order to pay.");
            return;
        }
        summaryList.getItems().setAll(order.getItems().stream().map(this::formatItem).toList());
        totalLabel.setText(MoneyUtils.format(order.getTotal()));
        statusLabel.setText(order.getStatus().name());
    }

    private void applySelectedStrategy() {
        switch (selectedMethod) {
            case "CASH" -> uiContext.getPaymentService().setPaymentStrategy(new CashPayment());
            case "WALLET" -> uiContext.getPaymentService().setPaymentStrategy(new WalletPayment("HousePay"));
            default -> uiContext.getPaymentService().setPaymentStrategy(new CreditCardPayment("**** 4242"));
        }
    }

    private String formatItem(MenuItem item) {
        return item.getName() + " | " + item.getDescription() + " | " + MoneyUtils.format(item.getPrice());
    }

    private void styleMethods() {
        styleMethod(cardMethodButton, "CARD".equals(selectedMethod));
        styleMethod(cashMethodButton, "CASH".equals(selectedMethod));
        styleMethod(walletMethodButton, "WALLET".equals(selectedMethod));
    }

    private void styleMethod(Button button, boolean selected) {
        button.setStyle(selected
                ? "-fx-background-color: #1d4ed8; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 16;"
                : "-fx-background-color: white; -fx-text-fill: #111827; -fx-padding: 16; -fx-effect: dropshadow(gaussian, rgba(24,32,56,0.08), 12, 0, 0, 3);");
    }
}
