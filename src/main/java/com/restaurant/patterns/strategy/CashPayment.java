package com.restaurant.patterns.strategy;

import com.restaurant.models.PaymentReceipt;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Cash payment algorithm.
 *
 * Role: Handles cash settlement without changing PaymentService.
 * Pattern: GoF Strategy concrete strategy.
 */
public class CashPayment implements PaymentStrategy {
    @Override
    public PaymentReceipt pay(UUID orderId, BigDecimal amount) {
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("Cash payment amount must be positive.");
        }
        return new PaymentReceipt(orderId, amount, "Cash");
    }
}
