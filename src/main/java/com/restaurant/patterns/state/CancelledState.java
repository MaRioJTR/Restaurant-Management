package com.restaurant.patterns.state;

import com.restaurant.models.OrderStatus;

/**
 * CANCELLED terminal state.
 *
 * Role: Prevents further lifecycle transitions after cancellation.
 * Pattern: GoF State concrete state.
 */
public class CancelledState implements OrderState {
    @Override
    public OrderStatus getStatus() {
        return OrderStatus.CANCELLED;
    }
}
