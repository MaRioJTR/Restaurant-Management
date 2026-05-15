package com.restaurant.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Value object describing a completed payment.
 *
 * Role: Carries payment result data back from the Strategy context.
 */
public class PaymentReceipt {
    private final UUID orderId;
    private final BigDecimal amount;
    private final String paymentMethod;
    private final LocalDateTime paidAt;

    public PaymentReceipt(UUID orderId, BigDecimal amount, String paymentMethod) {
        this.orderId = Objects.requireNonNull(orderId, "orderId");
        this.amount = Objects.requireNonNull(amount, "amount");
        this.paymentMethod = Objects.requireNonNull(paymentMethod, "paymentMethod");
        this.paidAt = LocalDateTime.now();
    }

    public UUID getOrderId() {
        return orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }
}
