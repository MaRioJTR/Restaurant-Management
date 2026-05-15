package com.restaurant.patterns.command;

import com.restaurant.models.Order;
import com.restaurant.services.OrderService;

import java.util.Objects;

/**
 * Command that cancels an order through OrderService.
 *
 * Role: Wraps the cancel-order action as an object.
 * Pattern: GoF Command concrete command. It can be queued, logged, or reused by
 * different invokers without changing OrderService.
 */
public class CancelOrderCommand implements Command {
    private final OrderService orderService;
    private final Order order;

    public CancelOrderCommand(OrderService orderService, Order order) {
        this.orderService = Objects.requireNonNull(orderService, "orderService");
        this.order = Objects.requireNonNull(order, "order");
    }

    @Override
    public void execute() {
        orderService.cancelOrder(order);
    }
}
