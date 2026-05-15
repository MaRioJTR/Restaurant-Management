package com.restaurant.controllers;

import com.restaurant.models.MenuItem;
import com.restaurant.models.Order;
import com.restaurant.patterns.command.OrderInvoker;
import com.restaurant.patterns.observer.OrderObserver;
import com.restaurant.patterns.singleton.AppConfig;
import com.restaurant.patterns.strategy.CreditCardPayment;
import com.restaurant.repositories.FileOrderRepository;
import com.restaurant.services.MenuService;
import com.restaurant.services.OrderService;
import com.restaurant.services.PaymentService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Shared JavaFX UI state for screens.
 *
 * Role: Provides one observable order list and shared service instances to all
 * controllers. It is intentionally a UI-layer coordinator, not business logic.
 */
public final class RestaurantUiContext implements OrderObserver {
    private static final RestaurantUiContext INSTANCE = new RestaurantUiContext();

    private final AppConfig config = AppConfig.getInstance();
    private final OrderService orderService = new OrderService(config, new FileOrderRepository(config));
    private final MenuService menuService = new MenuService();
    private final OrderInvoker orderInvoker = new OrderInvoker();
    private final PaymentService paymentService = new PaymentService(new CreditCardPayment("4111111111111111"));
    private final ObservableList<Order> orders = FXCollections.observableArrayList();
    private final ObservableList<MenuItem> cartItems = FXCollections.observableArrayList();
    private Order selectedOrder;

    private RestaurantUiContext() {
        orderService.addObserver(this);
    }

    public static RestaurantUiContext getInstance() {
        return INSTANCE;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public MenuService getMenuService() {
        return menuService;
    }

    public OrderInvoker getOrderInvoker() {
        return orderInvoker;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public ObservableList<Order> getOrders() {
        return orders;
    }

    public ObservableList<MenuItem> getCartItems() {
        return cartItems;
    }

    public Optional<Order> findOrder(UUID orderId) {
        return orders.stream().filter(order -> order.getId().equals(orderId)).findFirst();
    }

    public Optional<Order> getSelectedOrder() {
        return Optional.ofNullable(selectedOrder);
    }

    public void setSelectedOrder(Order selectedOrder) {
        this.selectedOrder = selectedOrder;
    }

    public List<Order> snapshotOrders() {
        return new ArrayList<>(orders);
    }

    @Override
    public void update(Order order) {
        Platform.runLater(() -> upsertOrder(order));
    }

    public void upsertOrder(Order order) {
        for (int index = 0; index < orders.size(); index++) {
            if (orders.get(index).getId().equals(order.getId())) {
                orders.set(index, order);
                return;
            }
        }
        orders.add(order);
    }
}
