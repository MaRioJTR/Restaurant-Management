package com.restaurant.patterns.state;

import com.restaurant.models.Order;
import com.restaurant.models.OrderStatus;

/**
 * PLACED order state.
 *
 * Role: Allows a placed order to be paid or cancelled.
 * Pattern: GoF State concrete state.
 */
public class PlacedState implements OrderState {
    @Override
    public OrderStatus getStatus() {
        return OrderStatus.PLACED;
    }

    @Override
    public void pay(Order order) {
        order.changeState(new PaidState());
    }

    @Override
    public void cancel(Order order) {
        order.changeState(new CancelledState());
    }

    @Override
    public boolean blocksTable() {
        return true;
    }
}
