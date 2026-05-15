package com.restaurant.repositories;

import com.restaurant.models.PaymentReceipt;

public interface PaidOrderCsvRepository {
    void append(PaymentReceipt receipt);
}
