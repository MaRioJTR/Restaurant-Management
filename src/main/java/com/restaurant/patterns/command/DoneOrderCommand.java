package com.restaurant.patterns.command;

import com.restaurant.models.Order;
import com.restaurant.services.OrderService;

import java.util.Objects;

/**
 * Command that marks a paid order as done through OrderService.
 *
 * Role: Encapsulates the kitchen completion action.
 * Pattern: GoF Command concrete command. It preserves the existing command
 * routing for order lifecycle actions.
 */
public class DoneOrderCommand implements Command {
    private final OrderService orderService;
    private final Order order;

    public DoneOrderCommand(OrderService orderService, Order order) {
        this.orderService = Objects.requireNonNull(orderService, "orderService");
        this.order = Objects.requireNonNull(order, "order");
    }

    @Override
    public void execute() {
        orderService.markOrderDone(order);
    }
}
