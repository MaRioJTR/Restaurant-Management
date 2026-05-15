package com.restaurant.controllers;

import com.restaurant.models.MenuItem;
import com.restaurant.models.Order;
import com.restaurant.models.OrderStatus;
import com.restaurant.patterns.command.CancelOrderCommand;
import com.restaurant.patterns.command.OrderInvoker;
import com.restaurant.patterns.command.PlaceOrderCommand;
import com.restaurant.patterns.observer.KitchenDisplay;
import com.restaurant.patterns.observer.OrderObserver;
import com.restaurant.patterns.observer.WaiterUI;
import com.restaurant.services.OrderService;
import com.restaurant.utils.AppConstants;
import com.restaurant.utils.MoneyUtils;
import com.restaurant.utils.NavigationManager;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * JavaFX controller for the dashboard view.
 *
 * Role: Handles UI events and delegates work to commands/services.
 * Pattern: MVC controller. It demonstrates Factory Method, Decorator, Command,
 * Observer, and Strategy without embedding business rules in FXML.
 */
public class DashboardController implements OrderObserver {
    @FXML
    private Label orderTotalLabel;

    @FXML
    private Label kitchenStatusLabel;

    @FXML
    private Label refreshStatusLabel;

    @FXML
    private TableView<Order> ordersTable;

    @FXML
    private TableColumn<Order, String> orderIdColumn;

    @FXML
    private TableColumn<Order, String> tableColumn;

    @FXML
    private TableColumn<Order, String> itemsColumn;

    @FXML
    private TableColumn<Order, String> totalColumn;

    @FXML
    private TableColumn<Order, String> statusColumn;

    @FXML
    private TableColumn<Order, Void> actionsColumn;

    private final KitchenDisplay kitchenDisplay = new KitchenDisplay();
    private final WaiterUI waiterUI = new WaiterUI();
    private final RestaurantUiContext uiContext = RestaurantUiContext.getInstance();
    private final OrderService orderService = uiContext.getOrderService();
    private final OrderInvoker orderInvoker = uiContext.getOrderInvoker();
    private final ObservableList<Order> orders = uiContext.getOrders();

    @FXML
    private void initialize() {
        orderService.addObserver(kitchenDisplay);
        orderService.addObserver(waiterUI);
        orderService.addObserver(this);
        configureOrdersTable();
        ordersTable.setItems(orders);
        orderTotalLabel.setText(MoneyUtils.format(BigDecimal.ZERO));
        kitchenStatusLabel.setText(kitchenDisplay.getLatestMessage());
    }

    @FXML
    private void handlePlaceSampleOrder() {
        MenuItem customizedBurger = uiContext.getMenuService().getFoodItems().get(0);
        Order order = new Order(7, List.of(customizedBurger));

        orderInvoker.submit(new PlaceOrderCommand(orderService, order));
        orderInvoker.executeAll();

        updateDashboard(order);
    }

    @Override
    public void update(Order order) {
        Platform.runLater(() -> {
            upsertOrder(order);
            kitchenStatusLabel.setText(kitchenDisplay.getLatestMessage());
            refreshStatusLabel.setText("Observer refresh: " + order.getStatus());
            animateRefresh();
        });
    }

    @FXML
    private void handleNavigateOrders() {
        NavigationManager.show(AppConstants.ORDERS_FXML);
    }

    @FXML
    private void handleNavigateMenu() {
        NavigationManager.show(AppConstants.MENU_FXML);
    }

    @FXML
    private void handleNavigatePayments() {
        uiContext.getOrders().stream()
                .filter(order -> order.getStatus() != OrderStatus.PAID && order.getStatus() != OrderStatus.CANCELLED)
                .findFirst()
                .ifPresent(uiContext::setSelectedOrder);
        NavigationManager.show(AppConstants.PAYMENT_FXML);
    }

    @FXML
    private void handleNavigateKitchen() {
        NavigationManager.show(AppConstants.KITCHEN_FXML);
    }

