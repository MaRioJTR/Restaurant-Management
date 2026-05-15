package com.restaurant.patterns.singleton;

import com.restaurant.models.Order;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread-safe singleton responsible for persistence access.
 *
 * Role: Central database gateway placeholder for repositories/services.
 * Pattern: GoF Singleton with private constructor and static getInstance().
 * Double-checked locking plus volatile ensures lazy, thread-safe initialization.
 */
public final class DatabaseManager {
    private static volatile DatabaseManager instance;

    private final Map<UUID, Order> orderStore = new ConcurrentHashMap<>();

    private DatabaseManager() {
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            synchronized (DatabaseManager.class) {
                if (instance == null) {
                    instance = new DatabaseManager();
                }
            }
        }
        return instance;
    }

    public void saveOrder(Order order) {
        orderStore.put(order.getId(), order);
    }

    public Optional<Order> findOrderById(UUID orderId) {
        return Optional.ofNullable(orderStore.get(orderId));
    }
}
