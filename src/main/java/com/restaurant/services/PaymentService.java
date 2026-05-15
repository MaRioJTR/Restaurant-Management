package com.restaurant.services;

import com.restaurant.models.Order;
import com.restaurant.models.PaymentReceipt;
import com.restaurant.patterns.strategy.PaymentStrategy;

import java.util.Objects;

/**
 * Payment context for restaurant checkout.
 *
 * Role: Delegates payment processing to an interchangeable strategy.
 * Pattern: GoF Strategy context. The algorithm can change at runtime through
 * setPaymentStrategy without modifying checkout logic.
 */
public class PaymentService {
    private PaymentStrategy paymentStrategy;
    private final PaidOrderCsvExporter csvExporter;

    public PaymentService(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = Objects.requireNonNull(paymentStrategy, "paymentStrategy");
        this.csvExporter = new PaidOrderCsvExporter();
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = Objects.requireNonNull(paymentStrategy, "paymentStrategy");
    }

    public PaymentReceipt processPayment(Order order) {
        order.pay();
        PaymentReceipt receipt = paymentStrategy.pay(order.getId(), order.getTotal());
        csvExporter.export(receipt);
        return receipt;
    }
}
