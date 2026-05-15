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
    private final String cardNumber;

    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber.replaceAll("\\s+", "");
    }

    @Override
    public PaymentReceipt pay(UUID orderId, BigDecimal amount) {
        if (!isValidCardNumber(cardNumber)) {
            throw new IllegalArgumentException("Invalid credit card number.");
        }
        return new PaymentReceipt(orderId, amount, "Credit Card " + mask(cardNumber));
    }

    private boolean isValidCardNumber(String value) {
        if (!value.matches("\\d{13,19}")) {
            return false;
        }
        int sum = 0;
        boolean doubleDigit = false;
        for (int index = value.length() - 1; index >= 0; index--) {
            int digit = Character.digit(value.charAt(index), 10);
            if (doubleDigit) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
            doubleDigit = !doubleDigit;
        }
        return sum % 10 == 0;
    }

    private String mask(String value) {
        return "**** " + value.substring(value.length() - 4);
    }
}
