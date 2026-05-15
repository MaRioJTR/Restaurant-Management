package com.restaurant.repositories;

import com.restaurant.models.PaymentReceipt;
import com.restaurant.patterns.singleton.AppConfig;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FilePaidOrderCsvRepository implements PaidOrderCsvRepository {
    private final AppConfig config;

    public FilePaidOrderCsvRepository() {
        this(AppConfig.getInstance());
    }

    public FilePaidOrderCsvRepository(AppConfig config) {
        this.config = config;
    }

    @Override
    public void append(PaymentReceipt receipt) {
        String row = String.join(",",
                escape(receipt.getOrderId().toString()),
                escape(receipt.getAmount().toPlainString()),
                escape(receipt.getPaymentMethod()),
                escape(receipt.getPaidAt().toString())
        ) + System.lineSeparator();

        try {
            Files.writeString(Path.of(config.getPaidOrdersCsvPath()), row, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to append paid order audit record.", exception);
        }
    }

    private String escape(String value) {
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }
}
