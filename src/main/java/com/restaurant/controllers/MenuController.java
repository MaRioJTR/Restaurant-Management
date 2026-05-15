package com.restaurant.controllers;

import com.restaurant.models.MenuItem;
import com.restaurant.models.Order;
import com.restaurant.models.OrderStatus;
import com.restaurant.patterns.command.PlaceOrderCommand;
import com.restaurant.utils.AppConstants;
import com.restaurant.utils.MoneyUtils;
import com.restaurant.utils.NavigationManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

/**
 * Controller for menu management.
 *
 * Role: Lists factory-created menu items and builds orders through services.
 * Pattern use: Factory via MenuService, Decorator for selected customizations,
 * Command for placing the created order.
 */
public class MenuController {
    @FXML
    private TableView<MenuRow> menuTable;

    @FXML
    private TableColumn<MenuRow, String> nameColumn;

    @FXML
    private TableColumn<MenuRow, String> categoryColumn;

    @FXML
    private TableColumn<MenuRow, String> priceColumn;

    @FXML
    private TableView<MenuItem> cartTable;

    @FXML
    private TableColumn<MenuItem, String> cartItemColumn;

    @FXML
    private TableColumn<MenuItem, String> cartQuantityColumn;

    @FXML
    private TableColumn<MenuItem, String> cartTotalColumn;

    @FXML
    private Label cartTotalLabel;

    @FXML
    private ComboBox<Integer> tableSelector;

    @FXML
    private Label tableSelectionMessage;

    @FXML
    private Button placeOrderButton;

    @FXML
    private CheckMenuItem extraCheeseItem;

    @FXML
    private CheckMenuItem noOnionsItem;

    @FXML
    private CheckMenuItem extraSauceItem;

    private final RestaurantUiContext uiContext = RestaurantUiContext.getInstance();
    private final ObservableList<MenuRow> menuRows = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        loadMenuRows();
        configureMenuTable();
        configureCartTable();
        menuTable.setItems(menuRows);
        cartTable.setItems(uiContext.getCartItems());
        uiContext.getCartItems().addListener((javafx.collections.ListChangeListener<MenuItem>) change -> refreshCartTotal());
        uiContext.getOrders().addListener((javafx.collections.ListChangeListener<Order>) change -> refreshAvailableTables());
        refreshAvailableTables();
        refreshCartTotal();
    }

    @FXML
    private void handleAddSelectedItem() {
        MenuRow row = menuTable.getSelectionModel().getSelectedItem();
        if (row == null) {
            return;
        }
        uiContext.getCartItems().add(applyDecorators(row.item()));
    }

    @FXML
    private void handleRemoveCartItem() {
        MenuItem selected = cartTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            uiContext.getCartItems().remove(selected);
        }
    }

    @FXML
    private void handlePlaceOrder() {
        Integer selectedTable = tableSelector.getValue();
        if (selectedTable == null) {
            tableSelectionMessage.setText("Select an available table before placing the order.");
            return;
        }
        if (uiContext.getCartItems().isEmpty()) {
            tableSelectionMessage.setText("Add at least one item before placing the order.");
            return;
        }
        Order order = new Order(selectedTable, List.copyOf(uiContext.getCartItems()));
        uiContext.getOrderInvoker().submit(new PlaceOrderCommand(uiContext.getOrderService(), order));
        uiContext.getOrderInvoker().executeAll();
        uiContext.getCartItems().clear();
        tableSelector.setValue(null);
        refreshAvailableTables();
        NavigationManager.show(AppConstants.ORDERS_FXML);
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
    private void handlePayments() {
        NavigationManager.show(AppConstants.PAYMENT_FXML);
    }

    @FXML
    private void handleKitchen() {
        NavigationManager.show(AppConstants.KITCHEN_FXML);
    }

    private void loadMenuRows() {
        uiContext.getMenuService().getFoodItems().forEach(item -> {
            String category = item.getName().contains("Cake") ? "Desserts" : "Food";
            menuRows.add(new MenuRow(item, category));
        });
        uiContext.getMenuService().getDrinkItems().forEach(item -> menuRows.add(new MenuRow(item, "Drinks")));
    }

    private void configureMenuTable() {
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().item().getName()));
        categoryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().category()));
        priceColumn.setCellValueFactory(data -> new SimpleStringProperty(MoneyUtils.format(data.getValue().item().getPrice())));
    }

    private void configureCartTable() {
        cartItemColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        cartQuantityColumn.setCellValueFactory(data -> new SimpleStringProperty("1"));
        cartTotalColumn.setCellValueFactory(data -> new SimpleStringProperty(MoneyUtils.format(data.getValue().getPrice())));
    }

    private MenuItem applyDecorators(MenuItem item) {
        return uiContext.getMenuService().customize(
                item,
                extraCheeseItem.isSelected(),
                noOnionsItem.isSelected(),
                extraSauceItem.isSelected()
        );
    }

    private void refreshCartTotal() {
        Order cartPreview = new Order(0, List.copyOf(uiContext.getCartItems()));
        cartTotalLabel.setText(MoneyUtils.format(cartPreview.getTotal()));
        cartTable.refresh();
    }

    private void refreshAvailableTables() {
        Integer selectedTable = tableSelector.getValue();
        tableSelector.getItems().setAll(java.util.stream.IntStream.rangeClosed(1, 10)
                .boxed()
                .filter(this::isTableAvailable)
                .toList());
        if (selectedTable != null && tableSelector.getItems().contains(selectedTable)) {
            tableSelector.setValue(selectedTable);
        } else {
            tableSelector.setValue(null);
        }
        tableSelectionMessage.setText(tableSelector.getItems().isEmpty()
                ? "No tables are currently available."
                : "Choose a table for this order.");
        placeOrderButton.setDisable(tableSelector.getItems().isEmpty());
    }

    private boolean isTableAvailable(int tableNumber) {
        return uiContext.getOrders().stream()
                .filter(order -> order.getTableNumber() == tableNumber)
                .noneMatch(order -> order.getStatus() == OrderStatus.NEW
                        || order.getStatus() == OrderStatus.PLACED
                        || order.getStatus() == OrderStatus.PAID);
    }

    private record MenuRow(MenuItem item, String category) {
    }
}
