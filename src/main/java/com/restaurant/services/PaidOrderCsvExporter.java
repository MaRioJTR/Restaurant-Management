package com.restaurant.services;

import com.restaurant.models.PaymentReceipt;
import com.restaurant.repositories.FilePaidOrderCsvRepository;
import com.restaurant.repositories.PaidOrderCsvRepository;

/**
 * Backward-compatible facade for paid-order CSV export.
 *
 * Role: Delegates file persistence to the repository layer.
 */
public class PaidOrderCsvExporter {
    private final PaidOrderCsvRepository repository = new FilePaidOrderCsvRepository();

    public void export(PaymentReceipt receipt) {
        repository.append(receipt);
    }
}
