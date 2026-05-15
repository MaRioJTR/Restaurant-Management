package com.restaurant.services;

import com.restaurant.models.PaymentReceipt;
import com.restaurant.patterns.singleton.AppConfig;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Backward-compatible facade for paid-order CSV export.
 *
 * Role: Delegates file persistence to the repository layer.
 */
public class PaidOrderCsvExporter {
    private final AppConfig config = AppConfig.getInstance();

    public void export(PaymentReceipt receipt) {
        String row = String.join(",",
                escape(receipt.getOrderId().toString()),
                escape(receipt.getAmount().toPlainString()),
                escape(receipt.getPaymentMethod()),
                escape(receipt.getPaidAt().toString())
        ) + System.lineSeparator();

        try {
            Files.writeString(
                    Path.of(config.getPaidOrdersCsvPath()),
                    row,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to append paid order audit record.", exception);
        }
    }

    private String escape(String value) {
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }
}
