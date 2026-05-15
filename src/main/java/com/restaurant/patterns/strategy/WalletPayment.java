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
    private BigDecimal availableBalance;

    public WalletPayment(String walletProvider) {
        this(walletProvider, new BigDecimal("250.00"));
    }

    public WalletPayment(String walletProvider, BigDecimal availableBalance) {
        this.walletProvider = walletProvider;
        this.availableBalance = availableBalance;
    }

    @Override
    public PaymentReceipt pay(UUID orderId, BigDecimal amount) {
        if (availableBalance.compareTo(amount) < 0) {
            throw new IllegalStateException(walletProvider + " wallet balance is insufficient.");
        }
        availableBalance = availableBalance.subtract(amount);
        return new PaymentReceipt(orderId, amount, walletProvider + " Wallet");
    }
}