    private void configureOrdersTable() {
        orderIdColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getId().toString().substring(0, 8)));
        tableColumn.setCellValueFactory(data ->
                new SimpleStringProperty(String.valueOf(data.getValue().getTableNumber())));
        itemsColumn.setCellValueFactory(data ->
                new SimpleStringProperty(String.valueOf(data.getValue().getItems().size())));
        totalColumn.setCellValueFactory(data ->
                new SimpleStringProperty(MoneyUtils.format(data.getValue().getTotal())));
        statusColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getStatus().name()));
        actionsColumn.setCellFactory(column -> new TableCell<>() {
            private final Button detailsButton = createSecondaryButton("View Details");
            private final Button payButton = createPrimaryButton("Pay Order");
            private final Button cancelButton = createDangerButton("Cancel");
            private final HBox actions = new HBox(6, detailsButton, payButton, cancelButton);

            {
                detailsButton.setOnAction(event -> getCurrentOrder().ifPresent(DashboardController.this::showOrderDetails));
                payButton.setOnAction(event -> getCurrentOrder().ifPresent(DashboardController.this::payOrder));
                cancelButton.setOnAction(event -> getCurrentOrder().ifPresent(DashboardController.this::cancelOrder));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    return;
                }

                Order order = getTableView().getItems().get(getIndex());
                payButton.setDisable(order.getStatus() == OrderStatus.PAID
                        || order.getStatus() == OrderStatus.CANCELLED
                        || order.getStatus() == OrderStatus.DONE);
                cancelButton.setDisable(order.getStatus() == OrderStatus.CANCELLED
                        || order.getStatus() == OrderStatus.PAID
                        || order.getStatus() == OrderStatus.DONE);
                setGraphic(actions);
            }

            private Optional<Order> getCurrentOrder() {
                int rowIndex = getIndex();
                if (rowIndex < 0 || rowIndex >= getTableView().getItems().size()) {
                    return Optional.empty();
                }
                return Optional.of(getTableView().getItems().get(rowIndex));
            }
        });

        ordersTable.setRowFactory(table -> new TableRow<>() {
            @Override
            protected void updateItem(Order order, boolean empty) {
                super.updateItem(order, empty);
                if (empty || order == null) {
                    setStyle("");
                    return;
                }
                setStyle(statusRowStyle(order.getStatus()));
            }
        });
    }

    private void updateDashboard(Order order) {
        upsertOrder(order);
        orderTotalLabel.setText(MoneyUtils.format(order.getTotal()));
        kitchenStatusLabel.setText(kitchenDisplay.getLatestMessage());
        refreshStatusLabel.setText("Order placed through command");
        ordersTable.refresh();
        animateRefresh();
    }

    private void payOrder(Order order) {
        uiContext.setSelectedOrder(order);
        NavigationManager.show(AppConstants.PAYMENT_FXML);
    }

    private void cancelOrder(Order order) {
        orderInvoker.submit(new CancelOrderCommand(orderService, order));
        orderInvoker.executeAll();
        upsertOrder(order);
        refreshStatusLabel.setText("Order cancelled through command");
        ordersTable.refresh();
        animateRefresh();
    }

    private void showOrderDetails(Order order) {
        uiContext.setSelectedOrder(order);
        NavigationManager.show(AppConstants.ORDER_DETAILS_FXML);
    }

    private void upsertOrder(Order order) {
        int existingIndex = -1;
        for (int index = 0; index < orders.size(); index++) {
            if (orders.get(index).getId().equals(order.getId())) {
                existingIndex = index;
                break;
            }
        }

        if (existingIndex >= 0) {
            orders.set(existingIndex, order);
        } else {
            orders.add(order);
        }
    }

    private void animateRefresh() {
        FadeTransition transition = new FadeTransition(Duration.millis(220), refreshStatusLabel);
        transition.setFromValue(0.25);
        transition.setToValue(1.0);
        transition.play();
    }

    private String statusRowStyle(OrderStatus status) {
        return switch (status) {
            case NEW -> "-fx-background-color: #dbeafe;";
            case PLACED -> "-fx-background-color: #ffedd5;";
            case PAID -> "-fx-background-color: #dcfce7;";
            case CANCELLED -> "-fx-background-color: #fee2e2;";
            case DONE -> "-fx-background-color: #e5e7eb;";
        };
    }

    private Button createPrimaryButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #1d4ed8; -fx-text-fill: white; -fx-font-weight: bold;");
        return button;
    }

    private Button createSecondaryButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #eef2ff; -fx-text-fill: #1d4ed8;");
        return button;
    }

    private Button createDangerButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #dc2626; -fx-text-fill: white; -fx-font-weight: bold;");
        return button;
    }
}
