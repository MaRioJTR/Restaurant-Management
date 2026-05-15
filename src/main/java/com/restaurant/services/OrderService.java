package com.restaurant.services;

import com.restaurant.models.Order;
import com.restaurant.patterns.observer.OrderObserver;
import com.restaurant.patterns.singleton.DatabaseManager;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Business service and subject for order lifecycle operations.
 *
 * Role: Places/cancels orders and coordinates persistence and notifications.
 * Pattern: GoF Observer subject. It owns observer registration and notifies
 * observers when an order state change should be reflected in the UI.
 */
public class OrderService {
    private final DatabaseManager databaseManager;
    private final List<OrderObserver> observers = new CopyOnWriteArrayList<>();

    public OrderService(DatabaseManager databaseManager) {
        this.databaseManager = Objects.requireNonNull(databaseManager, "databaseManager");
    }

    public void addObserver(OrderObserver observer) {
        observers.add(Objects.requireNonNull(observer, "observer"));
    }

    public void removeObserver(OrderObserver observer) {
        observers.remove(observer);
    }

    public void placeOrder(Order order) {
        order.place();
        databaseManager.saveOrder(order);
        notifyObservers(order);
    }

    public void cancelOrder(Order order) {
        order.cancel();
        databaseManager.saveOrder(order);
        notifyObservers(order);
    }

    public void markOrderDone(Order order) {
        order.done();
        databaseManager.saveOrder(order);
        notifyObservers(order);
    }

    private void notifyObservers(Order order) {
        observers.forEach(observer -> observer.update(order));
    }
}
