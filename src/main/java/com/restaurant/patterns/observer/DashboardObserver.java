package com.restaurant.patterns.observer;

import com.restaurant.models.Order;

/**
 * Dashboard-facing observer for order state changes.
 *
 * Role: Demonstrates a second concrete subscriber without coupling OrderService
 * to JavaFX controls.
 * Pattern: GoF Observer concrete observer.
 */
public class DashboardObserver implements OrderObserver {
    private String latestMessage = "Dashboard awaiting order updates.";

    @Override
    public void update(Order order) {
        latestMessage = "Dashboard update: order " + order.getId()
                + " is now " + order.getStatus() + ".";
    }

    public String getLatestMessage() {
        return latestMessage;
    }
}
