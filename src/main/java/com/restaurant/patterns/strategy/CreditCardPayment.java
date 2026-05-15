package com.restaurant.patterns.strategy;

import com.restaurant.models.PaymentReceipt;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Credit-card payment algorithm.
 *
 * Role: Handles card payment behavior behind the PaymentStrategy interface.
 * Pattern: GoF Strategy concrete strategy.
 */
public class CreditCardPayment implements PaymentStrategy {
    private final String maskedCardNumber;

    public CreditCardPayment(String maskedCardNumber) {
        this.maskedCardNumber = maskedCardNumber;
    }

    @Override
    public PaymentReceipt pay(UUID orderId, BigDecimal amount) {
        return new PaymentReceipt(orderId, amount, "Credit Card " + maskedCardNumber);
    }
}
