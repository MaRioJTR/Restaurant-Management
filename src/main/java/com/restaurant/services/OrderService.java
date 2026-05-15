package com.restaurant.services;

import com.restaurant.models.Order;
import com.restaurant.patterns.observer.OrderObserver;
import com.restaurant.patterns.singleton.AppConfig;
import com.restaurant.repositories.FileOrderRepository;
import com.restaurant.repositories.OrderRepository;

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
    private final OrderRepository orderRepository;
    private final List<OrderObserver> observers = new CopyOnWriteArrayList<>();

    public OrderService() {
        this(AppConfig.getInstance());
    }

    public OrderService(AppConfig config) {
        this(config, new FileOrderRepository(config));
    }

    public OrderService(OrderRepository orderRepository) {
        this(AppConfig.getInstance(), orderRepository);
    }

    public OrderService(AppConfig config, OrderRepository orderRepository) {
        Objects.requireNonNull(config, "config");
        this.orderRepository = Objects.requireNonNull(orderRepository, "orderRepository");
    }

    public void addObserver(OrderObserver observer) {
        observers.add(Objects.requireNonNull(observer, "observer"));
    }

    public void removeObserver(OrderObserver observer) {
        observers.remove(observer);
    }

    public void placeOrder(Order order) {
        order.place();
        orderRepository.save(order);
        notifyObservers(order);
    }

    public void cancelOrder(Order order) {
        order.cancel();
        orderRepository.save(order);
        notifyObservers(order);
    }

    public void markOrderDone(Order order) {
        completeOrder(order);
    }

    public void completeOrder(Order order) {
        order.complete();
        orderRepository.save(order);
        notifyObservers(order);
    }

    public void assignTable(Order order, int tableNumber) {
        order.assignTable(tableNumber);
        orderRepository.save(order);
        notifyObservers(order);
    }

    private void notifyObservers(Order order) {
        observers.forEach(observer -> observer.update(order));
    }
}
