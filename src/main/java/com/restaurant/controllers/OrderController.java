package com.restaurant.controllers;

import com.restaurant.models.Order;
import com.restaurant.models.OrderStatus;
import com.restaurant.patterns.command.CancelOrderCommand;
import com.restaurant.patterns.command.OrderInvoker;
import com.restaurant.patterns.observer.OrderObserver;
import com.restaurant.services.OrderService;
import com.restaurant.utils.AppConstants;
import com.restaurant.utils.MoneyUtils;
import com.restaurant.utils.NavigationManager;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

import java.util.Optional;

/**
 * Controller for order management.
 *
 * Role: Presents all orders and delegates row actions to existing services and
 * commands. Pattern use: Command for cancellation, Observer for refresh.
 */
public class OrderController implements OrderObserver {
    @FXML
    private ComboBox<String> statusFilter;

    @FXML
    private TableView<Order> ordersTable;

    @FXML
    private TableColumn<Order, String> orderIdColumn;

    @FXML
    private TableColumn<Order, String> tableColumn;

    @FXML
    private TableColumn<Order, String> itemCountColumn;

    @FXML
    private TableColumn<Order, String> totalColumn;

    @FXML
    private TableColumn<Order, String> statusColumn;

    @FXML
    private TableColumn<Order, Void> actionsColumn;

    private final RestaurantUiContext uiContext = RestaurantUiContext.getInstance();
    private final OrderService orderService = uiContext.getOrderService();
    private final OrderInvoker orderInvoker = uiContext.getOrderInvoker();
    private FilteredList<Order> filteredOrders;

    @FXML
    private void initialize() {
        orderService.addObserver(this);
        statusFilter.getItems().setAll("PLACED", "PAID", "DONE", "CANCELLED");
        statusFilter.setValue("PLACED");
        filteredOrders = new FilteredList<>(uiContext.getOrders(), order -> true);
        statusFilter.valueProperty().addListener((observable, oldValue, newValue) -> applyFilter(newValue));
        configureTable();
        ordersTable.setItems(filteredOrders);
    }

    @Override
    public void update(Order order) {
        Platform.runLater(() -> {
            uiContext.upsertOrder(order);
            ordersTable.refresh();
        });
    }

    @FXML
    private void handleDashboard() {
        NavigationManager.show(AppConstants.DASHBOARD_FXML);
    }

    @FXML
    private void handleMenu() {
        NavigationManager.show(AppConstants.MENU_FXML);
    }

    @FXML
    private void handlePayments() {
        NavigationManager.show(AppConstants.PAYMENT_FXML);
    }

    @FXML
    private void handleKitchen() {
        NavigationManager.show(AppConstants.KITCHEN_FXML);
    }

    private void configureTable() {
        orderIdColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId().toString().substring(0, 8)));
        tableColumn.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getTableNumber())));
        itemCountColumn.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getItems().size())));
        totalColumn.setCellValueFactory(data -> new SimpleStringProperty(MoneyUtils.format(data.getValue().getTotal())));
        statusColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus().name()));
        actionsColumn.setCellFactory(column -> new TableCell<>() {
            private final Button detailsButton = button("View", "#eef2ff", "#1d4ed8");
            private final Button payButton = button("Pay", "#1d4ed8", "white");
            private final Button cancelButton = button("Cancel", "#dc2626", "white");
            private final HBox actions = new HBox(6, detailsButton, payButton, cancelButton);

            {
                detailsButton.setOnAction(event -> currentOrder().ifPresent(OrderController.this::viewDetails));
                payButton.setOnAction(event -> currentOrder().ifPresent(OrderController.this::payOrder));
                cancelButton.setOnAction(event -> currentOrder().ifPresent(OrderController.this::cancelOrder));
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
                cancelButton.setDisable(order.getStatus() == OrderStatus.PAID
                        || order.getStatus() == OrderStatus.CANCELLED
                        || order.getStatus() == OrderStatus.DONE);
                setGraphic(actions);
            }

            private Optional<Order> currentOrder() {
                int index = getIndex();
                if (index < 0 || index >= getTableView().getItems().size()) {
                    return Optional.empty();
                }
                return Optional.of(getTableView().getItems().get(index));
            }
        });
        ordersTable.setRowFactory(table -> new TableRow<>() {
            @Override
            protected void updateItem(Order order, boolean empty) {
                super.updateItem(order, empty);
                setStyle(empty || order == null ? "" : statusRowStyle(order.getStatus()));
            }
        });
    }

    private void applyFilter(String status) {
        filteredOrders.setPredicate(order -> order.getStatus().name().equals(status));
    }

    private void viewDetails(Order order) {
        uiContext.setSelectedOrder(order);
        NavigationManager.show(AppConstants.ORDER_DETAILS_FXML);
    }

    private void payOrder(Order order) {
        uiContext.setSelectedOrder(order);
        NavigationManager.show(AppConstants.PAYMENT_FXML);
    }

    private void cancelOrder(Order order) {
        orderInvoker.submit(new CancelOrderCommand(orderService, order));
        orderInvoker.executeAll();
        uiContext.upsertOrder(order);
        ordersTable.refresh();
    }

    private Button button(String text, String background, String foreground) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: " + background + "; -fx-text-fill: " + foreground + "; -fx-font-weight: bold;");
        return button;
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
}
