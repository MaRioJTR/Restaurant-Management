package com.restaurant.patterns.command;

import com.restaurant.models.Order;
import com.restaurant.models.PaymentReceipt;
import com.restaurant.patterns.strategy.PaymentStrategy;
import com.restaurant.services.PaymentService;

import java.util.Objects;

public class PayOrderCommand implements Command {
    private final PaymentService paymentService;
    private final PaymentStrategy paymentStrategy;
    private final Order order;
    private PaymentReceipt receipt;

    public PayOrderCommand(PaymentService paymentService, PaymentStrategy paymentStrategy, Order order) {
        this.paymentService = Objects.requireNonNull(paymentService, "paymentService");
        this.paymentStrategy = Objects.requireNonNull(paymentStrategy, "paymentStrategy");
        this.order = Objects.requireNonNull(order, "order");
    }

    @Override
    public void execute() {
        paymentService.setPaymentStrategy(paymentStrategy);
        receipt = paymentService.processPayment(order);
    }

    public PaymentReceipt getReceipt() {
        return receipt;
    }
}
