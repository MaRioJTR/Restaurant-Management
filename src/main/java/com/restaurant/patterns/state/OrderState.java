package com.restaurant.patterns.state;

import com.restaurant.models.Order;
import com.restaurant.models.OrderStatus;

/**
 * State abstraction for order lifecycle behavior.
 *
 * Role: Defines valid lifecycle operations for an order.
 * Pattern: GoF State. Concrete states decide which transitions are allowed.
 */
public interface OrderState {
    OrderStatus getStatus();

    default void place(Order order) {
        throw invalid("place");
    }

    default void pay(Order order) {
        throw invalid("pay");
    }

    default void cancel(Order order) {
        throw invalid("cancel");
    }

    default void done(Order order) {
        complete(order);
    }

    default void complete(Order order) {
        throw invalid("complete");
    }

    default boolean blocksTable() {
        return false;
    }

    private IllegalStateException invalid(String action) {
        return new IllegalStateException("Cannot " + action + " order while status is " + getStatus());
    }
}
