package com.restaurant.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Money formatting helper.
 *
 * Role: Keeps presentation formatting out of domain entities and services.
 */
public final class MoneyUtils {
    private static final NumberFormat USD_FORMAT = NumberFormat.getCurrencyInstance(Locale.US);

    private MoneyUtils() {
    }

    public static String format(BigDecimal amount) {
        return USD_FORMAT.format(amount);
    }
}
