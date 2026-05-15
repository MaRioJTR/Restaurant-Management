package com.restaurant.patterns.observer;

import com.restaurant.models.Order;

/**
 * Waiter-facing observer for order updates.
 *
 * Role: Represents a UI boundary that can be notified without coupling
 * OrderService to JavaFX controls.
 * Pattern: GoF Observer concrete observer.
 */
public class WaiterUI implements OrderObserver {
    private String latestMessage = "No waiter notifications yet.";

    @Override
    public void update(Order order) {
        latestMessage = "Waiter notified: order " + order.getId()
                + " placed for table " + order.getTableNumber() + ".";
    }

    public String getLatestMessage() {
        return latestMessage;
    }
}
