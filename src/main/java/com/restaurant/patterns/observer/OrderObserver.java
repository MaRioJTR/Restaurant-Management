package com.restaurant.patterns.observer;

import com.restaurant.models.Order;

/**
 * Observer contract for order notifications.
 *
 * Role: Allows displays and UI surfaces to react to new orders.
 * Pattern: GoF Observer observer interface.
 */
public interface OrderObserver {
    void update(Order order);
}
