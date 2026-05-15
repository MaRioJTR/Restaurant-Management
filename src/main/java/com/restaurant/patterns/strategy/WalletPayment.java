package com.restaurant.patterns.strategy;

import com.restaurant.models.PaymentReceipt;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Digital wallet payment algorithm.
 *
 * Role: Handles wallet payments through the same strategy contract.
 * Pattern: GoF Strategy concrete strategy.
 */
public class WalletPayment implements PaymentStrategy {
    private final String walletProvider;

    public WalletPayment(String walletProvider) {
        this.walletProvider = walletProvider;
    }

    @Override
    public PaymentReceipt pay(UUID orderId, BigDecimal amount) {
        return new PaymentReceipt(orderId, amount, walletProvider + " Wallet");
    }
}
