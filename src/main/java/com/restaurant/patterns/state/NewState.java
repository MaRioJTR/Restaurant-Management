package com.restaurant.patterns.state;

import com.restaurant.models.Order;
import com.restaurant.models.OrderStatus;

/**
 * NEW order state.
 *
 * Role: Allows a new order to be placed or cancelled.
 * Pattern: GoF State concrete state.
 */
public class NewState implements OrderState {
    @Override
    public OrderStatus getStatus() {
        return OrderStatus.NEW;
    }

    @Override
    public void place(Order order) {
        order.changeState(new PlacedState());
    }

    @Override
    public void cancel(Order order) {
        order.changeState(new CancelledState());
    }
}
