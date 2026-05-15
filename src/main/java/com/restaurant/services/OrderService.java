package com.restaurant.services;

import com.restaurant.models.Order;
import com.restaurant.patterns.observer.OrderObserver;
import com.restaurant.patterns.singleton.AppConfig;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Business service and subject for order lifecycle operations.
 *
 * Role: Places/cancels orders and coordinates persistence and notifications.
 * Pattern: GoF Observer subject. It owns observer registration and notifies
 * observers when an order state change should be reflected in the UI.
 */
public class OrderService {
    private final AppConfig config;
    private final Map<UUID, Order> orderStore = new ConcurrentHashMap<>();
    private final List<OrderObserver> observers = new CopyOnWriteArrayList<>();

    public OrderService() {
        this(AppConfig.getInstance());
    }

    public OrderService(AppConfig config) {
        this.config = Objects.requireNonNull(config, "config");
    }

    public void addObserver(OrderObserver observer) {
        observers.add(Objects.requireNonNull(observer, "observer"));
    }

    public void removeObserver(OrderObserver observer) {
        observers.remove(observer);
    }

    public void placeOrder(Order order) {
        order.place();
        saveOrder(order);
        notifyObservers(order);
    }

    public void cancelOrder(Order order) {
        order.cancel();
        saveOrder(order);
        notifyObservers(order);
    }

    public void markOrderDone(Order order) {
        completeOrder(order);
    }

    public void completeOrder(Order order) {
        order.complete();
        saveOrder(order);
        notifyObservers(order);
    }

    public Optional<Order> findOrderById(UUID orderId) {
        return Optional.ofNullable(orderStore.get(orderId));
    }

    private void saveOrder(Order order) {
        orderStore.put(order.getId(), order);
        appendAuditLine(order);
    }

    private void appendAuditLine(Order order) {
        String row = order.getId() + "|table=" + order.getTableNumber()
                + "|status=" + order.getStatus()
                + "|total=" + order.getTotal()
                + System.lineSeparator();
        try {
            Files.writeString(
                    Path.of(config.getOrdersFilePath()),
                    row,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to persist order audit record.", exception);
        }
    }

    private void notifyObservers(Order order) {
        observers.forEach(observer -> observer.update(order));
    }
}
