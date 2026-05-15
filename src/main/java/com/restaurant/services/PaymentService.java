package com.restaurant.services;

import com.restaurant.models.Order;
import com.restaurant.models.PaymentReceipt;
import com.restaurant.patterns.strategy.PaymentStrategy;
import com.restaurant.repositories.FilePaidOrderCsvRepository;
import com.restaurant.repositories.PaidOrderCsvRepository;

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
    private final PaidOrderCsvRepository paidOrderCsvRepository;

    public PaymentService(PaymentStrategy paymentStrategy) {
        this(paymentStrategy, new FilePaidOrderCsvRepository());
    }

    public PaymentService(PaymentStrategy paymentStrategy, PaidOrderCsvRepository paidOrderCsvRepository) {
        this.paymentStrategy = Objects.requireNonNull(paymentStrategy, "paymentStrategy");
        this.paidOrderCsvRepository = Objects.requireNonNull(paidOrderCsvRepository, "paidOrderCsvRepository");
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = Objects.requireNonNull(paymentStrategy, "paymentStrategy");
    }

    public PaymentReceipt processPayment(Order order) {
        order.pay();
        PaymentReceipt receipt = paymentStrategy.pay(order.getId(), order.getTotal());
        paidOrderCsvRepository.append(receipt);
        return receipt;
    }
}
