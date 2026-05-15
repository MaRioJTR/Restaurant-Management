package com.restaurant.patterns.observer;

import com.restaurant.models.Order;
import com.restaurant.utils.MoneyUtils;

/**
 * Kitchen-facing observer for new orders.
 *
 * Role: Receives order updates and converts them into kitchen display messages.
 * Pattern: GoF Observer concrete observer.
 */
public class KitchenDisplay implements OrderObserver {
    private String latestMessage = "Kitchen awaiting orders.";

    @Override
    public void update(Order order) {
        latestMessage = "Kitchen received order " + order.getId()
                + " for table " + order.getTableNumber()
                + " totaling " + MoneyUtils.format(order.getTotal()) + ".";
    }

    public String getLatestMessage() {
        return latestMessage;
    }
}
