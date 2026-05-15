package com.restaurant.patterns.command;

import com.restaurant.models.Order;
import com.restaurant.services.OrderService;

import java.util.Objects;

/**
 * Command that places an order through OrderService.
 *
 * Role: Wraps the place-order action as an object.
 * Pattern: GoF Command concrete command. It depends on OrderService as the
 * receiver and keeps UI controllers free of business logic.
 */
public class PlaceOrderCommand implements Command {
    private final OrderService orderService;
    private final Order order;

    public PlaceOrderCommand(OrderService orderService, Order order) {
        this.orderService = Objects.requireNonNull(orderService, "orderService");
        this.order = Objects.requireNonNull(order, "order");
    }

    @Override
    public void execute() {
        orderService.placeOrder(order);
    }
}
