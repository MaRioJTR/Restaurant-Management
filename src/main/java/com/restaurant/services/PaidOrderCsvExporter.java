package com.restaurant.services;

import com.restaurant.models.PaymentReceipt;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Append-only CSV audit exporter for paid orders.
 *
 * Role: Writes successful payment records to paid_orders.csv.
 * Constraint: This exporter never reads order data back from CSV; the file is
 * write-only audit storage.
 */
public class PaidOrderCsvExporter {
    private static final Path CSV_PATH = Path.of("src/main/java/com/restaurant/services/paid_orders.csv");

    public void export(PaymentReceipt receipt) {
        String row = String.join(",",
                escape(receipt.getOrderId().toString()),
                escape(receipt.getAmount().toPlainString()),
                escape(receipt.getPaymentMethod()),
                escape(receipt.getPaidAt().toString())
        ) + System.lineSeparator();

        try {
            Files.writeString(
                    CSV_PATH,
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
