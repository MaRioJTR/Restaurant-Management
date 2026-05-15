package com.restaurant.patterns.command;

import com.restaurant.models.Order;
import com.restaurant.services.OrderService;

import java.util.Objects;

public class CompleteOrderCommand implements Command {
    private final OrderService orderService;
    private final Order order;

    public CompleteOrderCommand(OrderService orderService, Order order) {
        this.orderService = Objects.requireNonNull(orderService, "orderService");
        this.order = Objects.requireNonNull(order, "order");
    }

    @Override
    public void execute() {
        orderService.completeOrder(order);
    }
}
