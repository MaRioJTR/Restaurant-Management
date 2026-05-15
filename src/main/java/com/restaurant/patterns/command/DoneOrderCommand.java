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
public class DoneOrderCommand extends CompleteOrderCommand {
    public DoneOrderCommand(OrderService orderService, Order order) {
        super(orderService, order);
    }
}
