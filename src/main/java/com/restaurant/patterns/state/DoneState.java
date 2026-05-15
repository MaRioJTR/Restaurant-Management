package com.restaurant.patterns.state;

import com.restaurant.models.OrderStatus;

/**
 * DONE terminal state.
 *
 * Role: Marks a completed kitchen workflow and prevents further transitions.
 * Pattern: GoF State concrete state.
 */
public class DoneState implements OrderState {
    @Override
    public OrderStatus getStatus() {
        return OrderStatus.DONE;
    }
}
