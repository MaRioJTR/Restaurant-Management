package com.restaurant.patterns.strategy;

import com.restaurant.models.PaymentReceipt;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Payment algorithm abstraction.
 *
 * Role: Allows payment behavior to vary independently from PaymentService.
 * Pattern: GoF Strategy interface.
 */
public interface PaymentStrategy {
    PaymentReceipt pay(UUID orderId, BigDecimal amount);
}
