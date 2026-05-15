package com.restaurant.patterns.state;

import com.restaurant.models.Order;
import com.restaurant.models.OrderStatus;

/**
 * PAID order state.
 *
 * Role: Keeps paid orders available for kitchen completion.
 * Pattern: GoF State concrete state.
 */
public class PaidState implements OrderState {
    @Override
    public OrderStatus getStatus() {
        return OrderStatus.PAID;
    }

    @Override
    public void complete(Order order) {
        order.changeState(new DoneState());
    }

    @Override
    public boolean blocksTable() {
        return true;
    }
}
