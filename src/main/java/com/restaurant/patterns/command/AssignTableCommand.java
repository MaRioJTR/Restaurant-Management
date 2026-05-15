package com.restaurant.patterns.command;

import com.restaurant.models.Order;
import com.restaurant.services.OrderService;

import java.util.Objects;

public class AssignTableCommand implements Command {
    private final OrderService orderService;
    private final Order order;
    private final int tableNumber;

    public AssignTableCommand(OrderService orderService, Order order, int tableNumber) {
        this.orderService = Objects.requireNonNull(orderService, "orderService");
        this.order = Objects.requireNonNull(order, "order");
        this.tableNumber = tableNumber;
    }

    @Override
    public void execute() {
        orderService.assignTable(order, tableNumber);
    }
}
